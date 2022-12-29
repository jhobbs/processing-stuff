package diffeq;

public class CircleODE implements FirstOrderODE {
    @Override
    public float dx_over_dt(float x, float y) {
        return y;
    }

    @Override
    public float dy_over_dt(float x, float y) {
        return -x;
    }
}
