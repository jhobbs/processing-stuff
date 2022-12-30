package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import static processing.core.PApplet.pow;

public class LefToRightConcentratorODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return 1 + pow(x, 2);
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return x - y;
    }
}
