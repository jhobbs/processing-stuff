package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class CrissCrossAtOriginODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return x;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return 2 * y;
    }
}
