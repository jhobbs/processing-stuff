import lombok.Data;
import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.*;

@Data
public class Pendulum {
    float theta;
    float r;
    float velocity;
    PApplet p;

    public Pendulum(PApplet p, float theta, float r, float velocity) {
        this.r = r;
        this.velocity = velocity;
        this.theta = theta;
        this.p = p;
    }

    private double effectiveTheta() {
        if (theta > PI / 2 && theta < (3 * PI) / 2) {
            return theta - (3 * (PI / 2));
        }

        if (theta + PI / 2 > 2 * PI) {
            theta -= 2 * PI;
        }
        return theta + PI / 2;
    }

    public float acceleration() {
        double gravitational = -((0.2 / r) * effectiveTheta());
        double drag;
        if (velocity != 0) {
            int sign;
            if (velocity >= 0) {
                sign = -1;
            } else {
                sign = 1;
            }
            drag = sign * (abs(pow(velocity, 2)) * 0.002);
            drag += sign * (abs(pow(velocity, 1)) * 0.002);
        } else {
            drag = 0;
        }
        double acceleration = gravitational + drag;
        //println("accel (" + r + ", " + effectiveTheta() + "): " + acceleration);
        return (float) (acceleration);
    }

    public void move() {
        theta += velocity;
        velocity += acceleration();
    }

    public void draw() {
        PVector pendulumLocation = Util.p2c(r, theta);
        p.circle(pendulumLocation.x, pendulumLocation.y, 20);
        p.line(0, 0, pendulumLocation.x, pendulumLocation.y);
        move();
    }
}