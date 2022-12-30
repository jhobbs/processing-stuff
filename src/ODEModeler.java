import diffeq.FirstOrderODE;
import diffeq.SlopeFunction;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class ODEModeler {
    SlopeFunction slopeFunction;
    float scaleSize;
    float particleSize;

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();

    public ODEModeler(SlopeFunction slopeFunction, float scaleSize, float particleSize) {
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
            float rotation = slopeFunction.getSlope(particle.position.x, particle.position.y);
            PVector delta = new PVector(cos(rotation) * (particleSize/10), sin(rotation) * (particleSize/10));
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
