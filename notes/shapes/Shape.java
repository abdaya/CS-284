package notes.shapes;

public abstract class Shape implements Colorable {

    protected String color;

    // even though an abstract class cannot be instatiated directly
    // subclasses of the class Shape call the Shape() constructor
    Shape(String color) {
        this.color = color;
    }

    // Methods
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "The color is " + this.color + ".";
    }

    // abstract methods force all subclasses of the abstract class to support
    public abstract double area();
}