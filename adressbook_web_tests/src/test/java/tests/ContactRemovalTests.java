package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }

        int contactCount = app.contacts().getCount();
        var oldContacts = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.contacts().getList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        int newGroupCont = app.contacts().getCount();
        Assertions.assertEquals(contactCount - 1, newGroupCont);
        Assertions.assertEquals(newContacts.size(), oldContacts.size() - 1);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void canRemoveAllContactsByMultipleCheckBox() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
        app.contacts().removeAllContactsMultiple();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(0, newContactCount);
    }

    @Test
    public void canRemoveContactByCheckBoxes() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
        app.contacts().removeAllContacts();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(0, newContactCount);
    }
}