package diffeq.examples;

import diffeq.BaseFirstOrderODE;

import java.util.Random;

public class LinearCoefficientsODE extends BaseFirstOrderODE {

    private final int a1, a2, b1, b2, c1, c2;

    public LinearCoefficientsODE() {
        Random random = new Random();
        a1 = random.nextInt(10) - 5;
        b1 = random.nextInt(10) - 5;
        c1 = random.nextInt(10) - 5;

        a2 = random.nextInt(10) - 5;
        b2 = random.nextInt(10) - 5;
        c2 = random.nextInt(10) - 5;

        System.out.println(
                String.format("(%dx + %dy + %d)dx - (%dx + %dy + %d)dy = 0",
                        a1, b1, c1, a2, b2, c2)
        );
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
