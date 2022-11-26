import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;

public class ProcessingTest extends PApplet {

    private final int boxSize = 1000;
    private final int width = boxSize;
    private final int height = boxSize;

    private final int maxX = width / 2;
    private final int maxY = height / 2;

    private final int spacing = boxSize / 20;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        line(0, maxY, 0, -maxY);
        line(-maxX, 0, maxX, 0);
        stroke(126);
    }

    private void drawVectorsPolar() {
        float angularSpacing = TWO_PI * ((float)spacing/boxSize);
        for (int r = maxY - spacing; r > 0; r -= spacing) {
            for (float theta = 0; theta < TWO_PI; theta += angularSpacing) {
                //println("theta: " + theta + " r: " + r);
                PVector point = Util.p2c(r, theta);
                PVector endpoint = Util.p2c(r, theta + (float)spacing/4);
                point(point.x, point.y);
                line(point.x, point.y, endpoint.x, endpoint.y);
            }
        }
    }

    public void setup() {
        background(0);
        strokeWeight(2);
    }

    public void draw(){
        background(0);
        translate(height/ 2, width / 2);
        drawVectorsPolar();
        drawGrid();
        drawPendulums();
    }

    private void drawPendulums() {
        for (Pendulum pendulum: pendulums) {
            pendulum.draw();
        }
    }

    final private ArrayList<Pendulum> pendulums = new ArrayList<>();

    public void mousePressed() {
        int actualX = mouseX - width/2;
        int actualY = -(mouseY - height/2);
        println("x: " + actualX + " y: " + actualY);
        float r = sqrt(pow(actualX, 2) + pow(actualY, 2));
        float theta = atan((float)actualY / actualX);
        if (actualX < 0) {
            theta += PI;
        }
        Pendulum pendulum = new Pendulum(this, theta, r, 0);
        pendulums.add(pendulum);
        println("Adding pendulum at r: " + r + " theta: " + theta);
    }

    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
