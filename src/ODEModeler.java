import diffeq.SlopeFunction;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ODEModeler {
    SlopeFunction slopeFunction;
    double scaleSize;
    double particleSize;

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();

    public ODEModeler(SlopeFunction slopeFunction, double scaleSize, double particleSize) {
        this.slopeFunction = slopeFunction;
        this.scaleSize = scaleSize;
        this.particleSize = particleSize;

        makeIntegralCurves();
    }

    void makeIntegralCurves() {
        for (int i = 0; i < 100; i++) {
            integralCurves.add(new IntegralCurve(slopeFunction, scaleSize, particleSize));
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
        for (Particle particle: particles) {
            double rotation = slopeFunction.getSlope(particle.position.getX(), particle.position.getY());
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
}
