package diffeq;

public interface FirstOrderODE {
    double dx_over_dt(double x, double y);

    double dy_over_dt(double x, double y);
}