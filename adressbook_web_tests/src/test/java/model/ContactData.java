package model;

import java.util.Objects;

public record ContactData(String firstName, String middleName, String lastName, String nickname, String photo,
                          String title, String company, String address, String homePhone, String mobilePhone,
                          String workPhone, String fax, String email, String email2, String email3, String homepage,
                          String birthdayDay, String birthdayMonth, String birthdayYear, String anniversaryDay,
                          String anniversaryMonth, String anniversaryYear) {

    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "-", "-", "", "-", "-", "");
    }


    public ContactData withFirstName(String firstName) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withMiddleName(String middleName) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withNickname(String nickname) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withPhoto(String photo) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withTitle(String title) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withCompany(String company) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withAddress(String address) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withHomePhone(String homePhone) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withWorkPhone(String workPhone) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withFax(String fax) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withEmail1(String email) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withEmail2(String email2) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withEmail3(String email3) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withHomepage(String homepage) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withBirthdayDay(String birthdayDay) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withBirthdayMonth(String birthdayMonth) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }


    public ContactData withBirthdayYear(String birthdayYear) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withAnniversaryDay(String anniversaryDay) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withAnniversaryMonth(String anniversaryMonth) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withAnniversaryYear(String anniversaryYear) {
        return new ContactData(firstName, middleName, lastName, nickname, photo, title, company, address, homePhone, mobilePhone, workPhone, fax, email, email2, email3, homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay, anniversaryMonth, anniversaryYear);
    }

    public ContactData withGroup(String group) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }


}