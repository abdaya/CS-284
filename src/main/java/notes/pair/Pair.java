package notes.pair;

// Generic or paramerteized class
public class Pair<E, F> {
    private E x;
    private F y;

    public Pair(E x, F y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return E return the x
     */
    public E getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(E x) {
        this.x = x;
    }

    /**
     * @return F return the y
     */
    public F getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(F y) {
        this.y = y;
    }

}