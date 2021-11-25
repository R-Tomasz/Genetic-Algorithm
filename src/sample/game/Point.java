package sample.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {
    public Point(double posX, double posY, double radius){
        super(posX, posY, radius);
        this.setFill(Color.BLUE);
    }
}
