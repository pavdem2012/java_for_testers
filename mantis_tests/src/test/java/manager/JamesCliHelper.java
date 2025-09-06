package manager;
import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

public class JamesCliHelper extends HelperBase{
    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }
    public void addUser(String email, String password) {
        CommandLine cmd = new CommandLine(
                "java",
                "-Dworking.directory=" + manager.property("james.workingDir"),
                "-cp",
                "james-server-jpa-app.jar:james-server-jpa-app.lib/*",
                "org.apache.james.cli.ServerCmd",
                "AddUser",  email, password);
        cmd.setWorkingDirectory(manager.property("james.workingDir"));
        CircularOutputStream out = new CircularOutputStream();
        cmd.copyOutputTo(out);
        cmd.execute();
        cmd.waitFor();
        System.out.println("Command output: " + out);
    }
}
