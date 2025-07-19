package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RectangleTest {

    @Test
    void canCalculateArea() {
        double result = Rectangle.rectangleArea(5.0, 4.0);
        Assertions.assertEquals(20.0, result);
    }

    @Test
    void canCalculatePerimeter() {
        double result = Rectangle.rectanglePerimeter(5.0, 4.0);
        Assertions.assertEquals(18.0, result);
    }
}