package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase{
    @Test
    void canModifyGroup() throws InterruptedException {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "name", "header", "footer"));
        }
        app.groups().modifyGroup(new GroupData().withName("modified name"));
    }
}
