import processing.core.PVector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import static java.lang.Math.abs;
import static java.lang.Math.random;

public class Particle {

    double scaleSize;
    Point2D position;

    ArrayList<Point2D> positionHistory = new ArrayList<>();

    int r, g, b;

    static double randomInScale(double scaleSize) {
        return -scaleSize + (float)random() * scaleSize * 2;
    }

    public Particle(double scaleSize) {
        this(scaleSize, randomInScale(scaleSize), randomInScale(scaleSize));
    }

    public void move(Point2D delta) {
        positionHistory.add(new Point2D.Double(position.getX(), position.getY()));
        position.setLocation(position.getX() + delta.getX(), position.getY() + delta.getY());
    }

    public boolean offGrid() {
        return abs(position.getX()) > scaleSize || abs(position.getY()) > scaleSize;
    }

    public Particle(double scaleSize, double x, double y) {
        this.scaleSize = scaleSize;
        this.position = new Point2D.Double(x, y);
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
    }
}
