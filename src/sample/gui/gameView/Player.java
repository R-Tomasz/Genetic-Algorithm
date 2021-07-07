package sample.gui.gameView;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import sample.Obstacle;
import sample.Point;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Player extends Circle {
    private double radius;
    private double positionX;
    private double positionY;
    private double velocityX = 0;
    private double velocityY = 0;
    private BigDecimal points;
    private ArrayList<Double> distanceToLeft;
    private ArrayList<Double> distanceToRight;
    private ArrayList<Double> distanceToTop;
    private ArrayList<Double> distanceToBottom;
//    EventHandler<ActionEvent> btnDownActionEvent = new EventHandler<ActionEvent>()
//    {
//        @Override
//        public void handle(ActionEvent event)
//        {
//            System.out.println("Pressed Down");
//            newY = circle.getCenterY() + 10 + newY;
//
//            circle.setTranslateX(newX);
//            circle.setTranslateY(newY);
//        }
//    };


    public Player(double positionX, double positionY, double radius) {
        super(positionX, positionY, radius);
        this.radius = radius;
        this.positionX = positionX;
        this.positionY = positionY;
        points = new BigDecimal(0);
    }

    public void tick(){
        positionX+=velocityX;
        positionY+=velocityY;
    }

    public void moveUp(){
        positionY +=5;
    }
    public void moveDown(){
        positionY -=5;
    }
    public void moveLeft(){
        positionX -=5;
    }
    public void moveRight(){
        positionX +=5;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public boolean collide(Obstacle obs) {
        return this.intersects(obs.getLayoutBounds());
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public ArrayList<Double> getDistanceToLeft() {
        return distanceToLeft;
    }

    public ArrayList<Double> getDistanceToRight() {
        return distanceToRight;
    }

    public ArrayList<Double> getDistanceToTop() {
        return distanceToTop;
    }

    public ArrayList<Double> getDistanceToBottom() {
        return distanceToBottom;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void addPoint(BigDecimal p) {
        points = points.add(p);
    }


}
