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

    private final float pixelSize = (1.0f/boxSize) * scaledSize*2;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        line(0, scaledSize, 0, -scaledSize);
        line(-scaledSize, 0, scaledSize, 0);
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
            line(x, 0.2f, x, -0.2f);
        }
        for (int y = -scaledSize; y <= scaledSize; y += 1) {
            line(-0.2f, y, 0.2f, y);
        }
    }

    static List<SlopeFunction> slopeFunctions = Arrays.asList(
            (x, y) -> {  return atan(x * y); },
            (x, y) -> {  return atan(2 * y - pow(y, 2)); },
            (x, y) -> {  return atan(x/4 * (-y) ); },
            (x, y) -> {  return atan(x * y); }
    );

    SlopeFunction slopeFunction;

    private float getRotation(float x, float y) {
        /*if (x - y == 0)
            return PI/2;
        return atan((x + y) / (x - y));*/
        /*if (x == 0)
            return 0;
        return atan(2 * y / x);*/
        return slopeFunction.getSlope(x, y);
//        return atan(sin(x)*scaledSize);
        //return 2*y - pow(y, 2);
        //return (x / 4) * (-y);
        //return atan(x * y);
        //return atan(1);
    }

    private void drawLineElement(float x, float y) {
        pushMatrix();
        translate(x, y);
        rotate(getRotation(x, y));
        float tickWidth = pixelSize * 10;
        line(-tickWidth, 0, tickWidth, 0);
        triangle(tickWidth, 0, tickWidth * 0.75f, tickWidth * 0.2f, tickWidth * 0.75f, -(tickWidth *.2f));
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
        if (particles.size() < -10) {
            particles.add(randomNewParticle());
        }
    }

    void drawParticles() {
        noStroke();
        for (Particle particle: particles) {
            fill(particle.r, particle.g, particle.b);
            for (PVector historicalPosition: particle.positionHistory) {
                circle(historicalPosition.x, historicalPosition.y, pixelSize * 5);
            }
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

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    void makeIntegralCurves() {
        for (int i = 0; i < 10; i++) {
            integralCurves.add(new IntegralCurve(slopeFunction, scaledSize));
        }
    }

    private void drawIntegralCurves() {
        for (IntegralCurve curve : integralCurves) {
            for (PVector point : curve.points) {
                stroke(curve.r, curve.b, curve.g);
                fill(curve.r, curve.b, curve.g);
                //point(point.x, point.y);
                circle(point.x, point.y, pixelSize * 10);
                noStroke();
            }
        }
    }


    public void setup() {
        background(0);
        strokeWeight(pixelSize);
        Random random = new Random();
        slopeFunction = slopeFunctions.get(random.nextInt(slopeFunctions.size()));
        makeIntegralCurves();
    }

    public void draw(){
        stroke(156.0f);
        background(0);
        scale((boxSize / ((float)scaledSize * 2)), -(boxSize/((float)scaledSize * 2)));
        translate(scaledSize, -(scaledSize));
        drawGrid();
        drawTicks();
        drawLineElements();
        maybeNewParticle();
        drawParticles();
        moveParticles();
        cullParticles();
        drawIntegralCurves();
    }

    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
