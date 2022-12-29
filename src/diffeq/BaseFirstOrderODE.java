package diffeq;

import static processing.core.PApplet.atan2;

public abstract class BaseFirstOrderODE implements SlopeFunction, FirstOrderODE {
    @Override
    public float getSlope(float x, float y) {
        return atan2(dy_over_dt(x, y), dx_over_dt(x, y));
    }
}
