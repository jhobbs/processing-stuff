import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;

public class ProcessingTest extends PApplet {

    private final int boxSize = 1000;
    private final int width = boxSize;
    private final int height = boxSize;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        int maxY = height / 2;
        line(0, maxY, 0, -maxY);
        int maxX = width / 2;
        line(-maxX, 0, maxX, 0);
        stroke(126);
    }

    public void setup() {
        background(0);
        strokeWeight(2);
    }

    public void draw(){
        background(0);
        translate(height/ 2, width / 2);
    //    drawVectorsPolar();
        drawGrid();
        drawPendulums();
    }

    private void drawPendulums() {
        for (Pendulum pendulum: pendulums) {
            pendulum.draw();
        }
    }

    private PVector translatedMouse() {
        return new PVector(mouseX - width/2, -(mouseY - height/2));
    }

    final private ArrayList<Pendulum> pendulums = new ArrayList<>();

    public void mousePressed() {
        PVector startCoords = new PVector(0, 0);
        PVector mouseCoords = translatedMouse();
        Pendulum pendulum = new Pendulum(startCoords, mouseCoords, this);
        pendulums.add(pendulum);
    }

    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
