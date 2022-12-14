import processing.core.PApplet;
import processing.core.PVector;

import java.util.concurrent.ThreadLocalRandom;

public class Particle {
    PVector position;

    int r, g, b;

    public Particle(float x, float y) {
        this.position = new PVector(x, y);
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
    }
}
