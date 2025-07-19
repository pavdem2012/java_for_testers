package ru.stqa.geometry.figures;

public class Square {

    double side;

    public Square(double side) {
        this.side = side;
    }

    public static void printSquareArea(Square s){
        String text = String.format("Площадь квадрата со стороной %f = %f" , s.side, s.area());
        System.out.println(text);
    }

    public static void printSquarePerimeter(Square s){
        String text = String.format("Периметер квадрата со стороной %f = %f" , s.side, s.area());
        System.out.println(text);
    }


    public double area() {
        return this.side * this.side;
    }

    public double perimeter() {
        return this.side*4;
    }
}
