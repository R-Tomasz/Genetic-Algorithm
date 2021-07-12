package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Point extends Circle {
    private Image image;
    private BigDecimal pointValue;
    private boolean obtained;

    public BigDecimal getPointValue() {
        return pointValue;
    }

    public Point(double posX, double posY, double radius){
        super(posX, posY, radius);
        this.setFill(Color.BLUE);
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    public boolean isObtained() {
        return obtained;
    }
}
