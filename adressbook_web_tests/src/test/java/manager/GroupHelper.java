package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase {
    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openGroupsPage() {
        if (!isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    public boolean isGroupPresent() {
        openGroupsPage();
        return isElementPresent(By.name("selected[]"));
    }

    public void removeGroup() throws InterruptedException {
        openGroupsPage();
        click(By.name("selected[]"));
        click(By.xpath("(//input[@name='delete'])[2]"));
        Thread.sleep(50);
        click(By.linkText("group page"));
    }

    public void createGroup(GroupData group) throws InterruptedException {
        openGroupsPage();
        click(By.name("new"));
        Thread.sleep(50);
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
        click(By.name("submit"));
        Thread.sleep(50);
        click(By.linkText("group page"));
    }
}