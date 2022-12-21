package partition;

import processing.core.PVector;

import java.util.Random;

import static java.lang.Math.abs;

public class CircularPartition implements PartitionFunction {
    private final PVector center;
    private final float radius;

    public CircularPartition(float scaleSize) {
        Random random = new Random();
        final float x = (float)random.nextGaussian(0, scaleSize/2);
        final float y = (float)random.nextGaussian(0, scaleSize/2);
        center = new PVector(x, y);
        radius = abs((float)random.nextGaussian(scaleSize, scaleSize/2));
        System.out.println("circle of " + radius + " at (" + x + ", " + y +")");
    }
    @Override
    public int getPartition(float x, float y) {
        PVector point = new PVector(x, y);
        if (center.dist(point) < radius) {
            return 0;
        }

        return 1;
    }
}