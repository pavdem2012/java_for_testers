package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RectangleTest {

    @Test
    void canCalculateArea() {
        var Rectangle = new Rectangle(5.0, 4.0);
        double result = Rectangle.rectangleArea();
        Assertions.assertEquals(20.0, result);
    }

    @Test
    void canCalculatePerimeter() {
        var Rectangle = new Rectangle(5.0, 4.0);
        double result = Rectangle.rectanglePerimeter();
        Assertions.assertEquals(18.0, result);
    }

    @Test
    void cannotCreateRectangleWithNegativeASide() {
        try {
            new Rectangle(-5.0, 3.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
    @Test
    void cannotCreateRectangleWithNegativeBSide() {
        try {
            new Rectangle(5.0, -3.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
    @Test
    void cannotCreateRectangleWithNegativeSides() {
        try {
            new Rectangle(-5.0, -3.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
}