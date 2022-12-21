package partition;

import lombok.AllArgsConstructor;

import java.util.Random;

public class VerticalPartition implements PartitionFunction {
    private final float xBoundary;

    public VerticalPartition(float scaleSize) {
        Random random = new Random();
        xBoundary = (float)random.nextGaussian(0, scaleSize/2);
        System.out.println("boundary at x = " + xBoundary);
    }
    @Override
    public int getPartition(float x, float y) {
        if (x < xBoundary) {
            return 0;
        }
        return 1;
    }
}