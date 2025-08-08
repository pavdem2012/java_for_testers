package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() throws InterruptedException {
        int groupCont = app.groups().getCount();
        app.groups().createGroup(new GroupData("group name", "group header", "group footer"));
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont + 1, newGroupCont);

    }

    @Test
    public void canCreateGroupWithEmptyName() throws InterruptedException {
        app.groups().createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() throws InterruptedException {
        app.groups().createGroup(new GroupData().withName("some name"));
    }

    @Test
    public void canCreateMultipleGroups() throws InterruptedException {
        int n =5;
        int groupCont = app.groups().getCount();
        for (int i = 0; i < n; i++ ) {
            app.groups().createGroup(new GroupData(randomString(i+1), "group header", "group footer"));
        }
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont + n, newGroupCont);

    }
}
