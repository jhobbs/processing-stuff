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

    private final int scaledX = maxX / scaledSize;
    private final int scaledY = maxY / scaledSize;

    private final int spacing = boxSize / 20;

    public void settings(){
        size(width, height);
    }

    private void drawGrid() {
        line(0, maxY, 0, -maxY);
        line(-maxX, 0, maxX, 0);
        stroke(126);
    }

    private float sX(float x) {
        return x * scaledX;
    }

    private float sY(float y) {
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

    private float getSlope(float x, float y) {
        if (x + y == 0)
            return 100;
        return (x - y) / (x + y);
    }

    private void drawLineElement(float x, float y) {
        float slope = getSlope(x, y);
        pushMatrix();
        translate(sX(x), sY(y));
        rotate(atan(slope));
        line(sX(- 0.1f), 0, sX(0.1f), 0);
        popMatrix();
    }

    void drawLineElements() {
        for (float x = -scaledSize; x <= scaledSize; x += 0.4) {
            for (float y = -scaledSize; y <= scaledSize; y += 0.2) {
                drawLineElement(x, y);
            }
        }
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
    }


    public static void main(String... args){
        PApplet.main("ProcessingTest");
    }
}
