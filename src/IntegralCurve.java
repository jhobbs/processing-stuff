import diffeq.FirstOrderODE;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class IntegralCurve {
    final ArrayList<Point2D> points = new ArrayList<>();
    final int r, g, b;
    final FirstOrderODE ode;

    final double scaleSize;

    final int maxTraceLen;

    final double particleSize;
    private final double increment;

    public IntegralCurve(FirstOrderODE ode, double particleSize, double increment, int maxTraceLen) {
        this(ode, particleSize, randomInScale(ode.getScaleSize()), randomInScale(ode.getScaleSize()), increment, maxTraceLen);
    }

    IntegralCurve(FirstOrderODE ode, double particleSize, double x, double y, double increment, int maxTraceLen) {
        this.ode = ode;
        this.scaleSize = ode.getScaleSize();
        this.particleSize = particleSize;
        this.increment = increment;
        this.maxTraceLen = maxTraceLen;
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        addPoints(x, y);
    }

    static double randomInScale(double scaleSize) {
        return -scaleSize + random() * scaleSize * 2;
    }

    //TODO: Investigate threading this to trace curves concurrently
    public void tracePath(double x, double y, double increment) {
        double recentXVelocity = 0;
        double recentYVelocity = 0;

        Point2D currentPoint = new Point2D.Double(x, y);
        for (int traceCount = 0;
             traceCount < maxTraceLen && (currentPoint.getX()) < scaleSize && abs(currentPoint.getY()) < scaleSize;
             traceCount++) {
            /*
            FIXME: we can be more accurate by iterating on smaller increments but not drawing a point for
            each one. Let's say we need a circle drawn every 10 pixels to get the appearance of a smooth
            line. We can iterate 10 or 100 or 1000 times instead of just one time for each circle.
            */
            points.add(currentPoint);
            double rotation = ode.getSlope(currentPoint.getX(), currentPoint.getY());
            double xDelta = cos(rotation) * increment;
            double yDelta = sin(rotation) * increment;
            Point2D newPoint = new Point2D.Double(
                    currentPoint.getX() + xDelta,
                    currentPoint.getY() + yDelta);
            currentPoint = newPoint;
            recentXVelocity = recentXVelocity * 0.8 + xDelta * 0.2;
            recentYVelocity = recentYVelocity * 0.8 + yDelta * 0.2;
            if (traceCount > 5 && (abs(recentXVelocity) < abs(increment/2) && abs(recentYVelocity) < abs(increment/2))) {
                break;
            }
        }
    }

    public void addPoints(double x, double y) {
        tracePath(x, y, increment);
        tracePath(x, y, -increment);
    }
}
