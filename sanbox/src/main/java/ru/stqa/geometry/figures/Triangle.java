package ru.stqa.geometry.figures;

public class Triangle {

    public static void printTriangleArea(double a, double b, double c) {
        var text = String.format("Площадь треугольника со сторонами %f, %f и %f = %f",
                a, b, c, triangleArea(a, b, c));
        System.out.println(text);
    }

    public static void printTrianglePerimeter(double a, double b, double c) {
        var text = String.format("Периметр треугольника со сторонами %f, %f и %f = %f",
                a, b, c, trianglePerimeter(a, b, c));
        System.out.println(text);
    }

    public static double triangleArea(double a, double b, double c) {
        double p = trianglePerimeter(a, b, c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public static double trianglePerimeter(double a, double b, double c) {
        return a + b + c;
    }
}
