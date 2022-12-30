package diffeq;

import static java.lang.Math.atan2;

public abstract class BaseFirstOrderODE implements FirstOrderODE {
    @Override
    public double getSlope(double x, double y) {
        return atan2(dy_over_dt(x, y), dx_over_dt(x, y));
    }

    @Override
    public double getScaleSize() {
        return 5;
    }
}
