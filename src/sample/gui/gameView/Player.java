package sample.gui.gameView;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.Obstacle;
import sample.Point;

public class Player extends Circle {
    boolean pointReached;

    public Player(int positionX, int positionY, double radius) {
        super(positionX, positionY, radius);
    }

    public void moveUp() {
        if (!(getCenterY() - GameViewModel.movement <= GameViewModel.playerRadius))
            setCenterY(getCenterY() - GameViewModel.movement);
    }

    public void moveLeft() {
        if (!(getCenterX() - GameViewModel.movement <= GameViewModel.playerRadius)){
            setCenterX(getCenterX() - GameViewModel.movement);
        }
    }

    public void moveDown() {
        if (!(getCenterY() + GameViewModel.movement >= GameViewModel.sceneHeight - GameViewModel.playerRadius))
            setCenterY(getCenterY() + GameViewModel.movement);
    }

    public void moveRight() {
        if (!(getCenterX() + GameViewModel.movement >= GameViewModel.sceneWidth - GameViewModel.playerRadius)){
            setCenterX(getCenterX() + GameViewModel.movement);
        }
    }

    public boolean isDead(Obstacle[] obstacles){
        for (Obstacle o : obstacles){
            Shape intersect = Shape.intersect(this,o); //tworzenie obiektu z części wspólnej kształtów gracza i przeszkody
            if (intersect.getBoundsInLocal().getWidth() != -1) { // jeżeli istnieje, znaczy że była kolizja
                return true;
            }
        }
        return false;
    }

    public boolean pointObtained(Point point){
        Shape intersect = Shape.intersect(this, point);
        if (intersect.getBoundsInLocal().getWidth() != -1) { // jeżeli istnieje, znaczy że była kolizja
            return true;
        }
        return false;
    }

    public void setPointReached(boolean pointReached) {
        this.pointReached = pointReached;
    }
}
