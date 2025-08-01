import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase{

    @Test
    public void canCreateGroup() throws InterruptedException {
        openGroupsPage();
        createGroup("group name", "group header", "group footer");
    }

    @Test
    public void canCreateGroupWithEmptyName() throws InterruptedException {

        openGroupsPage();
        createGroup("", "group header", "group footer");
    }
}
