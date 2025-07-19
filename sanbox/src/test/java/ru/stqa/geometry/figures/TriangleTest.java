package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TriangleTest {
    @Test
    void canCalculatePerimeter() {
        double result = Triangle.trianglePerimeter(3.0, 4.0, 5.0);
        Assertions.assertEquals(12.0, result);
    }

    @Test
    void canCalculateArea() {
        double result = Triangle.triangleArea(3.0, 4.0, 5.0);
        Assertions.assertEquals(6.0, result);
    }
}
