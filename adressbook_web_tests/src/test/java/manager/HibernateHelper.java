package manager;

import io.qameta.allure.Step;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase {
    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.USER, "root")
                .setProperty(AvailableSettings.PASS, "")
                .buildSessionFactory();
    }

    // Группы
    static List<GroupData> convertGroupList(List<GroupRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    @Step
    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    @Step
    public long getGroupCount() throws InterruptedException {
        Thread.sleep(50);
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    @Step
    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    // Контакты
    static List<ContactData> convertContactList(List<ContactRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static ContactData convert(ContactRecord record) {
        return new ContactData()
                .withId("" + record.id)
                .withFirstName(record.firstname)
                .withMiddleName(record.middlename)
                .withLastName(record.lastname)
                .withNickname(record.nickname)
                .withTitle(record.title)
                .withCompany(record.company)
                .withAddress(record.address)
                .withHomePhone(record.home)
                .withMobilePhone(record.mobile)
                .withWorkPhone(record.work)
                .withFax(record.fax)
                .withEmail1(record.email)
                .withEmail2(record.email2)
                .withEmail3(record.email3)
                .withHomepage(record.homepage)
                .withBirthdayDay(record.bday)
                .withBirthdayMonth(record.bmonth)
                .withBirthdayYear(record.byear)
                .withAnniversaryDay(record.aday)
                .withAnniversaryMonth(record.amonth)
                .withAnniversaryYear(record.ayear);
    }

    private static ContactRecord convert(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(
                Integer.parseInt(id),
                data.firstName(),
                data.middleName(),
                data.lastName(),
                data.nickname(),
                data.title(),
                data.company(),
                data.address(),
                data.homePhone(),
                data.mobilePhone(),
                data.workPhone(),
                data.fax(),
                data.email(),
                data.email2(),
                data.email3(),
                data.homepage(),
                data.birthdayDay(),
                data.birthdayMonth(),
                data.birthdayYear(),
                data.anniversaryDay(),
                data.anniversaryMonth(),
                data.anniversaryYear()
        );
    }

    @Step
    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }

    @Step
    public long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    @Step
    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            var groupRecord = session.get(GroupRecord.class, Integer.parseInt(group.id()));
            return convertContactList(groupRecord.contacts);
        });
    }

    @Step
    public ContactData getContactById(String id) {
        return sessionFactory.fromSession(session -> {
            var contactRecord = session.get(ContactRecord.class, Integer.parseInt(id));
            return convert(contactRecord);
        });
    }
}