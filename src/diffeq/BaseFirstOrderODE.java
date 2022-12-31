package diffeq;

import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.atan2;

public abstract class BaseFirstOrderODE implements FirstOrderODE {

    @Getter
    @Setter
    private double scaleSize = 5;

    @Override
    public double getSlope(double x, double y) {
        return atan2(dy_over_dt(x, y), dx_over_dt(x, y));
    }
}
