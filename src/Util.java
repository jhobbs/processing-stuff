import processing.core.PVector;

import static processing.core.PApplet.*;

public class Util {
    static public PVector p2c(float r, float theta) {
        return new PVector(r * cos(theta), -(r * sin(theta)));
    }
}
