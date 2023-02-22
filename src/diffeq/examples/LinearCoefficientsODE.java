package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import java.util.Random;

public class LinearCoefficientsODE extends BaseFirstOrderODE {

    private final int a1, a2, b1, b2, c1, c2;

    public LinearCoefficientsODE(int coeffs[], double scaleSize) {
        a1 = coeffs[0];
        b1 = coeffs[1];
        c1 = coeffs[2];
        a2 = coeffs[3];
        b2 = coeffs[4];
        c2 = coeffs[5];
        setScaleSize(scaleSize);

        System.out.printf("(%dx + %dy + %d)dx - (%dx + %dy + %d)dy = 0; -%.02f <= x,y <= %.02f%n",
                a1, b1, c1, a2, b2, c2, scaleSize, scaleSize);
    }

    public LinearCoefficientsODE getOrthogonalODE() {
        int[] orthogonalCoeffs = {
                -a2, -b2, -c2, a1, b1, c1
        };

        return new LinearCoefficientsODE(orthogonalCoeffs, getScaleSize());
    }

    @Override
    public double dx_over_dt(double x, double y) {
        return (a2 * x + b2 * y + c2);
    }

    @Override
    public double dy_over_dt(double x, double y) {
        return (a1 * x + b1 * y + c1);
    }
}
