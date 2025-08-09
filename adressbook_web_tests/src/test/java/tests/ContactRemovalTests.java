package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
        int contactCount = app.contacts().getCount();
        app.contacts().removeContact();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount - 1, newContactCount);
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