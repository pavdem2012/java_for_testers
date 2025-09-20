package tests;

import common.CommonFunctions;
import manager.GroupHelper;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class GroupModificationTestsWithDB extends TestBase {

    @Test
    void canModifyGroupWithFullDBCheck() throws InterruptedException {
        preconditionCheck();
        var oldGroupsUI = app.groups().getList();
        var oldGroupsHbm = app.hbm().getGroupList();
        var oldGroupsJdbc = app.jdbc().getGroupList();

        var rnd = new Random();
        var index = rnd.nextInt(oldGroupsUI.size());
        var groupToModify = oldGroupsUI.get(index);

        var modifiedData = new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(10))
                .withFooter(CommonFunctions.randomString(10));

        app.groups().modifyGroup(groupToModify, modifiedData);

        // Получаем новые данные
        var newGroupsUI = app.groups().getList();
        var newGroupsHbm = app.hbm().getGroupList();
        var newGroupsJdbc = app.jdbc().getGroupList();

        // Создаем expected списки на основе старых данных
        var expectedUIList = new ArrayList<>(oldGroupsUI);
        var expectedHbmList = new ArrayList<>(oldGroupsHbm);
        var expectedJdbcList = new ArrayList<>(oldGroupsJdbc);

        // Для UI: header и footer не отображаются (пустые)
        var expectedModifiedGroupUI = new GroupData()
                .withId(groupToModify.id())
                .withName(modifiedData.name())
                .withHeader("")
                .withFooter("");

        // Для БД: header и footer сохраняются
        var expectedModifiedGroupDB = new GroupData()
                .withId(groupToModify.id())
                .withName(modifiedData.name())
                .withHeader(modifiedData.header())
                .withFooter(modifiedData.footer());

        // Заменяем измененную группу в expected списках
        expectedModifiedGroupUI.replaceGroupInList(expectedUIList, groupToModify.id());
        expectedModifiedGroupDB.replaceGroupInList(expectedHbmList, groupToModify.id());
        expectedModifiedGroupDB.replaceGroupInList(expectedJdbcList, groupToModify.id());

        // Проверки через множества (игнорируют порядок)
        Assertions.assertEquals(Set.copyOf(expectedUIList), Set.copyOf(newGroupsUI), "UI списки не совпадают");
        Assertions.assertEquals(Set.copyOf(expectedHbmList), Set.copyOf(newGroupsHbm), "Hibernate списки не совпадают");
        Assertions.assertEquals(Set.copyOf(expectedJdbcList), Set.copyOf(newGroupsJdbc), "JDBC списки не совпадают");

        // Дополнительная проверка: находим измененную группу в новых списках
        var modifiedGroupUI = GroupHelper.findGroupById(newGroupsUI, groupToModify.id());
        var modifiedGroupHbm = GroupHelper.findGroupById(newGroupsHbm, groupToModify.id());
        var modifiedGroupJdbc = GroupHelper.findGroupById(newGroupsJdbc, groupToModify.id());

        // Проверка UI атрибутов (header и footer пустые)
        Assertions.assertEquals(modifiedData.name(), modifiedGroupUI.name());
        Assertions.assertEquals("", modifiedGroupUI.header());
        Assertions.assertEquals("", modifiedGroupUI.footer());

        // Проверка БД атрибутов (header и footer сохраняются)
        Assertions.assertEquals(modifiedData.name(), modifiedGroupHbm.name());
        Assertions.assertEquals(modifiedData.header(), modifiedGroupHbm.header());
        Assertions.assertEquals(modifiedData.footer(), modifiedGroupHbm.footer());

        Assertions.assertEquals(modifiedData.name(), modifiedGroupJdbc.name());
        Assertions.assertEquals(modifiedData.header(), modifiedGroupJdbc.header());
        Assertions.assertEquals(modifiedData.footer(), modifiedGroupJdbc.footer());
    }


    @Test
    void canModifyOnlyGroupNameWithDBCheck() throws InterruptedException {
        preconditionCheck();
        var oldGroupsHbm = app.hbm().getGroupList();
        var groupToModify = oldGroupsHbm.get(0);

        var modifiedData = new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(groupToModify.header()) // сохраняем оригинальные значения
                .withFooter(groupToModify.footer());

        app.groups().modifyGroup(groupToModify, modifiedData);

        var newGroupsHbm = app.hbm().getGroupList();
        var modifiedGroup = newGroupsHbm.stream()
                .filter(g -> g.id().equals(groupToModify.id()))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(modifiedData.name(), modifiedGroup.name());
        Assertions.assertEquals(groupToModify.header(), modifiedGroup.header());
        Assertions.assertEquals(groupToModify.footer(), modifiedGroup.footer());
    }

    private static void preconditionCheck() throws InterruptedException {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name HBM Modify create", "group header HBM Modify create", "group footer HBM Modify create"));
        }
    }
}