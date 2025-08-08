package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("name", "header", "footer"));
        }
        int groupCont = app.groups().getCount();
        app.groups().removeGroup();
        int newGroupCont = app.groups().getCount();
        Assertions.assertEquals(groupCont - 1, newGroupCont);

    }

}
