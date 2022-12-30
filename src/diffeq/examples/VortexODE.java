package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import static java.lang.Math.pow;

public class VortexODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return 1 - pow(x, 2) - pow(y, 2);
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return pow(x, 2);
    }
}
