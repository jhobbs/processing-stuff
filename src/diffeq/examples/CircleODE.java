package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class CircleODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return y;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return -x;
    }
}
