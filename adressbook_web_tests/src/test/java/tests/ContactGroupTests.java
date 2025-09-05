package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ContactGroupTests extends TestBase {

    @BeforeEach
    void ensurePreconditions() throws InterruptedException {
        if (app.hbm().getGroupCount() == 0) {
            app.groups().createGroup(new GroupData()
                    .withName("Test Group " + System.currentTimeMillis()));
        }

        if (app.hbm().getContactCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("Test")
                    .withLastName("Contact " + System.currentTimeMillis()));
        }
    }

    @Test
    void canAddContactToGroup() {
        var groups = app.hbm().getGroupList();
        GroupData targetGroup = null;
        ContactData contactToAdd = null;

        // Ищем группу с контактами не в группе
        for (var group : groups) {
            var contactsNotInGroup = app.getContactsNotInGroup(group);
            System.out.println("Group " + group.name() + " has " + contactsNotInGroup.size() + " contacts not in group");
            if (!contactsNotInGroup.isEmpty()) {
                targetGroup = group;
                contactToAdd = contactsNotInGroup.get(0);
                break;
            }
        }

        // Если не нашли, создаем новый контакт
        if (targetGroup == null) {
            targetGroup = groups.get(0);
            var newContact = new ContactData()
                    .withFirstName("New Contact " + System.currentTimeMillis())
                    .withLastName("For Group Test");
            app.contacts().createContact(newContact);

            // Ищем созданный контакт
            contactToAdd = findContactByName(newContact.firstName());
            System.out.println("Created new contact: " + contactToAdd.firstName() + " " + contactToAdd.lastName());
        }

        // Проверяем, что контакт действительно не в группе
        boolean isInGroupBefore = app.isContactInGroup(contactToAdd, targetGroup);

        Assertions.assertFalse(isInGroupBefore, "Контакт уже находится в группе перед добавлением");

        // Добавляем контакт в группу через UI
        app.addContactToGroup(contactToAdd, targetGroup);

        // Проверяем через БД, что контакт добавлен в группу
        boolean isInGroupAfter = app.isContactInGroup(contactToAdd, targetGroup);
        Assertions.assertTrue(isInGroupAfter, "Контакт не был добавлен в группу");
    }

    @Test
    void canRemoveContactFromGroup() {
        var groups = app.hbm().getGroupList();
        GroupData targetGroup = null;
        ContactData contactToRemove = null;

        // Ищем группу с контактами
        for (var group : groups) {
            var contactsInGroup = app.getContactsInGroup(group);
            System.out.println("Group " + group.name() + " has " + contactsInGroup.size() + " contacts in group");
            if (!contactsInGroup.isEmpty()) {
                targetGroup = group;
                contactToRemove = contactsInGroup.get(0);
                break;
            }
        }

        // Если не нашли, добавляем контакт в группу через UI (а не через Hibernate)
        if (targetGroup == null) {
            targetGroup = groups.get(0);
            var allContactsList = app.hbm().getContactList();
            contactToRemove = allContactsList.get(0);

            // Добавляем через UI, а не через Hibernate
            app.addContactToGroup(contactToRemove, targetGroup);

        }

        // Проверяем, что контакт действительно в группе перед удалением
        boolean isInGroupBefore = app.isContactInGroup(contactToRemove, targetGroup);
        Assertions.assertTrue(isInGroupBefore, "Контакт не находится в группе перед удалением");

        // Удаляем контакт из группы через UI
        app.removeContactFromGroup(contactToRemove, targetGroup);

        // Проверяем через БД, что контакт удален из группы
        boolean isInGroupAfter = app.isContactInGroup(contactToRemove, targetGroup);
        Assertions.assertFalse(isInGroupAfter, "Контакт не был удален из группы");
    }

    private ContactData findContactByName(String firstName) {
        for (var contact : app.hbm().getContactList()) {
            if (contact.firstName().equals(firstName)) {
                return contact;
            }
        }
        throw new RuntimeException("Contact not found: " + firstName);
    }
}