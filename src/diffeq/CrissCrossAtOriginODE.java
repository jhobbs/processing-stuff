package diffeq;

public class CrissCrossAtOriginODE extends BaseFirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return x;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return 2 * y;
    }
}
