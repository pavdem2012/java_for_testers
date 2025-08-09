package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "name", "header", "footer"));
        }
        int groupCont = app.groups().getCount();
        var oldGroups = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
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
    public void canRemoveGroupsAtOnce() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "name", "header", "footer"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }

}
