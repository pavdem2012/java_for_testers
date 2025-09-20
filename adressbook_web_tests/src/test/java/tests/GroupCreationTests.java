package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        for (var name : List.of("", "group name")) {
            for (var header : List.of("", "group header")) {
                for (var footer : List.of("", "group footer")) {
                    result.add(new GroupData()
                            .withName(name)
                            .withHeader(header)
                            .withFooter(footer));
                }
            }
        }
        var json = "";
        try (var reader = new FileReader("groups.json");
             var breader = new BufferedReader(reader)) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line;
                line = breader.readLine();
            }
        }
/*
        var json = Files.readString(Paths.get("groups.json"));
*/
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {
        });
        result.addAll(value);
        return result;
    }

/*    @ParameterizedTest
    @ValueSource(strings = {"group name", "group name"})
    public void canCreateGroup(String name) throws InterruptedException {
        int groupCont = app.groups().getCount();
        app.groups().createGroup(new GroupData(name, "group header", "group footer"));
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont + 1, newGroupCont);

    }*/

/*    @Test
    public void canCreateGroupWithEmptyName() throws InterruptedException {
        app.groups().createGroup(new GroupData());
    }*/

/*    @Test
    public void canCreateGroupWithNameOnly() throws InterruptedException {
        app.groups().createGroup(new GroupData().withName("some name"));
    }*/


    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) throws InterruptedException {
        var oldGroups = app.groups().getList();
        var oldGroupsDB = app.jdbc().getGroupList();

        int groupCont = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont + 1, newGroupCont);
        var newGroups = app.groups().getList();
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var expectedList = new ArrayList<>(oldGroups);

        expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);

    }

    public static Stream<GroupData> randomGroups() {
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(20))
                .withFooter(CommonFunctions.randomString(30));
        return Stream.generate(randomGroup).limit(3);
    }

    @ParameterizedTest
    @MethodSource("randomGroups")
    public void canCreateGroup(GroupData group) throws InterruptedException {
        var oldUIGroups = app.groups().getList();
        var oldGroupsDB = app.jdbc().getGroupList();
        var oldGroupsDBHbm = app.hbm().getGroupList();

        int groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCont);

        var newUIGroups = app.groups().getList();
        var newGroupsDB = app.jdbc().getGroupList();
        var newGroupsDBHbm = app.hbm().getGroupList();

        // Находим ID созданной группы в UI (для expected UI списка)
        var extraGroups = newUIGroups.stream().filter(g -> !oldUIGroups.contains(g)).toList();
        var newId = extraGroups.get(0).id();
        // Создаем expected списки
        var expectedUIList = new ArrayList<>(oldUIGroups);
        var expectedListDB = new ArrayList<>(oldGroupsDB);
        var expectedListDBHbm = new ArrayList<>(oldGroupsDBHbm);

        // Добавляем новую группу с правильными ID
        expectedUIList.add(group.withId(newId).withHeader("").withFooter(""));
        expectedListDB.add(group.withId(newId)); // Используем тот же ID для согласованности
        expectedListDBHbm.add(group.withId(newId));

        // Сравниваем через множества (игнорирует порядок)
        Assertions.assertEquals(Set.copyOf(newUIGroups), Set.copyOf(expectedUIList));
        Assertions.assertEquals(Set.copyOf(newGroupsDB), Set.copyOf(expectedListDB));
        Assertions.assertEquals(Set.copyOf(newGroupsDBHbm), Set.copyOf(expectedListDBHbm));
    }

    public static List<GroupData> negativeGroupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        var json = Files.readString(Paths.get("negative_groups.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {
        });
        result.addAll(value);
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateMultipleGroups(GroupData group) throws InterruptedException {
        var oldGroups = app.groups().getList();
        int groupCont = app.groups().getCount();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont, newGroupCont);
        Assertions.assertEquals(newGroups, oldGroups);

    }
}
