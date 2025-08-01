package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() throws InterruptedException {
        app.openGroupsPage();
        if (app.isGroupPresent()) {
            app.createGroup(new GroupData("name", "header", "footer"));
        }
        app.removeGroup();
    }

}
