import processing.core.PVector;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Util {
    static public PVector p2c(float r, float theta) {
        return new PVector(r * cos(theta), -(r * sin(theta)));
    }
}
