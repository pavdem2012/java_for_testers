package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;

import java.util.List;


public class Geometry {
    public static void main(String[] args) {
        var squares = List.of(new Square(7.0), new Square(5.0),new Square(4.0));
/*        for (Square square : squares) {
            Square.printArea(square);
        }*/
        squares.forEach(Square::printArea);


/*        Square.printArea(new Square(7.0));
        Square.printArea(new Square(5.0));
        Square.printArea(new Square(4.0));*/

        Square.printPerimeter(new Square(8.0));

        Rectangle.printRectangleArea(new Rectangle(3.0, 5.0));
        Rectangle.printRectangleArea(new Rectangle(7.0, 9.0));

        Rectangle.printRectanglePerimeter(new Rectangle(3.0, 5.0));

        Triangle.printArea(new Triangle(7.0, 4.0, 5.0));

        Triangle.printPerimeter(new Triangle(7.0, 4.0, 5.0));

    }
}
