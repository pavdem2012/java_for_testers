package ru.stqa.geometry.figures;


public record Rectangle(double a, double b) {
    public Rectangle {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Rectangle side should be non-negative");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return (Double.compare(rectangle.a, this.a) == 0 && Double.compare(rectangle.b, this.b) == 0)
                || (Double.compare(rectangle.a, this.b) == 0 && Double.compare(rectangle.b, this.a) == 0);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
