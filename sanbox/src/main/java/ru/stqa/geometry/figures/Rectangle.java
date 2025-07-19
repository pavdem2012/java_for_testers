package ru.stqa.geometry.figures;

public record Rectangle(double a, double b) {

    public static void printRectangleArea(double a, double b) {
        var text = String.format("Площадь прямоугольника со сторонами %f b %f = %f", a, b, rectangleArea(a,b));

        System.out.println(text);
    }

    public static void printRectanglePerimeter(double a, double b) {
        var text = String.format("Периметер прямоугольника со сторонами %f b %f = %f", a, b, rectanglePerimeter(a,b));

        System.out.println(text);
    }

    static Double rectangleArea(double a, double b) {
        return a * b;
    }
    static Double rectanglePerimeter(double a, double b) {
        return (a + b)*2;
    }
}
