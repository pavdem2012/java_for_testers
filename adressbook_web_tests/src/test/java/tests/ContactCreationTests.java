package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContactWithAllFields() {
        app.contacts().createContact(new ContactData()
                .withFirstName("First")
                .withMiddleName("Middle")
                .withLastName("Last")
                .withNickname("Nick")
                .withPhoto("src/test/resources/хакатон.jpg")
                .withTitle("Title")
                .withCompany("Company")
                .withAddress("Address")
                .withHomePhone("TelHome")
                .withMobilePhone("TelMobile")
                .withWorkPhone("TelWork")
                .withFax("Fax")
                .withEmail1("email1@emaill.email")
                .withEmail2("email2@email.email")
                .withEmail3("email3@email.email")
                .withHomepage("www.homepage.page")
                .withBirthdayDay("16")
                .withBirthdayMonth("March")
                .withBirthdayYear("1975")
                .withAnniversaryDay("16")
                .withAnniversaryMonth("March")
                .withAnniversaryYear("2025"));
    }

    @Test
    public void canCreateContactWithMinimumData() {
        app.contacts().createContact(new ContactData()
                .withFirstName("First")
                .withLastName("Last"));
    }

    @Test
    public void canCreateContactWithNllData() {
        app.contacts().createContact(new ContactData());
    }
}