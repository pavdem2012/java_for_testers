package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ContactsHelper extends HelperBase {
    TestBase TestBase = new TestBase();

    public ContactsHelper(ApplicationManager manager) {
        super(manager);
    }

    public static String generateRandomString(int length) {
        var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        var random = new Random();
        return random.ints(length, 0, chars.length())
                .mapToObj(i -> String.valueOf(chars.charAt(i)))
                .collect(Collectors.joining());
    }

    public static String generateRandomEmail() {
        return generateRandomString(5) + "@" + generateRandomString(3) + ".com";
    }

    public static String generateRandomPhone() {
        return "+" + (1000000 + new Random().nextInt(9000000));
    }

    public static String generateRandomDay() {
        return String.valueOf(new Random().nextInt(1, 32));
    }

    public static String generateRandomMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[new Random().nextInt(months.length)];
    }

    public static String generateRandomYear(int min, int max) {
        return String.valueOf(min + new Random().nextInt(max - min));
    }

    public static String generateRandomGroup() {
        String[] groups = {"[none]", "eeanszjhts", "group name", "maeupculylg"};
        return groups[new Random().nextInt(groups.length)];
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
    }

    public int getCount() {

        TestBase.waitForDomLoaded();
        ContactsHelper.openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    private static void openHomePage() {
        if (!isElementPresent(By.id("maintable"))) {
            click(By.linkText("home"));
        }
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("nickname"), contact.nickname());
        if (!contact.photo().isEmpty()) {
            attach(By.name("photo"), contact.photo());
        }
        type(By.name("title"), contact.title());
        type(By.name("company"), contact.company());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.homePhone());
        type(By.name("mobile"), contact.mobilePhone());
        type(By.name("work"), contact.workPhone());
        type(By.name("fax"), contact.fax());
        type(By.name("email"), contact.email());
        type(By.name("email2"), contact.email2());
        type(By.name("email3"), contact.email3());
        type(By.name("homepage"), contact.homepage());
        select(By.name("bday"), contact.birthdayDay());
        select(By.name("bmonth"), contact.birthdayMonth());
        type(By.name("byear"), contact.birthdayYear());
        select(By.name("aday"), contact.anniversaryDay());
        select(By.name("amonth"), contact.anniversaryMonth());
        type(By.name("ayear"), contact.anniversaryYear());
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }


    public List<ContactData> getList() {
        openHomePage();
        var contacts = new ArrayList<ContactData>();
        var rows = manager.driver.findElements(By.cssSelector("tr[name=entry]"));
        for (var row : rows) {
            var lastName = row.findElement(By.xpath("./td[2]")).getText();
            var firstName = row.findElement(By.xpath("./td[3]")).getText();
            var id = row.findElement(By.name("selected[]")).getAttribute("value");
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName));
        }
        return contacts;
    }


    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContactById(contact.id());
        removeSelectedContacts();
    }

    public boolean isContactPresent() {
        return isElementPresent(By.name("selected[]"));
    }

    private void openContactsPage() {
        if (!isElementPresent(By.name("searchstring"))) {
            click(By.linkText("home"));
        }
    }

    /*  private void selectFirstContact() {
        click(By.name("selected[]"));
    }*/

    private void selectContactById(String id) {
        manager.driver.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
    }
    /*Не смог ничего сделать с этой командой*/
    //driver.switchTo().alert().accept();


    public void removeAllContacts() {
        openContactsPage();
        selectAllContacts();
        removeSelectedContacts();
    }

    private void removeSelectedContacts() {
        click(By.xpath(" //input[@value='Delete']"));
        //driver.switchTo().alert().accept();
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public void removeAllContactsMultiple() {
        openContactsPage();
        selectAllContactsMultiple();
        removeSelectedContacts();
    }

    private void selectAllContactsMultiple() {
        manager.driver.findElement(By.id("MassCB")).click();
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openHomePage();
        //selectContactById(contact.id());
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
    }

    private void initContactModification(ContactData contact) {
        click(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.id())));
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact, GroupData group) {
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }
}