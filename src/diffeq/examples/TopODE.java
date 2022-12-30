package diffeq.examples;

import diffeq.BaseFirstOrderODE;

public class TopODE extends BaseFirstOrderODE {
    @Override
    public double dx_over_dt(double x, double y) {
        return 1;
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return x / 4 * -y;
    }
}
