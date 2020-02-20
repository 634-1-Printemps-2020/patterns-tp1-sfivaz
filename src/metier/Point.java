package metier;

import java.util.Objects;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object obj) {
        return this.x == ((Point) obj).x && this.y == ((Point) obj).y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return "<" + x + ";" + y + ">";
    }
}