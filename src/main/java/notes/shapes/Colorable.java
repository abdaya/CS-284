package notes.shapes;

public interface Colorable {

    // Cross cutting the class heirarchy
    public String getColor();

    public void setColor(String color);
}