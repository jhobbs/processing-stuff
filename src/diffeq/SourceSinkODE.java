package diffeq;

import static processing.core.PApplet.pow;

public class SourceSinkODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return 1;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return 2 * y - pow(y, 2);
    }
}
