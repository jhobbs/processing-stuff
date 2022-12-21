import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class IntegralCurve {
    ArrayList<PVector> points = new ArrayList<>();
    int r, g, b;
    SlopeFunction slopeFunction;

    float scaleSize;

    final int MAX_TRACE_LEN = 4000;

    static float randomInScale(float scaleSize) {
        return -scaleSize + (float)random() * scaleSize * 2;
    }

    public IntegralCurve(SlopeFunction slopeFunction, float scaleSize) {
        this(slopeFunction, scaleSize, randomInScale(scaleSize), randomInScale(scaleSize));
    }

    public IntegralCurve(SlopeFunction slopeFunction, float scaleSize, float x, float y) {
        this.slopeFunction = slopeFunction;
        this.scaleSize = scaleSize;
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
        tracePath(x, y, 0.01f);
        tracePath(x, y, -0.01f);
    }
}
