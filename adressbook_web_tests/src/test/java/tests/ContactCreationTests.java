package tests;

import manager.ContactsHelper;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        var photoPaths = List.of("", "src/test/resources/avatar.jpeg", "src/test/resources/хакатон.jpg");
        for (var firstName : List.of("", "First")) {
            for (var lastName : List.of("", "Last")) {
                for (var email : List.of("", "test@example.com")) {
                    for (var photo : photoPaths) {
                        result.add(new ContactData()
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withEmail1(email)
                                .withPhoto(photo));
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(ContactsHelper.generateRandomString(i + 5))
                    .withMiddleName(ContactsHelper.generateRandomString(i + 5))
                    .withLastName(ContactsHelper.generateRandomString(i + 5))
                    .withNickname(ContactsHelper.generateRandomString(i + 5))
                    .withPhoto(photoPaths.get(i % photoPaths.size()))
                    .withTitle(ContactsHelper.generateRandomString(i + 10))
                    .withCompany(ContactsHelper.generateRandomString(i + 10))
                    .withAddress(ContactsHelper.generateRandomString(i + 15))
                    .withHomePhone(ContactsHelper.generateRandomPhone())
                    .withMobilePhone(ContactsHelper.generateRandomPhone())
                    .withWorkPhone(ContactsHelper.generateRandomPhone())
                    .withFax(ContactsHelper.generateRandomString(i + 7))
                    .withEmail1(ContactsHelper.generateRandomEmail())
                    .withEmail2(ContactsHelper.generateRandomEmail())
                    .withEmail3(ContactsHelper.generateRandomEmail())
                    .withHomepage("https://" + ContactsHelper.generateRandomString(5) + ".com")
                    .withBirthdayDay(ContactsHelper.generateRandomDay())
                    .withBirthdayMonth(ContactsHelper.generateRandomMonth())
                    .withBirthdayYear(ContactsHelper.generateRandomYear(1900, 2000))
                    .withAnniversaryDay(ContactsHelper.generateRandomDay())
                    .withAnniversaryMonth(ContactsHelper.generateRandomMonth())
                    .withAnniversaryYear(ContactsHelper.generateRandomYear(2000, 2025))
                    .withGroup(ContactsHelper.generateRandomGroup()));
        }
        return result;
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(ContactsHelper.generateRandomString(i + 5)+"'")
                    .withMiddleName(ContactsHelper.generateRandomString(i + 5)+"'")
                    .withLastName(ContactsHelper.generateRandomString(i + 5)+"'")
                    .withNickname(ContactsHelper.generateRandomString(i + 5)+"'")
                    .withTitle(ContactsHelper.generateRandomString(i + 10)+"'")
                    .withCompany(ContactsHelper.generateRandomString(i + 10)+"'")
                    .withAddress(ContactsHelper.generateRandomString(i + 15)+"'")
                    .withHomePhone(ContactsHelper.generateRandomPhone()+"'")
                    .withMobilePhone(ContactsHelper.generateRandomPhone()+"'")
                    .withWorkPhone(ContactsHelper.generateRandomPhone()+"'")
                    .withFax(ContactsHelper.generateRandomString(i + 7)+"'")
                    .withEmail1(ContactsHelper.generateRandomEmail()+"'")
                    .withEmail2(ContactsHelper.generateRandomEmail()+"'")
                    .withEmail3(ContactsHelper.generateRandomEmail()+"'")
                    .withHomepage("https://" + ContactsHelper.generateRandomString(5) + ".com"+"'")
                    .withBirthdayDay(ContactsHelper.generateRandomDay())
                    .withBirthdayMonth(ContactsHelper.generateRandomMonth())
                    .withBirthdayYear(ContactsHelper.generateRandomYear(1900, 2000)+"'")
                    .withAnniversaryDay(ContactsHelper.generateRandomDay())
                    .withAnniversaryMonth(ContactsHelper.generateRandomMonth())
                    .withAnniversaryYear(ContactsHelper.generateRandomYear(2000, 2025)+"'")
                    .withGroup(ContactsHelper.generateRandomGroup()));
        }
        return result;
    }


    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        int contactCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        int contactCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount, newContactCount);
    }
}