import lombok.Data;
import processing.core.PApplet;
import processing.core.PVector;
import static processing.core.PApplet.*;


@Data
public class Pendulum {
    float velocity;
    PApplet p;

    PVector startCoords;

    PVector endCoords;

    private float length;

    private double effectiveTheta() {
        float theta = getTheta();
        if (theta > PI/2 && theta < (3*PI)/2) {
            return theta - (3 * (PI/2));
        }

        if (theta + PI/2 > 2 * PI) {
            theta -= 2 * PI;
        }
        return theta + PI/2;
    }

    private float getTheta() {
        float theta = atan2(endCoords.y - startCoords.y, endCoords.x - startCoords.x);
        return theta;
    }
    private PVector p2c(float theta) {
        return new PVector(length * cos(theta) - startCoords.x, length * sin(theta) - startCoords.y);
    }

    public Pendulum(PVector startCoords, PVector endCoords, PApplet p) {
        this.startCoords = startCoords;
        this.endCoords = endCoords;
        this.length = startCoords.dist(endCoords);
        this.p = p;
        this.velocity = 0;
        println("Creating pendulum from " + startCoords + " to " + endCoords + "; length: " + length + " theta: " + getTheta());
    }

    public float acceleration() {
        //FixME: should be sin(theta)
        double gravitational = - ((0.25/length) * sin((float)effectiveTheta()));
        double drag;
        if (velocity != 0) {
            int sign;
            if (velocity >= 0 ) {
                sign = -1;
            } else {
                sign = 1;
            }
            drag = sign * (abs(pow(velocity, 2)) * 0.002) ;
            drag += sign * (abs(pow(velocity, 1)) * 0.002) ;
        } else {
            drag = 0;
        }
        double acceleration = gravitational + drag;
        //println("accel (" + r + ", " + effectiveTheta() + "): " + acceleration);
        return (float)(acceleration);
    }

    public void move() {
        float currentTheta = getTheta();
        currentTheta += velocity;
        endCoords = p2c(currentTheta);
        velocity += acceleration();
    }

    public void draw() {
        p.circle(endCoords.x, endCoords.y, 20);
        p.line(startCoords.x, startCoords.y, endCoords.x, endCoords.y);
        move();
    }
}