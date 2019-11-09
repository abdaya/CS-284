package maze;

public class PairInt {
    private int x;
    private int y;

    public PairInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return int return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return int return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object pair) {
        return (pair instanceof PairInt) && this.x == ((PairInt) pair).x && this.y == ((PairInt) pair).y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public PairInt copy() {
        return new PairInt(x, y);
    }
}