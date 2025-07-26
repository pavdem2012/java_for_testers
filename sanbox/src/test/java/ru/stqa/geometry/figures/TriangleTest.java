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

    @Test
    void cannotCreateTriangleWithNegativeASide() {
        try {
            new Triangle(-3.0, 4.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateTriangleWithNegativeBSide() {
        try {
            new Triangle(3.0, -4.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateTriangleWithNegativeCSide() {
        try {
            new Triangle(3.0, 4.0, -5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateTriangleWithNegativeSides() {
        try {
            new Triangle(-3.0, -4.0, -5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateInvalidTriangle1() {
        try {
            new Triangle(1.0, 2.0, 5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateInvalidTriangle2() {
        try {
            new Triangle(5.0, 2.0, 1.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void cannotCreateInvalidTriangle3() {
        try {
            new Triangle(2.0, 5.0, 1.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }

    @Test
    void canCreateEquilateralTriangle() {
        var triangle = new Triangle(2.0, 2.0, 2.0);
        Assertions.assertEquals(3.0, triangle.perimeter() / 2);
    }


}
