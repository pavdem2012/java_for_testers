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
}
