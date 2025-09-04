package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

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
                .withName("Modified Name " + System.currentTimeMillis())
                .withHeader("Modified Header")
                .withFooter("Modified Footer");

        app.groups().modifyGroup(groupToModify, modifiedData);

        // Получаем новые данные
        var newGroupsUI = app.groups().getList();
        var newGroupsHbm = app.hbm().getGroupList();
        var newGroupsJdbc = app.jdbc().getGroupList();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldGroupsUI);
        var expectedHbmList = new ArrayList<>(oldGroupsHbm);
        var expectedJdbcList = new ArrayList<>(oldGroupsJdbc);

        var expectedModifiedGroupUI = new GroupData()
                .withId(groupToModify.id())
                .withName(modifiedData.name())
                .withHeader("")
                .withFooter("");

        var expectedModifiedGroup = modifiedData.withId(groupToModify.id());
        expectedUIList.set(index, expectedModifiedGroupUI);
        System.out.println(expectedUIList);
        expectedHbmList.set(index, expectedModifiedGroup);
        expectedJdbcList.set(index, expectedModifiedGroup);

        // Сортируем все списки по ID
        Comparator<GroupData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));

        expectedUIList.sort(compareById);
        expectedHbmList.sort(compareById);
        expectedJdbcList.sort(compareById);
        newGroupsUI.sort(compareById);
        newGroupsHbm.sort(compareById);
        newGroupsJdbc.sort(compareById);

        // Проверки
        Assertions.assertEquals(expectedUIList, newGroupsUI, "UI списки не совпадают");
        Assertions.assertEquals(expectedHbmList, newGroupsHbm, "Hibernate списки не совпадают");
        Assertions.assertEquals(expectedJdbcList, newGroupsJdbc, "JDBC списки не совпадают");

        // Проверка конкретных атрибутов в БД
        var modifiedGroupHbm = newGroupsHbm.stream()
                .filter(g -> g.id().equals(groupToModify.id()))
                .findFirst()
                .orElseThrow();

        var modifiedGroupJdbc = newGroupsJdbc.stream()
                .filter(g -> g.id().equals(groupToModify.id()))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(modifiedData.name(), modifiedGroupHbm.name());
        Assertions.assertEquals(modifiedData.name(), modifiedGroupJdbc.name());
        Assertions.assertEquals(modifiedData.header(), modifiedGroupHbm.header());
        Assertions.assertEquals(modifiedData.header(), modifiedGroupJdbc.header());
        Assertions.assertEquals(modifiedData.footer(), modifiedGroupHbm.footer());
        Assertions.assertEquals(modifiedData.footer(), modifiedGroupJdbc.footer());
    }


    @Test
    void canModifyOnlyGroupNameWithDBCheck() throws InterruptedException {
        preconditionCheck();
        var oldGroupsHbm = app.hbm().getGroupList();
        var groupToModify = oldGroupsHbm.get(0);

        var modifiedData = new GroupData()
                .withName("Only Name Modified " + System.currentTimeMillis())
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

    private static void preconditionCheck() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name HBM Modify create", "group header HBM Modify create", "group footer HBM Modify create"));
        }
    }
}