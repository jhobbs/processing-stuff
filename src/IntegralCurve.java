import diffeq.SlopeFunction;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class IntegralCurve {
    final ArrayList<Point2D> points = new ArrayList<>();
    final int r, g, b;
    final SlopeFunction slopeFunction;

    final double scaleSize;

    final int MAX_TRACE_LEN = 4000;

    final double particleSize;

    public IntegralCurve(SlopeFunction slopeFunction, double scaleSize, double particleSize) {
        this(slopeFunction, scaleSize, particleSize, randomInScale(scaleSize), randomInScale(scaleSize));
    }

    IntegralCurve(SlopeFunction slopeFunction, double scaleSize, double particleSize, double x, double y) {
        this.slopeFunction = slopeFunction;
        this.scaleSize = scaleSize;
        this.particleSize = particleSize;
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        addPoints(x, y);
    }

    static double randomInScale(double scaleSize) {
        return -scaleSize + random() * scaleSize * 2;
    }

    public void tracePath(double x, double y, double increment) {
        Point2D currentPoint = new Point2D.Double(x, y);
        for (int traceCount = 0;
             traceCount < MAX_TRACE_LEN && (currentPoint.getX()) < scaleSize && abs(currentPoint.getY()) < scaleSize;
             traceCount++) {
            points.add(currentPoint);
            double rotation = slopeFunction.getSlope(currentPoint.getX(), currentPoint.getY());
            Point2D delta = new Point2D.Double(cos(rotation) * increment, sin(rotation) * increment);
            currentPoint = new Point2D.Double(currentPoint.getX() + delta.getX(), currentPoint.getY() + delta.getY());
        }
    }

    public void addPoints(double x, double y) {
        tracePath(x, y, particleSize / 3);
        tracePath(x, y, -particleSize / 3);
    }
}
