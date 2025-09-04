package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

public class ContactCreationTestsWithDB extends TestBase {

    @Test
    void canCreateContactWithFullDBCheck() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(8))
                .withLastName(CommonFunctions.randomString(10))
                .withMiddleName(CommonFunctions.randomString(6))
                .withNickname(CommonFunctions.randomString(5))
                .withTitle("Test Title")
                .withCompany("Test Company")
                .withAddress("Test Address " + CommonFunctions.randomString(5))
                .withHomePhone("+7" + CommonFunctions.randomString(10))
                .withMobilePhone("+7" + CommonFunctions.randomString(10))
                .withWorkPhone("+7" + CommonFunctions.randomString(10))
                .withFax("+7" + CommonFunctions.randomString(10))
                .withEmail1(CommonFunctions.randomString(5) + "@test.com")
                .withEmail2(CommonFunctions.randomString(5) + "@test.org")
                .withEmail3(CommonFunctions.randomString(5) + "@test.net")
                .withHomepage("https://" + CommonFunctions.randomString(5) + ".com")
                .withBirthdayDay("15")
                .withBirthdayMonth("June")
                .withBirthdayYear("1990")
                .withAnniversaryDay("20")
                .withAnniversaryMonth("July")
                .withAnniversaryYear("2010");

        var oldContactsUI = app.contacts().getList();
        var oldContactsHbm = app.hbm().getContactList();

        app.contacts().createContact(contact);

        // Получаем новые данные
        var newContactsUI = app.contacts().getList();
        var newContactsHbm = app.hbm().getContactList();

        // Определяем ID нового контакта
        var newContactId = newContactsUI.get(newContactsUI.size() - 1).id();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldContactsUI);
        var expectedHbmList = new ArrayList<>(oldContactsHbm);

        var expectedContact = contact.withId(newContactId);
        expectedUIList.add(expectedContact);
        expectedHbmList.add(expectedContact);

        // Сортируем все списки по ID
        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));

        expectedUIList.sort(compareById);
        expectedHbmList.sort(compareById);
        newContactsUI.sort(compareById);
        newContactsHbm.sort(compareById);

        // Проверки UI
        Assertions.assertEquals(expectedUIList, newContactsUI, "UI списки контактов не совпадают");

        // Проверки Hibernate с подробным сравнением атрибутов
        Assertions.assertEquals(expectedHbmList.size(), newContactsHbm.size(),
                "Количество контактов в Hibernate не совпадает");

        for (int i = 0; i < expectedHbmList.size(); i++) {
            var expected = expectedHbmList.get(i);
            var actual = newContactsHbm.get(i);

            assertContactAttributesEqual(expected, actual);
        }

        // Проверка количества
        Assertions.assertEquals(oldContactsUI.size() + 1, newContactsUI.size());
        Assertions.assertEquals(oldContactsHbm.size() + 1, newContactsHbm.size());

        // Проверка что контакт действительно создан в БД
        var createdContact = newContactsHbm.stream()
                .filter(c -> c.id().equals(newContactId))
                .findFirst()
                .orElseThrow();

        assertContactAttributesEqual(expectedContact, createdContact);
    }

    private void assertContactAttributesEqual(ContactData expected, ContactData actual) {
        Assertions.assertEquals(expected.id(), actual.id(), "ID не совпадает");
        Assertions.assertEquals(expected.firstName(), actual.firstName(), "FirstName не совпадает");
        Assertions.assertEquals(expected.middleName(), actual.middleName(), "MiddleName не совпадает");
        Assertions.assertEquals(expected.lastName(), actual.lastName(), "LastName не совпадает");
        Assertions.assertEquals(expected.nickname(), actual.nickname(), "Nickname не совпадает");
        Assertions.assertEquals(expected.title(), actual.title(), "Title не совпадает");
        Assertions.assertEquals(expected.company(), actual.company(), "Company не совпадает");
        Assertions.assertEquals(expected.address(), actual.address(), "Address не совпадает");
        Assertions.assertEquals(expected.homePhone(), actual.homePhone(), "HomePhone не совпадает");
        Assertions.assertEquals(expected.mobilePhone(), actual.mobilePhone(), "MobilePhone не совпадает");
        Assertions.assertEquals(expected.workPhone(), actual.workPhone(), "WorkPhone не совпадает");
        Assertions.assertEquals(expected.fax(), actual.fax(), "Fax не совпадает");
        Assertions.assertEquals(expected.email(), actual.email(), "Email1 не совпадает");
        Assertions.assertEquals(expected.email2(), actual.email2(), "Email2 не совпадает");
        Assertions.assertEquals(expected.email3(), actual.email3(), "Email3 не совпадает");
        Assertions.assertEquals(expected.homepage(), actual.homepage(), "Homepage не совпадает");
        Assertions.assertEquals(expected.birthdayDay(), actual.birthdayDay(), "BirthdayDay не совпадает");
        Assertions.assertEquals(expected.birthdayMonth(), actual.birthdayMonth(), "BirthdayMonth не совпадает");
        Assertions.assertEquals(expected.birthdayYear(), actual.birthdayYear(), "BirthdayYear не совпадает");
        Assertions.assertEquals(expected.anniversaryDay(), actual.anniversaryDay(), "AnniversaryDay не совпадает");
        Assertions.assertEquals(expected.anniversaryMonth(), actual.anniversaryMonth(), "AnniversaryMonth не совпадает");
        Assertions.assertEquals(expected.anniversaryYear(), actual.anniversaryYear(), "AnniversaryYear не совпадает");
    }

    @Test
    void canCreateMinimalContactWithDBCheck() {
        var contact = new ContactData()
                .withFirstName("Minimal")
                .withLastName("Contact");

        var oldContactsHbm = app.hbm().getContactList();

        app.contacts().createContact(contact);

        var newContactsHbm = app.hbm().getContactList();
        var createdContact = newContactsHbm.stream()
                .filter(c -> !oldContactsHbm.contains(c))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals("Minimal", createdContact.firstName());
        Assertions.assertEquals("Contact", createdContact.lastName());
        Assertions.assertEquals("", createdContact.middleName());
        Assertions.assertEquals("", createdContact.address());
    }
}