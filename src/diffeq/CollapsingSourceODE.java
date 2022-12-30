package diffeq;

import static processing.core.PApplet.pow;

public class CollapsingSourceODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return 1;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return 1+x*pow(y, 2);
    }
}
