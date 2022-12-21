import partition.CircularPartition;
import partition.PartitionFunction;
import partition.VerticalPartition;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;


public class ProcessingTest extends PApplet {

    private final int boxSize = 1000;
    private final int width = boxSize;
    private final int height = boxSize;

    private final int maxX = width / 2;
    private final int maxY = height / 2;

    static private final int scaleSize = 5;

    private final float pixelSize = (1.0f/boxSize) * scaleSize*2;

    private final float particleSize = pixelSize * 15;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        line(0, scaleSize, 0, -scaleSize);
        line(-scaleSize, 0, scaleSize, 0);
        stroke(126);
    }

    private float nX(float x) {
        float scaledX = (float)scaleSize / maxX;
        return x * scaledX;
    }

    private float nY(float y) {
        float scaledY = (float)scaleSize / maxX;
        return y * scaledY;
    }

    private void drawTicks() {
        for (int x = -scaleSize; x <= scaleSize; x += 1) {
            line(x, 0.2f, x, -0.2f);
        }
        for (int y = -scaleSize; y <= scaleSize; y += 1) {
            line(-0.2f, y, 0.2f, y);
        }
    }

    static List<SlopeFunction> slopeFunctions = Arrays.asList(
            (x, y) -> {  return atan(x * y); },
            (x, y) -> {  return atan(2 * y - pow(y, 2)); },
            (x, y) -> {  return atan(x/4 * (-y) ); },
            (x, y) -> {  return atan(x * y); },
            (x, y) -> {  return atan(x); },
            (x, y) -> {  return atan(y); },
            (x, y) -> {
                if (x - y == 0)
                    return PI/2;
                return atan((x + y) / (x - y));
            },
            (x, y) -> {
                if (x == 0)
                    return 0;
                return atan(2 * y / x);
            },
            (x, y) -> {
                return atan(sin(x)*scaleSize);
            }
    );

    SlopeFunction slopeFunction;

    private float getRotation(float x, float y) {
        return slopeFunction.getSlope(x, y);
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
        for (float x = -scaleSize; x <= scaleSize; x += 0.4) {
            for (float y = -scaleSize; y <= scaleSize; y += 0.2) {
                drawLineElement(x, y);
            }
        }
    }

    Particle randomNewParticle() {
        return new Particle(scaleSize);
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
                circle(historicalPosition.x, historicalPosition.y, particleSize);
            }
        }
        stroke(156.0f);
    }

    void moveParticles() {
        for (Particle particle: particles) {
            float rotation = getRotation(particle.position.x, particle.position.y);
            PVector delta = new PVector(cos(rotation) * (particleSize/2), sin(rotation) * (particleSize/2));
            particle.move(delta);
        }
    }

    public void mousePressed() {
        float x = nX((mouseX - width/2.0f));
        float y = nY(-(mouseY -height/2.0f));
        println("adding at (" + x + ", " + y + ")");
        particles.add(new Particle(scaleSize, x, y));
    }

    void cullParticles() {
        particles.removeIf(Particle::offGrid);
    }

    ArrayList<IntegralCurve> integralCurves = new ArrayList<>();
    void makeIntegralCurves() {
        for (int i = 0; i < 100; i++) {
            integralCurves.add(new IntegralCurve(slopeFunction, scaleSize));
        }
    }

    private void drawIntegralCurves() {
        for (IntegralCurve curve : integralCurves) {
            for (PVector point : curve.points) {
                stroke(curve.r, curve.b, curve.g);
                fill(curve.r, curve.b, curve.g);
                //point(point.x, point.y);
                circle(point.x, point.y, particleSize);
                noStroke();
            }
        }
    }

    List<PartitionFunction> partitionFunctions = Arrays.asList(
            new VerticalPartition(scaleSize),
            new CircularPartition(scaleSize)
    );

    SlopeFunction compositeSlopeFunction() {
        Random random = new Random();
        List<SlopeFunction> slopeFunctions1 = Arrays.asList(
                slopeFunctions.get(random.nextInt(slopeFunctions.size())),
                slopeFunctions.get(random.nextInt(slopeFunctions.size()))
        );

        PartitionFunction partitionFunction = partitionFunctions.get(random.nextInt(partitionFunctions.size()));

        return (x, y) -> {
            return slopeFunctions1.get(partitionFunction.getPartition(x, y)).getSlope(x, y);
        };
    }

    public void setup() {
        background(0);
        strokeWeight(pixelSize);
        slopeFunction = compositeSlopeFunction();
        makeIntegralCurves();
    }

    public void drawGuides() {
        drawGrid();
        drawTicks();
        drawLineElements();
    }

    public void draw(){
        stroke(156.0f);
        background(0);
        scale((boxSize / ((float)scaleSize * 2)), -(boxSize/((float)scaleSize * 2)));
        translate(scaleSize, -(scaleSize));
        //drawGuides();
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
