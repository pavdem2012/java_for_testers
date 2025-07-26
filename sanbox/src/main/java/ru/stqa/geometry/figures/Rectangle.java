package ru.stqa.geometry.figures;

public record Rectangle(double a, double b) {
    public Rectangle {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Square side should be non-negative");
        }

    }

    public static void printRectangleArea(Rectangle r) {
        var text = String.format("Площадь прямоугольника со сторонами %f b %f = %f", r.a, r.b, r.rectangleArea());

        System.out.println(text);
    }

    public static void printRectanglePerimeter(Rectangle r) {
        var text = String.format("Периметер прямоугольника со сторонами %f b %f = %f", r.a, r.b, r.rectanglePerimeter());

        System.out.println(text);
    }

    public double rectangleArea() {
        return a * b;
    }

    public double rectanglePerimeter() {
        return (a + b) * 2;
    }
}
