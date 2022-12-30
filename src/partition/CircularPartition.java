package partition;

import java.awt.geom.Point2D;
import java.util.Random;

import static java.lang.Math.abs;

public class CircularPartition implements PartitionFunction {
    private final Point2D center;
    private final double radius;

    public CircularPartition(double scaleSize) {
        Random random = new Random();
        final double x = random.nextGaussian(0, scaleSize / 2);
        final double y = random.nextGaussian(0, scaleSize / 2);
        center = new Point2D.Double(x, y);
        radius = abs(random.nextGaussian(scaleSize, scaleSize / 2));
        System.out.println("circle of " + radius + " at (" + x + ", " + y + ")");
    }

    @Override
    public int getPartition(double x, double y) {
        if (center.distance(x, y) < radius) {
            return 0;
        }

        return 1;
    }
}