import processing.core.PApplet;
import processing.core.PVector;

import java.util.concurrent.ThreadLocalRandom;


import static java.lang.Math.random;
import static processing.core.PApplet.*;

public class Particle {

    float scaleSize;
    PVector position;

    int r, g, b;

    static float randomInScale(float scaleSize) {
        return -scaleSize + (float)random() * scaleSize * 2;
    }

    public Particle(float scaleSize) {
        this(scaleSize, randomInScale(scaleSize), randomInScale(scaleSize));
    }

    public void move(PVector delta) {
        position.add(delta);
    }

    public boolean offGrid() {
        return abs(position.x) > scaleSize || abs(position.y) > scaleSize;
    }

    public Particle(float scaleSize, float x, float y) {
        this.scaleSize = scaleSize;
        this.position = new PVector(x, y);
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
    }
}
