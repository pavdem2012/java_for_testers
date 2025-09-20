package tests;

import io.qameta.allure.Allure;
import model.GroupData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupRemovalTestsWithDB extends TestBase {

    @Test
    @Order(1)
    public void canRemoveGroupWithFullDBCheck() throws InterruptedException {
        Allure.step("Checking precondition", step -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "group name DB Remove", "group header DB Remove", "group footer DB Remove"));
            }
        });
        var oldGroupsHbm = app.hbm().getGroupList();
        var oldGroupsJdbc = app.jdbc().getGroupList();
        var oldGroupsUI = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroupsUI.size());
        var groupToRemove = oldGroupsUI.get(index);

        app.groups().removeGroup(groupToRemove);

        // Получаем новые данные
        var newGroupsUI = app.groups().getList();
        var newGroupsHbm = app.hbm().getGroupList();
        var newGroupsJdbc = app.jdbc().getGroupList();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldGroupsUI);
        var expectedHbmList = new ArrayList<>(oldGroupsHbm);
        var expectedJdbcList = new ArrayList<>(oldGroupsJdbc);

        expectedUIList.remove(index);
        expectedHbmList.remove(index);
        expectedJdbcList.remove(index);

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

        // Проверка количества
        Assertions.assertEquals(oldGroupsUI.size() - 1, newGroupsUI.size());
        Assertions.assertEquals(oldGroupsHbm.size() - 1, newGroupsHbm.size());
        Assertions.assertEquals(oldGroupsJdbc.size() - 1, newGroupsJdbc.size());

        // Проверка что группа действительно удалена из БД
        boolean groupExistsInHbm = newGroupsHbm.stream()
                .anyMatch(g -> g.id().equals(groupToRemove.id()));
        boolean groupExistsInJdbc = newGroupsJdbc.stream()
                .anyMatch(g -> g.id().equals(groupToRemove.id()));

        Assertions.assertFalse(groupExistsInHbm, "Группа все еще существует в Hibernate");
        Assertions.assertFalse(groupExistsInJdbc, "Группа все еще существует в JDBC");
    }


    @Test
    @Order(2)
    public void canRemoveAllGroupsWithDBCheck() throws InterruptedException {

        Allure.step("Checking precondition", step -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "group name for Remove", "group header for Remove", "group footer for Remove"));
            }
            Thread.sleep(400);
        });

        app.groups().removeAllGroups();
        Thread.sleep(400);
        var uiCount = app.groups().getCount();
        var hbmCount = app.hbm().getGroupCount();
        var jdbcCount = app.jdbc().getGroupList().size();

        Assertions.assertEquals(0, uiCount, "Не все группы удалены из UI");
        Assertions.assertEquals(0, hbmCount, "Не все группы удалены из Hibernate");
        Assertions.assertEquals(0, jdbcCount, "Не все группы удалены из JDBC");

        var hbmGroups = app.hbm().getGroupList();
        var jdbcGroups = app.jdbc().getGroupList();

        Assertions.assertTrue(hbmGroups.isEmpty(), "Список Hibernate не пустой");
        Assertions.assertTrue(jdbcGroups.isEmpty(), "Список JDBC не пустой");
    }
}
