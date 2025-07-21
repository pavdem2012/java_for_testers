package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TriangleTest {
    @Test
    void canCalculatePerimeter() {
        var triangle = new Triangle(3.0, 4.0, 5.0);
        Assertions.assertEquals(12.0, triangle.perimeter());
    }


    @Test
    void canCalculateArea() {
        var triangle = new Triangle(3.0, 4.0, 5.0);
        Assertions.assertEquals(6.0, triangle.area());
    }

}
