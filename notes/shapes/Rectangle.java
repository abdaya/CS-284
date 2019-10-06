package notes.shapes;

import notes.pair.*;

/**
 * This class represents the geometric shape of a rectangle
 * 
 * @author Robert Schaedler III
 */

public class Rectangle extends Shape {

    // Instance Variables
    private double base;
    private double height;

    // Class Variables
    static private int numberOfRectangles = 0;

    // Constructors
    Rectangle(double base, double height, String color) {
        super(color); // calls constructor for Shape
        this.base = base;
        this.height = height;
        numberOfRectangles++;
    }

    Rectangle(double base, double height) {
        super("Blue"); // calls constructor for Shape
        this.base = base;
        this.height = height;
        numberOfRectangles++;
    }

    // Methods

    // Static (class) methods can only reference static (class) variables
    public static int getNumberOfRect() {
        return numberOfRectangles;
    }

    /**
     * Gets the base of the rectangle.
     * 
     * @return double return the base
     */
    public double getBase() {
        return base;
    }

    /**
     * Sets the base of the rectangle.
     * 
     * @param base the base to set
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * Gets the height of the rectangle.
     * 
     * @return double return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     * 
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    public String toString() {
        return "rectangle of base " + base + " and height " + height + ". " + super.toString();
    }

    // implements a method area() of the abstract class Shape
    public double area() {
        return this.base * this.height;
    }

    public Pair<Double, Double> getInfo() {
        return new Pair<Double, Double>(this.height, this.base);
    }

}