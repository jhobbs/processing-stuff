package diffeq;

import static processing.core.PApplet.pow;

public class SpiralODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return x - y;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return x + y;
    }
}
