package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() throws InterruptedException {
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("name", "header", "footer"));
        }
        app.groups().removeGroup();
    }

}
