package diffeq;

public interface FirstOrderODE {
    public double dx_over_dt(double x, double y);

    public double dy_over_dt(double x, double y);
}