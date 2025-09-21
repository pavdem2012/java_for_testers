import java.io.File;

public class Hello {
    public static void main(String[] args) {
        var x = 1;
        var y = 1;
        //var y = 0;
        if (y == 0) {
            System.out.println("Division by zero is not allowed");
        } else {
            var z = divide(x, y);
            System.out.println(z);
            System.out.println("Hello world!!!");
        }
        System.out.println("Hello world!!!");


        System.out.println(2 + 2);
        System.out.println(2 * 2);
        System.out.println(2 - 2);
        System.out.println(2 / 2);
        System.out.println(2 + 2 * 2);
        System.out.println((2 + 2) * 2);
        System.out.println("Hello " + "world!!!");
        System.out.println("2+2 = " + 4);
        System.out.println("2+2 = " + 2 + 2);
        System.out.println("2+2 = " + (2 + 2));

        var configFile = new File("sanbox/build.gradle");
        System.out.println(configFile.getAbsoluteFile());

        System.out.println(configFile.exists());
    }

    private static int divide(int x, int y) {
        var z = x / y;
        return z;
    }
}
