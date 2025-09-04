package tests;

import model.GroupData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupRemovalTests extends TestBase {
    @Test
    @Order(1)
    public void canRemoveGroupDB() throws InterruptedException {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name DB Remove", "group header DB Remove", "group footer DB Remove"));
        }
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        Thread.sleep(300);
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    @Order(2)
    public void canRemoveGroupsAtOnceDB() throws InterruptedException {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name AtOnceDB Remove", "group header AtOnceDB Remove", "group footer AtOnceDB Remove"));
        }
        Thread.sleep(200);
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.hbm().getGroupCount());
    }

    @Test
    @Order(3)
    public void canRemoveGroup() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "name", "header", "footer"));
        }
        int groupCont = app.groups().getCount();
        var oldGroups = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        Thread.sleep(200);
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.groups().getList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont - 1, newGroupCont);
        Assertions.assertEquals(newGroups.size(), oldGroups.size() - 1);
        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    @Order(4)
    public void canRemoveGroupsAtOnce() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "name", "header", "footer"));
        }
        Thread.sleep(200);
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }


}
