package manager;
import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    protected WebDriver driver;
    private LoginHelper session;
    private GroupHelper groups;
    private ContactsHelper contacts;
    private Properties properties;
    private JdbcHelper jdbc;
    private HibernateHelper hbm;


    public void init(String browser, Properties properties) {
        this.properties = properties;
        if (driver == null) {
            if ("firefox".equals(browser)) {
                driver = new FirefoxDriver();
            } else if ("chrome".equals(browser)) {
                driver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknown browser %s", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(2360, 1321));
            session().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
        }
    }

    public LoginHelper session() {
        if (session == null) {
            session = new LoginHelper(this);
        }
        return session;
    }

    public GroupHelper groups() {
        if (groups == null) {
            groups = new GroupHelper(this);
        }
        return groups;
    }

    public ContactsHelper contacts() {
        if (contacts == null) {
            contacts = new ContactsHelper(this);
        }
        return contacts;
    }

    public JdbcHelper jdbc() {
        if (jdbc == null) {
            jdbc = new JdbcHelper(this);
        }
        return jdbc;
    }

    public HibernateHelper hbm() {
        if (hbm == null) {
            hbm = new HibernateHelper(this);
        }
        return hbm;
    }
    public void addContactToGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectContactById(contact.id());
        selectGroupForAction(group.id());
        clickAddToButton();
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectGroupInFilter(group.id());
        selectContactById(contact.id());
        clickRemoveFromButton();
    }

    private void openHomePage() {
        if (!isElementPresent(By.id("maintable"))) {
            driver.findElement(By.linkText("home")).click();
        }
    }
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void selectContactById(String id) {
        driver.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    private void selectGroupForAction(String groupId) {
        WebElement groupSelect = driver.findElement(By.name("to_group"));
        Select select = new Select(groupSelect);
        select.selectByValue(groupId);
    }

    private void selectGroupInFilter(String groupId) {
        WebElement groupFilter = driver.findElement(By.name("group"));
        Select select = new Select(groupFilter);
        select.selectByValue(groupId);
    }

    private void clickAddToButton() {
        driver.findElement(By.cssSelector("input[value='Add to']")).click();
    }

    private void clickRemoveFromButton() {
        driver.findElement(By.name("remove")).click();
        acceptAlert();
    }

    private void acceptAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // Алерт не появился, продолжаем
        }
    }

    public boolean isContactInGroup(ContactData contact, GroupData group) {
        var contactsInGroup = hbm().getContactsInGroup(group);
        return contactsInGroup.stream()
                .anyMatch(c -> c.id().equals(contact.id()));
    }

    public List<ContactData> getContactsNotInGroup(GroupData group) {
        var allContacts = hbm().getContactList();
        var contactsInGroup = hbm().getContactsInGroup(group);
        return allContacts.stream()
                .filter(c -> contactsInGroup.stream().noneMatch(gc -> gc.id().equals(c.id())))
                .collect(Collectors.toList());
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return hbm().getContactsInGroup(group);
    }
}