import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class IntegralCurve {
    ArrayList<PVector> points = new ArrayList<>();
    int r, g, b;
    SlopeFunction slopeFunction;

    float scaleSize;

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

    public void addPoints(float x, float y) {
        PVector startPoint = new PVector(x, y);
        PVector currentPoint = startPoint.copy();
        while (abs(currentPoint.x) < scaleSize && abs(currentPoint.y) < scaleSize) {
            points.add(currentPoint.copy());
            float rotation = slopeFunction.getSlope(currentPoint.x, currentPoint.y);
            PVector delta = new PVector((float) cos(rotation) * 0.01f, (float) sin(rotation) * 0.01f);
            currentPoint.add(delta);
        }
        currentPoint = startPoint.copy();
        while (abs(currentPoint.x) < scaleSize && abs(currentPoint.y) < scaleSize) {
            points.add(currentPoint.copy());
            float rotation = slopeFunction.getSlope(currentPoint.x, currentPoint.y);
            PVector delta = new PVector((float) cos(rotation) * -0.01f, (float) sin(rotation) * -0.01f);
            currentPoint.add(delta);
        }
    }
}
