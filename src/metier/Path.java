package metier;

import java.util.Objects;

public class Path {

    private Point from;
    private Point to;

    public Path(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(from, path.from) &&
                Objects.equals(to, path.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
