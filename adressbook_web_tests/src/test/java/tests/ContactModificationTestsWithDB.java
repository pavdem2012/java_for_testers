package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTestsWithDB extends TestBase {

    @Test
    void canModifyContactWithFullDBCheck() {
        preconditionCheck();
        var oldContactsUI = app.contacts().getList();
        var oldContactsHbm = app.hbm().getContactList();

        var rnd = new Random();
        var index = rnd.nextInt(oldContactsUI.size());
        var contactToModify = oldContactsUI.get(index);

        var modifiedData = new ContactData()
                .withFirstName("ModifiedFirst" + System.currentTimeMillis())
                .withLastName("ModifiedLast")
                .withMiddleName("ModifiedMiddle")
                .withAddress("Modified Address " + System.currentTimeMillis())
                .withHomePhone("+7" + System.currentTimeMillis())
                .withMobilePhone("+7" + System.currentTimeMillis())
                .withEmail1("modified" + System.currentTimeMillis() + "@test.com")
                .withEmail2("modified2@test.com")
                .withBirthdayDay("10")
                .withBirthdayMonth("January")
                .withBirthdayYear("1985");

        app.contacts().modifyContact(contactToModify, modifiedData);

        // Получаем новые данные
        var newContactsUI = app.contacts().getList();
        var newContactsHbm = app.hbm().getContactList();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldContactsUI);
        var expectedHbmList = new ArrayList<>(oldContactsHbm);

        var expectedModifiedContact = modifiedData.withId(contactToModify.id());
        expectedUIList.set(index, expectedModifiedContact);
        expectedHbmList.set(index, expectedModifiedContact);

        // Сортируем все списки по ID
        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));

        expectedUIList.sort(compareById);
        expectedHbmList.sort(compareById);
        newContactsUI.sort(compareById);
        newContactsHbm.sort(compareById);

        // Проверки UI
        Assertions.assertEquals(expectedUIList, newContactsUI, "UI списки контактов не совпадают");

        // Проверки Hibernate с подробным сравнением
        Assertions.assertEquals(expectedHbmList.size(), newContactsHbm.size());

        for (int i = 0; i < expectedHbmList.size(); i++) {
            var expected = expectedHbmList.get(i);
            var actual = newContactsHbm.get(i);

            if (expected.id().equals(contactToModify.id())) {
                assertContactAttributesEqual(expected, actual);
            } else {
                // Для неизмененных контактов проверяем только основные поля
                Assertions.assertEquals(expected.id(), actual.id());
                Assertions.assertEquals(expected.firstName(), actual.firstName());
                Assertions.assertEquals(expected.lastName(), actual.lastName());
            }
        }

        // Проверка конкретного модифицированного контакта
        var modifiedContact = newContactsHbm.stream()
                .filter(c -> c.id().equals(contactToModify.id()))
                .findFirst()
                .orElseThrow();

        assertContactAttributesEqual(expectedModifiedContact, modifiedContact);
    }

    private void assertContactAttributesEqual(ContactData expected, ContactData actual) {
        Assertions.assertEquals(expected.firstName(), actual.firstName(), "FirstName не совпадает");
        Assertions.assertEquals(expected.middleName(), actual.middleName(), "MiddleName не совпадает");
        Assertions.assertEquals(expected.lastName(), actual.lastName(), "LastName не совпадает");
        Assertions.assertEquals(expected.address(), actual.address(), "Address не совпадает");
        Assertions.assertEquals(expected.homePhone(), actual.homePhone(), "HomePhone не совпадает");
        Assertions.assertEquals(expected.mobilePhone(), actual.mobilePhone(), "MobilePhone не совпадает");
        Assertions.assertEquals(expected.workPhone(), actual.workPhone(), "WorkPhone не совпадает");
        Assertions.assertEquals(expected.email(), actual.email(), "Email1 не совпадает");
        Assertions.assertEquals(expected.email2(), actual.email2(), "Email2 не совпадает");
        Assertions.assertEquals(expected.birthdayDay(), actual.birthdayDay(), "BirthdayDay не совпадает");
        Assertions.assertEquals(expected.birthdayMonth(), actual.birthdayMonth(), "BirthdayMonth не совпадает");
        Assertions.assertEquals(expected.birthdayYear(), actual.birthdayYear(), "BirthdayYear не совпадает");
    }

    private static void preconditionCheck() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
    }
}