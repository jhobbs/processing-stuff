package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import static java.lang.Math.sin;

public class HorizontalSinWaveODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return 1;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return sin(x);
    }
}
