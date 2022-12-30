package partition;

import java.util.Random;

public class VerticalPartition implements PartitionFunction {
    private final double xBoundary;

    public VerticalPartition(double scaleSize) {
        Random random = new Random();
        xBoundary = random.nextGaussian(0, scaleSize/2);
        System.out.println("boundary at x = " + xBoundary);
    }
    @Override
    public int getPartition(double x, double y) {
        if (x < xBoundary) {
            return 0;
        }
        return 1;
    }
}