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

    private final int incrementDenominator;

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();

    public ODEModeler(FirstOrderODE ode, int incrementDenominator) {
        this.ode = ode;
        this.incrementDenominator = incrementDenominator;
        updateScaledSettings();
    }

    void makeIntegralCurves() {
        for (int i = 0; i < 100; i++) {
            integralCurves.add(new IntegralCurve(ode, particleSize, incrementDenominator));
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
        this.particleSize = scaleSize * 0.01;
        makeIntegralCurves();
    }
}