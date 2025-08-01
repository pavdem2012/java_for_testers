import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase{

    @Test
    public void canCreateGroup() throws InterruptedException {
        openGroupsPage();
        createGroup(new GroupData("group name", "group header", "group footer"));
    }

    @Test
    public void canCreateGroupWithEmptyName() throws InterruptedException {
        openGroupsPage();
        createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() throws InterruptedException {
        openGroupsPage();
        var emptyGroup = new GroupData();
        var groupWithName = emptyGroup.withName("some name");
        createGroup(groupWithName);
    }
}
