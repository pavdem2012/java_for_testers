package ru.stqa.geometry.figures;

public record Triangle(double a, double b, double c) {
    public static void printArea(Triangle t) {
        System.out.printf("Площадь треугольника со сторонами %f, %f и %f = %f%n",
                t.a, t.b, t.c, t.area());
    }

    public static void printPerimeter(Triangle t) {
        System.out.printf("Периметр треугольника со сторонами %f, %f и %f = %f%n",
                t.a, t.b, t.c, t.perimeter());
    }

    public double area() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public double perimeter() {
        return a + b + c;
    }
}
