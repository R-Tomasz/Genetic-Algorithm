package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle  {
    double width;
    double height;

    public Obstacle(double width, double height) {
        super(width,height, Color.RED);
        this.width = width;
        this.height = height;
    }
}
