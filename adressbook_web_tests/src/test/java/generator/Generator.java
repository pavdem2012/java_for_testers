package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.CommonFunctions;
import manager.ContactsHelper;
import model.ContactData;
import model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static manager.ContactsHelper.*;

public class Generator {
    @Parameter(names = {"--type", "-t"}, description = "Тип данных (groups/contacts)", required = true)
    String type;
    @Parameter(names = {"--output", "-o"}, description = "Выходной файл", required = true)
    String output;
    @Parameter(names = {"--format", "-f"}, description = "Формат данных (json)", required = true)
    String format;
    @Parameter(names = {"--count", "-c"}, description = "Количество записей")
    int count;
    @Parameter(names = {"--negative", "-n"}, description = "Генерация негативных данных")
    boolean negative = false;
    private final Random random = new Random();

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        var data = generate();
        save(data);
    }

    private Object generate() {
        if ("groups".equals(type)) {
            return negative ? generateNegativeGroups() : generateGroups();
        } else if ("contacts".equals(type)) {
            return negative ? generateNegativeContacts() : generateContacts();
        } else {
            throw new IllegalArgumentException("Неизвестный тип данных" + type);
        }
    }


    private Object generateGroups() {
        var result = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            result.add(new GroupData()
                    .withName(CommonFunctions.randomString(i + 10))
                    .withHeader(CommonFunctions.randomString(i + 10))
                    .withFooter(CommonFunctions.randomString(i + 10)));
        }
        return result;
    }

    private Object generateNegativeGroups() {
        var result = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            result.add(new GroupData()
                    .withName(CommonFunctions.randomString(i + 10) + "'")
                    .withHeader(CommonFunctions.randomString(i + 10) + "'")
                    .withFooter(CommonFunctions.randomString(i + 10) + "'"));
        }
        return result;
    }

    private Object generateContacts() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            result.add(new ContactData()
                    .withFirstName(ContactsHelper.generateRandomString(i + 5))
                    .withMiddleName(ContactsHelper.generateRandomString(i + 5))
                    .withLastName(ContactsHelper.generateRandomString(i + 5))
                    .withNickname(ContactsHelper.generateRandomString(i + 5))
                    .withPhoto(randomFile("src/test/resources/images"))
                    .withTitle(ContactsHelper.generateRandomString(i + 10))
                    .withCompany(ContactsHelper.generateRandomString(i + 10))
                    .withAddress(ContactsHelper.generateRandomString(i + 15))
                    .withHomePhone(ContactsHelper.generateRandomPhone())
                    .withMobilePhone(ContactsHelper.generateRandomPhone())
                    .withWorkPhone(ContactsHelper.generateRandomPhone())
                    .withFax(ContactsHelper.generateRandomString(i + 7))
                    .withEmail1(ContactsHelper.generateRandomEmail())
                    .withEmail2(ContactsHelper.generateRandomEmail())
                    .withEmail3(ContactsHelper.generateRandomEmail())
                    .withHomepage("https://" + ContactsHelper.generateRandomString(5) + ".com")
                    .withBirthdayDay(ContactsHelper.generateRandomDay())
                    .withBirthdayMonth(ContactsHelper.generateRandomMonth())
                    .withBirthdayYear(ContactsHelper.generateRandomYear(1900, 2000))
                    .withAnniversaryDay(ContactsHelper.generateRandomDay())
                    .withAnniversaryMonth(ContactsHelper.generateRandomMonth())
                    .withAnniversaryYear(ContactsHelper.generateRandomYear(2000, 2025))
                    .withGroup(ContactsHelper.generateRandomGroup()));
        }
        return result;
    }

    private Object generateNegativeContacts() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            result.add(new ContactData()
                    .withFirstName(CommonFunctions.randomString(i + 5) + "'")
                    .withMiddleName(CommonFunctions.randomString(i + 5) + "'")
                    .withLastName(CommonFunctions.randomString(i + 5) + "'")
                    .withNickname(CommonFunctions.randomString(i + 5) + "'")
                    .withPhoto(randomFile("src/test/resources/images"))
                    .withTitle(CommonFunctions.randomString(i + 5) + "'")
                    .withCompany(CommonFunctions.randomString(i + 5) + "'")
                    .withAddress(CommonFunctions.randomString(i + 10) + "'")
                    .withHomePhone(generateRandomPhone() + "'")
                    .withMobilePhone(generateRandomPhone() + "'")
                    .withWorkPhone(generateRandomPhone() + "'")
                    .withFax(CommonFunctions.randomString(i + 5) + "'")
                    .withEmail1(generateRandomEmail() + "'")
                    .withEmail2(generateRandomEmail() + "'")
                    .withEmail3(generateRandomEmail() + "'")
                    .withHomepage("https://" + CommonFunctions.randomString(5) )
                    .withBirthdayDay(generateRandomDay() )
                    .withBirthdayMonth(generateRandomMonth() )
                    .withBirthdayYear(generateRandomYear(1900, 2000))
                    .withAnniversaryDay(generateRandomDay())
                    .withAnniversaryMonth(generateRandomMonth())
                    .withAnniversaryYear(generateRandomYear(2000, 2025)));
        }
        return result;
    }
    private String randomFile(String dir) {
        var files = new File(dir).listFiles();
        if (files == null || files.length == 0) {
            return "";
        }
        return files[random.nextInt(files.length)].getPath();
    }



    private void save(Object data) throws IOException {
        if ("json".equals(format)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            var json = mapper.writeValueAsString(data);
            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
        } else {
            throw new IllegalArgumentException("Неизвестный формат данных " + format);
        }
    }
}
