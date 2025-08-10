package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData()
                    .withFirstName("First")
                    .withLastName("Last"));
        }
        List<ContactData> oldContacts = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        ContactData contactToModify = oldContacts.get(index);
        ContactData modifiedData = new ContactData()
                .withFirstName("Modified First")
                .withLastName("Modified Last")
                .withEmail1("modified@test.com");
        app.contacts().modifyContact(contactToModify, modifiedData);
        List<ContactData> newContacts = app.contacts().getList();
        List<ContactData> expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, modifiedData.withId(contactToModify.id()));
        Comparator<ContactData> fullComparator = Comparator
                .comparingInt((ContactData c) -> Integer.parseInt(c.id()))
                .thenComparing(ContactData::firstName)
                .thenComparing(ContactData::lastName);
        newContacts.sort(fullComparator);
        expectedList.sort(fullComparator);
        Assertions.assertEquals(expectedList, newContacts);
    }
}