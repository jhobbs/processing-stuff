import diffeq.FirstOrderODE;
import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ODEModeler {
    FirstOrderODE ode;

    @Getter
    private double scaleSize;
    double particleSize;

    private final double incrementDenominator;

    private final int integralCurveCount;

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();

    public ODEModeler(FirstOrderODE ode, double incrementDenominator, double particleSize, int integralCurveCount) {
        this.ode = ode;
        this.incrementDenominator = incrementDenominator;
        this.particleSize = particleSize;
        this.integralCurveCount = integralCurveCount;
        updateScaledSettings();
    }

    void makeIntegralCurves() {

        int maxTraceLen = (int)(incrementDenominator / particleSize) * 100;
        double increment = particleSize / incrementDenominator;

        for (int i = 0; i < integralCurveCount; i++) {
            integralCurves.add(new IntegralCurve(ode, particleSize, increment, maxTraceLen));
        }
    }

    void maybeNewParticle() {
        if (particles.size() < -10) {
            particles.add(randomNewParticle());
        }
    }

    Particle randomNewParticle() {
        return new Particle(scaleSize);
    }

    void moveParticles() {
        for (Particle particle : particles) {
            double rotation = ode.getSlope(particle.position.getX(), particle.position.getY());
            Point2D delta = new Point2D.Double(cos(rotation) * (particleSize / 10), sin(rotation) * (particleSize / 10));
            particle.move(delta);
        }
    }

    void cullParticles() {
        particles.removeIf(Particle::offGrid);
    }

    void update() {
        cullParticles();
        maybeNewParticle();
        moveParticles();
    }

    private void updateScaledSettings() {
        this.scaleSize = ode.getScaleSize();
        makeIntegralCurves();
    }
}
