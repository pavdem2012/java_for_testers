package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
        var photoPaths = new ArrayList<>(getAllImageRelativePaths());
        for (var firstName : List.of("", "First")) {
            for (var lastName : List.of("", "Last")) {
                for (var email : List.of("", "test@example.com")) {
                    for (var ignored : photoPaths) {
                        result.add(new ContactData()
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withEmail1(email)
                                .withPhoto(randomFile("src/test/resources/images")));
                    }
                }
            }
        }
        var json = Files.readString(Paths.get("contacts.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>() {
        });
        result.addAll(value);
        return result;
    }

    public static List<ContactData> negativeContactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
        var json = Files.readString(Paths.get("negative_contacts.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>() {
        });
        result.addAll(value);
        return result;
    }


    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContacts(ContactData contact) {
        int contactCount = app.contacts().getCount();
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getCount();
        var newContacts = app.contacts().getList();
        Comparator<ContactData> fullComparator = Comparator
                .comparing((ContactData c) -> Integer.parseInt(c.id()))
                .thenComparing(ContactData::firstName)
                .thenComparing(ContactData::lastName);
        oldContacts.sort(fullComparator);
        newContacts.sort(fullComparator);
        var expectedList = new ArrayList<>(oldContacts);
        var newContactWithId = contact.withId(
                newContacts.get(newContacts.size() - 1).id() // Получаем ID нового контакта
        );
        expectedList.add(newContactWithId);
        expectedList.sort(fullComparator);
        Assertions.assertEquals(contactCount + 1, newContactCount);
        Assertions.assertEquals(expectedList, newContacts,
                "Списки контактов не совпадают после создания");
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        int contactCount = app.contacts().getCount();
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount, newContactCount);
        Assertions.assertEquals(oldContacts, newContacts,
                "Списки контактов не совпадают после создания");
    }

    @Test
    void canCreateContact() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        app.contacts().createContact(contact);
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name HBM Create", "group header HBM Create", "group footer HBM Create"));
        }
        var group = app.hbm().getGroupList().get(0);
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().createContact(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }
}