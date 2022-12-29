package diffeq;

public interface FirstOrderODE {
    public float dx_over_dt(float x, float y);

    public float dy_over_dt(float x, float y);
}