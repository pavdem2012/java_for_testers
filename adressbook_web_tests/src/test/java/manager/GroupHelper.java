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


    public void removeGroup() throws InterruptedException {
        openGroupsPage();
        selectGroup();
        removeSelectedGroups();
        Thread.sleep(50);
        returnToGroupsPage();
    }


    public void createGroup(GroupData group) throws InterruptedException {
        openGroupsPage();
        initGroupCreation();
        Thread.sleep(50);
        fillGroupForm(group);
        submitGroupCreation();
        Thread.sleep(50);
        returnToGroupsPage();
    }


    public void modifyGroup(GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup();
        initGroupModification();
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    private void removeSelectedGroups() {
        click(By.xpath("(//input[@name='delete'])[2]"));
    }

    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    private void submitGroupModification() {
        manager.driver.findElement(By.name("update")).click();
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
    }

    private void initGroupModification() {
        manager.driver.findElement(By.name("edit")).click();
    }

    private void selectGroup() {
        click(By.name("selected[]"));
    }

    private void initGroupCreation() {
        click(By.name("new"));
    }

    private void submitGroupCreation() {
        click(By.name("submit"));
    }

    public int getCount() {
        openGroupsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeAllGroups() {
        openGroupsPage();
        selectAllGroups();
        removeSelectedGroups();
    }

    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox:checkboxes){
            checkbox.click();
        }
    }
}