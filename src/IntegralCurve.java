import diffeq.SlopeFunction;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class IntegralCurve {
    final ArrayList<PVector> points = new ArrayList<>();
    final int r, g, b;
    final SlopeFunction slopeFunction;

    final float scaleSize;

    final int MAX_TRACE_LEN = 4000;

    final float particleSize;

    static float randomInScale(float scaleSize) {
        return -scaleSize + (float)random() * scaleSize * 2;
    }

    public IntegralCurve(SlopeFunction slopeFunction, float scaleSize, float particleSize) {
        this(slopeFunction, scaleSize, particleSize, randomInScale(scaleSize), randomInScale(scaleSize));
    }

    IntegralCurve(SlopeFunction slopeFunction, float scaleSize, float particleSize, float x, float y) {
        this.slopeFunction = slopeFunction;
        this.scaleSize = scaleSize;
        this.particleSize = particleSize;
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        addPoints(x, y);
    }

    public void tracePath(float x, float y, float increment) {
        PVector currentPoint = new PVector(x, y);
        for (int traceCount = 0;
                traceCount < MAX_TRACE_LEN && (currentPoint.x) < scaleSize && abs(currentPoint.y) < scaleSize;
                traceCount++) {
            points.add(currentPoint.copy());
            float rotation = slopeFunction.getSlope(currentPoint.x, currentPoint.y);
            PVector delta = new PVector((float) cos(rotation) * increment, (float) sin(rotation) * increment);
            currentPoint.add(delta);
        }
    }

    public void addPoints(float x, float y) {
        tracePath(x, y, particleSize/3);
        tracePath(x, y, -particleSize/3);
    }
}
