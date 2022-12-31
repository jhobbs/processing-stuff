import diffeq.BaseFirstOrderODE;
import diffeq.FirstOrderODE;
import diffeq.examples.*;
import partition.CircularPartition;
import partition.PartitionFunction;
import partition.VerticalPartition;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ProcessingTest extends PApplet {

    private float scaleSize;
    static List<FirstOrderODE> odes = Arrays.asList(
            new MirroredParabolasODE(),
            new SourceSinkODE(),
            new TopODE(),
            new VerticalParabolaODE(),
            new HorizontalFrondODE(),
            new CircleODE(),
            new HorizontalWaveishODE(),
            new CollapsingSourceODE(),
            new LefToRightConcentratorODE(),
            new SpiralODE(),
            new CrissCrossAtOriginODE(),
            new HorizontalSinWaveODE()
    );
    private final int boxSize = 1000;
    private final int width = boxSize;
    private final int maxX = width / 2;
    private final int height = boxSize;
    private float pixelSize;
    private float particleSize;
    ODEModeler odeModeler;
    private int incrementDenominator = 3;

    public static void main(String... args) {
        PApplet.main("ProcessingTest");
    }

    public void settings() {

        size(width, height);
    }

    private void drawGrid() {
        line(0, scaleSize, 0, -scaleSize);
        line(-scaleSize, 0, scaleSize, 0);
        stroke(126);
    }

    private float nX(float x) {
        float scaledX = scaleSize / maxX;
        return x * scaledX;
    }

    private float nY(float y) {
        float scaledY = scaleSize / maxX;
        return y * scaledY;
    }

    private void drawTicks() {
        for (int x = -round(scaleSize); x <= scaleSize; x += 1) {
            line(x, 0.2f, x, -0.2f);
        }
        for (int y = -round(scaleSize); y <= scaleSize; y += 1) {
            line(-0.2f, y, 0.2f, y);
        }
    }

    private double getRotation(float x, float y) {
        return odeModeler.ode.getSlope(x, y);
    }

    private void drawLineElement(float x, float y) {
        pushMatrix();
        translate(x, y);
        rotate((float) getRotation(x, y));
        float tickWidth = pixelSize * 10;
        line(-tickWidth, 0, tickWidth, 0);
        triangle(tickWidth, 0, tickWidth * 0.75f, tickWidth * 0.2f, tickWidth * 0.75f, -(tickWidth * .2f));
        popMatrix();
    }

    void drawLineElements() {
        stroke(156.0f);
        fill(156.0f);
        for (float x = -scaleSize; x <= scaleSize; x += pixelSize * 50) {
            for (float y = -scaleSize; y <= scaleSize; y += pixelSize * 50) {
                drawLineElement(x, y);
            }
        }
    }

    void drawParticles() {
        noStroke();
        for (Particle particle : odeModeler.particles) {
            fill(particle.r, particle.g, particle.b);
            for (Point2D historicalPosition : particle.positionHistory) {
                circle((float) historicalPosition.getX(), (float) historicalPosition.getY(), particleSize);
            }
        }
        stroke(156.0f);
    }

    public void mousePressed() {
        float x = nX((mouseX - width / 2.0f));
        float y = nY(-(mouseY - height / 2.0f));
        println("adding at (" + x + ", " + y + ")");
        odeModeler.particles.add(new Particle(scaleSize, x, y));
    }

    int[] coeffs = {1,1,1,1,1,1};

    public void keyPressed() {

        switch (keyCode) {
            case 38 -> scaleSize += 1;
            case 40 -> { if (scaleSize > 1) { scaleSize -= 1; } }
            default -> { if (key == 65535) { return; } }
        }

        switch (key) {
            case 'q' -> coeffs[0] += 1;
            case 'a' -> coeffs[0] -= 1;
            case 'w' -> coeffs[1] += 1;
            case 's' -> coeffs[1] -= 1;
            case 'e' -> coeffs[2] += 1;
            case 'd' -> coeffs[2] -= 1;
            case 'r' -> coeffs[3] += 1;
            case 'f' -> coeffs[3] -= 1;
            case 't' -> coeffs[4] += 1;
            case 'g' -> coeffs[4] -= 1;
            case 'y' -> coeffs[5] += 1;
            case 'h' -> coeffs[5] -= 1;
            case 'x' -> randomOdeCoefficients();
            case 'z' -> zeroOdeCoefficients();
            case '\'' -> { shouldDrawGuides = !shouldDrawGuides; return; }
            case ',' -> { if (incrementDenominator > 1)  incrementDenominator -= 1; }
            case '.' -> incrementDenominator += 1;
            case 65535 -> {}
            default -> { return; }
        }

        updateRandomOdeModeler();
    }

    private void drawIntegralCurves() {
        for (IntegralCurve curve : odeModeler.integralCurves) {
            for (Point2D point : curve.points) {
                stroke(curve.r, curve.b, curve.g);
                fill(curve.r, curve.b, curve.g);
                //point((float)point.getX(), (float)point.getY());
                circle((float) point.getX(), (float) point.getY(), particleSize);
                noStroke();
            }
        }
    }

    FirstOrderODE compositeSlopeFunction() {
        Random random = new Random();
        List<FirstOrderODE> chosenODEs = Arrays.asList(
                odes.get(random.nextInt(odes.size())),
                odes.get(random.nextInt(odes.size()))
        );

        List<PartitionFunction> partitionFunctions = Arrays.asList(
                new VerticalPartition(chosenODEs.get(0).getScaleSize()),
                new CircularPartition(chosenODEs.get(0).getScaleSize())
        );

        PartitionFunction partitionFunction = partitionFunctions.get(random.nextInt(partitionFunctions.size()));

        return new BaseFirstOrderODE() {
            @Override
            public double dx_over_dt(double x, double y) {
                return chosenODEs.get(partitionFunction.getPartition(x, y)).dx_over_dt(x, y);
            }

            @Override
            public double dy_over_dt(double x, double y) {
                return chosenODEs.get(partitionFunction.getPartition(x, y)).dy_over_dt(x, y);
            }
        };
    }

    void randomOdeCoefficients() {
        Random random = new Random();
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = random.nextInt(10) - 5;
        }

        scaleSize = random.nextInt(9) + 1;
    }

    void zeroOdeCoefficients() {
        Arrays.fill(coeffs, 0);
    }

    void resetOdeModeler() {
        randomOdeCoefficients();
        updateRandomOdeModeler();
    }

    void updateRandomOdeModeler() {
        odeModeler = new ODEModeler(new LinearCoefficientsODE(coeffs, scaleSize), incrementDenominator);
        scaleSize = (float)odeModeler.getScaleSize();
        particleSize = (float)odeModeler.particleSize;
        pixelSize = scaleSize * 0.002f; //(1.0f / boxSize) * scaleSize * 2;
    }

    public void setup() {
        //colorMode(HSB, 360, 100, 100, 100);
        background(0);
        resetOdeModeler();
        strokeWeight(pixelSize);
    }

    boolean shouldDrawGuides = true;

    public void drawGuides() {
        if (!shouldDrawGuides) {
            return;
        }
        drawGrid();
        drawTicks();
        drawLineElements();
    }

    public void draw() {
        stroke(156.0f);
        background(0);
        scale((boxSize / (scaleSize * 2)), -(boxSize / (scaleSize * 2)));
        translate(scaleSize, -(scaleSize));
        drawGuides();
        odeModeler.update();
        drawParticles();
        drawIntegralCurves();
    }
}
