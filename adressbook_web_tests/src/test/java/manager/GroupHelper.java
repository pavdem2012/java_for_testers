package manager;

import io.qameta.allure.Step;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GroupHelper extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public static void openGroupsPage() {
        if (!isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    public static String findNewGroupId(List<GroupData> oldList, List<GroupData> newList) {
        //Using sets to find the difference
        Set<GroupData> oldSet = new HashSet<>(oldList);
        Set<GroupData> newSet = new HashSet<>(newList);

        newSet.removeAll(oldSet);

        if (newSet.size() != 1) {
            throw new RuntimeException("Expected exactly one new group, but found: " + newSet.size());
        }

        return newSet.iterator().next().id();
    }

    // Вспомогательный метод для поиска группы по ID
    public static GroupData findGroupById(List<GroupData> groups, String id) {
        return groups.stream()
                .filter(g -> g.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
    }

    @Step
    public int getCount() {
        GroupHelper.openGroupsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    @Step
    public void removeGroup(GroupData group) throws InterruptedException {
        Thread.sleep(50);
        openGroupsPage();
        Thread.sleep(50);
        selectGroup(group);
        Thread.sleep(50);
        removeSelectedGroups();
        Thread.sleep(50);
        returnToGroupsPage();
        Thread.sleep(50);
    }


    public void createGroup(GroupData group) throws InterruptedException {
        openGroupsPage();
        initGroupCreation();
        Thread.sleep(50);
        fillGroupForm(group);
        submitGroupCreation();
        Thread.sleep(200);
        returnToGroupsPage();
    }


    public void modifyGroup(GroupData group, GroupData modifiedGroup) throws InterruptedException {
        Thread.sleep(50);
        openGroupsPage();
        selectGroup(group);
        initGroupModification();
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        Thread.sleep(300);
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

    private void selectGroup(GroupData group) {
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
    }

    private void initGroupCreation() {
        click(By.name("new"));
    }

    private void submitGroupCreation() {
        click(By.name("submit"));
    }

    @Step
    public void removeAllGroups() throws InterruptedException {
        openGroupsPage();
        driver.navigate().refresh();
        Thread.sleep(150);
        driver.navigate().refresh();
        selectAllGroups();
        Thread.sleep(50);
        removeSelectedGroups();
        Thread.sleep(100);
    }

    private void selectAllGroups() {
        manager.driver
                .findElements(By.name("selected[]"))
                .forEach(WebElement::click);
    }

    @Step
    public List<GroupData> getList() throws InterruptedException {
        openGroupsPage();
        Thread.sleep(500);
        var spans = manager.driver.findElements(By.cssSelector("span.group"));
        return spans.stream()
                .map(span -> {
                    var name = span.getText();
                    var checkbox = span.findElement(By.name("selected[]"));
                    var id = checkbox.getAttribute("value");
                    return new GroupData().withId(id).withName(name);
                })
                .collect(Collectors.toList());
    }

}