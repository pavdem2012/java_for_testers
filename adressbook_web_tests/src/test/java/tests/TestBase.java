package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static manager.HelperBase.driver;

public class TestBase {
    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() throws IOException {
        if (app == null) {
            var properties = new Properties();
            properties.load(new FileReader(System.getProperty("target", "local.properties")));
            app = new ApplicationManager();
            app.init(System.getProperty("browser", "chrome"), properties);
        }
    }

    @AfterEach
    void checkDatabaseConsistency() {
        app.jdbc().checkConsistency();
    }

    public void waitForDomLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
                ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete")
        );
    }

    public static String randomFile(String dir) {
        var fileNames = new File(dir).list();
        var rnd = new Random();
        var index = rnd.nextInt(fileNames.length);
        return Paths.get(dir, fileNames[index]).toString();
    }

    public static List<String> getAllImageRelativePaths() {
        List<String> imagePaths = new ArrayList<>();
        File imagesDir = new File("src/test/resources/images");
        if (!imagesDir.exists() || !imagesDir.isDirectory()) {
            throw new RuntimeException("Директория не найдена: " + imagesDir.getAbsolutePath());
        }
        File[] files = imagesDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imagePaths.add(imagesDir + file.getName());
                }
            }
        }
        return imagePaths;
    }

}
