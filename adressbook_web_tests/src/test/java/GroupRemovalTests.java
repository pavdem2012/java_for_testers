import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() throws InterruptedException {
        openGroupsPage();
        if (isGroupPresent()) {
            createGroup(new GroupData("name", "header", "footer"));
        }
        removeGroup();
    }

}
