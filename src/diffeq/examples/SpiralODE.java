package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class SpiralODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return x - y;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return x + y;
    }
}
