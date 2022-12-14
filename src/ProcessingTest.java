import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;

public class ProcessingTest extends PApplet {

    private final int boxSize = 1000;
    private final int width = boxSize;
    private final int height = boxSize;

    private final int maxX = width / 2;
    private final int maxY = height / 2;

    private final int scaledSize = 5;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        line(0, maxY, 0, -maxY);
        line(-maxX, 0, maxX, 0);
        stroke(126);
    }

    private float sX(float x) {
        float scaledX = maxX / (float)scaledSize;
        return x * scaledX;
    }

    private float sY(float y) {
        float scaledY = maxY / (float)scaledSize;
        return y * scaledY;
    }

    private float nX(float x) {
        float scaledX = (float)scaledSize / maxX;
        return x * scaledX;
    }

    private float nY(float y) {
        float scaledY = (float)scaledSize / maxX;
        return y * scaledY;
    }

    private void drawTicks() {
        for (int x = -scaledSize; x <= scaledSize; x += 1) {
            line(sX(x), 10, sX(x), -10);
        }
        for (int y = -scaledSize; y <= scaledSize; y += 1) {
            line(-10, sY(y), 10, sY(y));
        }
    }

    private float getRotation(float x, float y) {
        /*if (x - y == 0)
            return PI/2;
        return atan((x + y) / (x - y));*/
        /*if (x == 0)
            return 0;
        return atan(2 * y / x);*/
        return atan(pow(x, 2) - y);
//        return atan(sin(x)*scaledSize);
        //return 2*y - pow(y, 2);
        //return (x / 4) * (-y);
        //return atan(x * y);
        //return atan(1);
    }

    private void drawLineElement(float x, float y) {
        pushMatrix();
        translate(sX(x), sY(y));
        rotate(getRotation(x, y));
        line(sX(- 0.1f), 0, sX(0.1f), 0);
        triangle(sX(0.1f), 0, sX(0.075f), sY(0.02f), sX(0.075f), sY(-0.02f));
        popMatrix();
    }

    void drawLineElements() {
        for (float x = -scaledSize; x <= scaledSize; x += 0.4) {
            for (float y = -scaledSize; y <= scaledSize; y += 0.2) {
                drawLineElement(x, y);
            }
        }
    }

    Particle randomNewParticle() {
        return new Particle(scaledSize);
    }

    ArrayList<Particle> particles = new ArrayList<>();

    void maybeNewParticle() {
        if (particles.size() < 10) {
            particles.add(randomNewParticle());
        }
    }

    void drawParticles() {
        noStroke();
        for (Particle particle: particles) {
            fill(particle.r, particle.g, particle.b);
            circle(sX(particle.position.x), sY(particle.position.y), 20);
        }
        stroke(156.0f);
    }

    void moveParticles() {
        for (Particle particle: particles) {
            float rotation = getRotation(particle.position.x, particle.position.y);
            PVector delta = new PVector(cos(rotation) * 0.01f, sin(rotation) * 0.01f);
            particle.move(delta);
        }
    }

    public void mousePressed() {
        float x = nX((mouseX - width/2.0f));
        float y = nY(-(mouseY -height/2.0f));
        println("adding at (" + x + ", " + y + ")");
        particles.add(new Particle(scaledSize, x, y));
    }

    void cullParticles() {
        particles.removeIf(Particle::offGrid);
    }

    public void setup() {
        background(0);
        strokeWeight(2);
    }

    public void draw(){
        background(0);
        scale(1, -1);
        translate(width/2, -(height/2));
        drawGrid();
        drawTicks();
        drawLineElements();
        maybeNewParticle();
        drawParticles();
        moveParticles();
        cullParticles();
    }


    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
