package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import static java.lang.Math.pow;

public class CollapsingSourceODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return 1;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return 1+x*pow(y, 2);
    }
}
