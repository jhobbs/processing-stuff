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
        scale(1, -1);
        translate(width/ 2, -(height / 2));
    //    drawVectorsPolar();
        drawGrid();
        line(0, 0, 250, 250);
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

    PVector startCoords;

    public void mousePressed() {
        startCoords = translatedMouse();
    }

    public void mouseReleased() {
        PVector mouseCoords = translatedMouse();
        Pendulum pendulum = new Pendulum(startCoords, mouseCoords, this);
        pendulums.add(pendulum);
    }

    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
