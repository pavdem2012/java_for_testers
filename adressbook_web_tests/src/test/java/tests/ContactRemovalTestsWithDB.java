package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactRemovalTestsWithDB extends TestBase {

    @Test
    public void canRemoveContactWithFullDBCheck() {
        preconditionCheck();
        var oldContactsUI = app.contacts().getList();
        var oldContactsHbm = app.hbm().getContactList();

        var rnd = new Random();
        var index = rnd.nextInt(oldContactsUI.size());
        var contactToRemove = oldContactsUI.get(index);

        app.contacts().removeContact(contactToRemove);

        // Получаем новые данные
        var newContactsUI = app.contacts().getList();
        var newContactsHbm = app.hbm().getContactList();

        // Подготавливаем ожидаемые списки
        var expectedUIList = new ArrayList<>(oldContactsUI);
        var expectedHbmList = new ArrayList<>(oldContactsHbm);

        expectedUIList.remove(index);
        expectedHbmList.remove(index);

        // Сортируем все списки по ID
        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));

        expectedUIList.sort(compareById);
        expectedHbmList.sort(compareById);
        newContactsUI.sort(compareById);
        newContactsHbm.sort(compareById);

        // Проверки
        Assertions.assertEquals(expectedUIList, newContactsUI, "UI списки не совпадают");
        Assertions.assertEquals(expectedHbmList, newContactsHbm, "Hibernate списки не совпадают");

        // Проверка количества
        Assertions.assertEquals(oldContactsUI.size() - 1, newContactsUI.size());
        Assertions.assertEquals(oldContactsHbm.size() - 1, newContactsHbm.size());

        // Проверка что контакт действительно удален из БД
        boolean contactExistsInHbm = newContactsHbm.stream()
                .anyMatch(c -> c.id().equals(contactToRemove.id()));

        Assertions.assertFalse(contactExistsInHbm, "Контакт все еще существует в Hibernate");

        // Проверка что остальные контакты не изменились
        for (int i = 0; i < expectedHbmList.size(); i++) {
            var expected = expectedHbmList.get(i);
            var actual = newContactsHbm.get(i);

            Assertions.assertEquals(expected.id(), actual.id());
            Assertions.assertEquals(expected.firstName(), actual.firstName());
            Assertions.assertEquals(expected.lastName(), actual.lastName());
            Assertions.assertEquals(expected.address(), actual.address());
        }
    }

    @Test
    public void canRemoveAllContactsWithDBCheck() {
        preconditionCheck();
        app.contacts().removeAllContacts();

        var uiCount = app.contacts().getCount();
        var hbmCount = app.hbm().getContactCount();

        Assertions.assertEquals(0, uiCount, "Не все контакты удалены из UI");
        Assertions.assertEquals(0, hbmCount, "Не все контакты удалены из Hibernate");

        var hbmContacts = app.hbm().getContactList();
        Assertions.assertTrue(hbmContacts.isEmpty(), "Список Hibernate не пустой");
    }

    private static void preconditionCheck() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
    }
}