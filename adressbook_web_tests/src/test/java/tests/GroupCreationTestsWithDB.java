package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;

public class GroupCreationTestsWithDB extends TestBase {

    @ParameterizedTest
    @MethodSource("tests.GroupCreationTests#singleRandomGroup")
    public void canCreateGroupWithFullDBCheck(GroupData group) throws InterruptedException {
        // Получаем исходные данные из всех источников
        var oldGroupsUI = app.groups().getList();
        var oldGroupsHbm = app.hbm().getGroupList();
        var oldGroupsJdbc = app.jdbc().getGroupList();

        // Создаем группу
        app.groups().createGroup(group);

        // Получаем новые данные
        var newGroupsUI = app.groups().getList();
        var newGroupsHbm = app.hbm().getGroupList();
        var newGroupsJdbc = app.jdbc().getGroupList();

        // Определяем ID новой группы
        var newGroupId = newGroupsUI.get(newGroupsUI.size() - 1).id();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldGroupsUI);
        var expectedHbmList = new ArrayList<>(oldGroupsHbm);
        var expectedJdbcList = new ArrayList<>(oldGroupsJdbc);

        expectedUIList.add(group.withId(newGroupId).withHeader("").withFooter(""));
        System.out.println(expectedUIList);
        expectedHbmList.add(group.withId(newGroupId));
        expectedJdbcList.add(group.withId(newGroupId));

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
        Assertions.assertEquals(oldGroupsUI.size() + 1, newGroupsUI.size());
        Assertions.assertEquals(oldGroupsHbm.size() + 1, newGroupsHbm.size());
        Assertions.assertEquals(oldGroupsJdbc.size() + 1, newGroupsJdbc.size());

        // Проверка конкретных атрибутов в БД
        var createdGroupHbm = newGroupsHbm.stream()
                .filter(g -> g.id().equals(newGroupId))
                .findFirst()
                .orElseThrow();

        var createdGroupJdbc = newGroupsJdbc.stream()
                .filter(g -> g.id().equals(newGroupId))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(group.name(), createdGroupHbm.name());
        Assertions.assertEquals(group.name(), createdGroupJdbc.name());
        Assertions.assertEquals(group.header(), createdGroupHbm.header());
        Assertions.assertEquals(group.header(), createdGroupJdbc.header());
        Assertions.assertEquals(group.footer(), createdGroupHbm.footer());
        Assertions.assertEquals(group.footer(), createdGroupJdbc.footer());
    }

    @Test
    public void canCreateGroupWithEmptyFieldsDBCheck() throws InterruptedException {
        var group = new GroupData().withName("").withHeader("").withFooter("");

        var oldGroupsHbm = app.hbm().getGroupList();
        var oldGroupsJdbc = app.jdbc().getGroupList();

        app.groups().createGroup(group);

        var newGroupsHbm = app.hbm().getGroupList();
        var newGroupsJdbc = app.jdbc().getGroupList();

        Assertions.assertEquals(oldGroupsHbm.size() + 1, newGroupsHbm.size());
        Assertions.assertEquals(oldGroupsJdbc.size() + 1, newGroupsJdbc.size());

        // Находим созданную группу
        var newGroup = newGroupsHbm.stream()
                .filter(g -> !oldGroupsHbm.contains(g))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals("", newGroup.name());
        Assertions.assertEquals("", newGroup.header());
        Assertions.assertEquals("", newGroup.footer());
    }
}