package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;


public class Geometry {
    public static void main(String[] args) {
        Square.printSquareArea(new Square(7.0));
        Square.printSquareArea(new Square(5.0));
        Square.printSquareArea(new Square(4.0));

        Square.printSquarePerimeter(new Square(8.0));

        Rectangle.printRectangleArea(3.0, 5.0);
        Rectangle.printRectangleArea(7.0, 9.0);

        Rectangle.printRectanglePerimeter(3.0, 5.0);

        Triangle.printTriangleArea(7, 4, 5);

        Triangle.printTrianglePerimeter(7, 4, 5);

    }
}
