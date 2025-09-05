package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;

public class GroupCreationTestsWithDB extends TestBase {

    @ParameterizedTest
    @MethodSource("tests.GroupCreationTests#randomGroups")
    public void canCreateGroupWithFullDBCheck(GroupData group) throws InterruptedException {
        // Получаем исходные данные из всех источников
        var oldGroupsUI = Set.copyOf(app.groups().getList());
        var oldGroupsHbm = Set.copyOf(app.hbm().getGroupList());
        var oldGroupsJdbc = Set.copyOf(app.jdbc().getGroupList());

        // Создаем группу
        app.groups().createGroup(group);

        // Получаем новые данные
        var newGroupsUI = Set.copyOf(app.groups().getList());
        var newGroupsHbm = Set.copyOf(app.hbm().getGroupList());
        var newGroupsJdbc = Set.copyOf(app.jdbc().getGroupList());

        // Находим ID новой группы (разница между новым и старым множеством)
        var newGroupId = newGroupsUI.stream()
                .filter(g -> !oldGroupsUI.contains(g))
                .findFirst()
                .orElseThrow()
                .id();

        // Подготавливаем ожидаемые множества
        var expectedUI = new HashSet<>(oldGroupsUI);
        var expectedHbm = new HashSet<>(oldGroupsHbm);
        var expectedJdbc = new HashSet<>(oldGroupsJdbc);

        expectedUI.add(group.withId(newGroupId).withHeader("").withFooter(""));
        expectedHbm.add(group.withId(newGroupId));
        expectedJdbc.add(group.withId(newGroupId));

        // Проверки множеств
        Assertions.assertEquals(expectedUI, newGroupsUI, "UI множества не совпадают");
        Assertions.assertEquals(expectedHbm, newGroupsHbm, "Hibernate множества не совпадают");
        Assertions.assertEquals(expectedJdbc, newGroupsJdbc, "JDBC множества не совпадают");

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

        var oldGroupsHbm = Set.copyOf(app.hbm().getGroupList());
        var oldGroupsJdbc = Set.copyOf(app.jdbc().getGroupList());

        app.groups().createGroup(group);

        var newGroupsHbm = Set.copyOf(app.hbm().getGroupList());
        var newGroupsJdbc = Set.copyOf(app.jdbc().getGroupList());

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