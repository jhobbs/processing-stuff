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

    final int MAX_TRACE_LEN = 4000;

    final double particleSize;

    public IntegralCurve(FirstOrderODE ode, double particleSize) {
        this(ode, particleSize, randomInScale(ode.getScaleSize()), randomInScale(ode.getScaleSize()));
    }

    IntegralCurve(FirstOrderODE ode, double particleSize, double x, double y) {
        this.ode = ode;
        this.scaleSize = ode.getScaleSize();
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
        double recentXVelocity = 0;
        double recentYVelocity = 0;

        Point2D currentPoint = new Point2D.Double(x, y);
        for (int traceCount = 0;
             traceCount < MAX_TRACE_LEN && (currentPoint.getX()) < scaleSize && abs(currentPoint.getY()) < scaleSize;
             traceCount++) {
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
        tracePath(x, y, particleSize / 3);
        tracePath(x, y, -particleSize / 3);
    }
}
