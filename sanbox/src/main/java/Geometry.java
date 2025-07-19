public class Geometry {
    public static void main(String[] args) {
        printSquareArea(7.0);
        printSquareArea(5.0);
        printSquareArea(4.0);


        printRectangleArea(3.0, 5.0);
        printRectangleArea(7,9);

        var side = 7.;
        System.out.println("Площадь квадрата со стороной " + side + " = " + (side * side));

        side = 7.;
        System.out.println("Площадь квадрата со стороной " + side + " = " + (side * side));

        side = 7.;
        System.out.println("Площадь квадрата со стороной " + side + " = " + (side * side));

    }

    private static void printRectangleArea(double a, double b) {
        System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + rectangleArea(a,b));
    }

    private static Double rectangleArea(double a, double b) {
        return a * b;
    }

    static void printSquareArea(double a){
        System.out.println("Площадь квадрата со стороной " + a + " = " + squareArea(a));

    }

    private static double squareArea(double a) {
        return a * a;
    }
}
