import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase{

    @Test
    public void canRemoveGroup() throws InterruptedException {
        openGroupsPage();
        if (isGroupPresent()) {
            createGroup("group_name", "group_header", "group_footer");
        }
        removeGroup();
    }

}
