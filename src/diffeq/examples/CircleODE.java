package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class CircleODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return y;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return -x;
    }
}
