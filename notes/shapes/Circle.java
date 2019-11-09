package notes.shapes;

/**
 * This class represents the geometric shape of a circle
 * 
 * @author Robert Schaedler III
 */

public class Circle extends Shape {

    private double radius;

    Circle(double radius, String color) {
        super(color); // calls constructor for Shape
        this.radius = radius;
    }

    // Methods

    /**
     * Gets the radius of the circle
     * 
     * @return double return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the redius of the circle
     * 
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String toString() {
        return "I am a circle of radius " + radius;
    }

    public double area() {
        return Math.PI * this.radius * this.radius;
    }

}