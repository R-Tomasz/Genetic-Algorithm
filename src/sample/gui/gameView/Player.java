package sample.gui.gameView;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.Obstacle;
import sample.Point;

public class Player extends Circle {
    boolean pointReached;
    public int individualMovesCounter =0;

    public Player(int positionX, int positionY, double radius) {
        super(positionX, positionY, radius);
    }

    public void moveUp() {
//        if (!(getCenterY() - GameViewModel.movement <= GameViewModel.playerRadius))
            setCenterY(getCenterY() - GameViewModel.movement);
    }

    public void moveLeft() {
//        if (!(getCenterX() - GameViewModel.movement <= GameViewModel.playerRadius))
            setCenterX(getCenterX() - GameViewModel.movement);

    }

    public void moveDown() {
//        if (!(getCenterY() + GameViewModel.movement >= GameViewModel.sceneHeight - GameViewModel.playerRadius))
            setCenterY(getCenterY() + GameViewModel.movement);
    }

    public void moveRight() {
//        if (!(getCenterX() + GameViewModel.movement >= GameViewModel.sceneWidth - GameViewModel.playerRadius))
            setCenterX(getCenterX() + GameViewModel.movement);
    }

    public boolean isDead(Obstacle[] obstacles){
        for (Obstacle o : obstacles){
            Shape intersect = Shape.intersect(this,o); //tworzenie obiektu z części wspólnej kształtów gracza i przeszkody
            if (intersect.getBoundsInLocal().getWidth() != -1) { // jeżeli istnieje, znaczy że była kolizja
                return true;
            }
            if(this.getCenterY()<= GameViewModel.playerRadius || this.getCenterX() <= GameViewModel.playerRadius || this.getCenterY() >= GameViewModel.sceneHeight - GameViewModel.playerRadius || this.getCenterX()>=GameViewModel.sceneWidth-GameViewModel.playerRadius)
                return true;
        }
        return false;
    }

    public boolean pointObtained(Point point){
        Shape intersect = Shape.intersect(this, point);
        return intersect.getBoundsInLocal().getWidth() != -1; // jeżeli istnieje, znaczy że była kolizja
    }

    public void setIndividualMovesCounter(int individualMovesCounter) {
        this.individualMovesCounter = individualMovesCounter;
    }

    public int getIndividualMovesCounter() {
        return individualMovesCounter;
    }

    public boolean isPointReached() {
        return pointReached;
    }

    public void setPointReached(boolean pointReached) {
        this.pointReached = pointReached;
    }
}
