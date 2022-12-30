package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class TopODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return 1;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return x/4 * -y;
    }
}
