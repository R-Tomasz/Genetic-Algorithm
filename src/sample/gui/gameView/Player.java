package sample.gui.gameView;

import javafx.scene.shape.Circle;
import sample.Obstacle;

public class Player extends Circle {
    public Player(int positionX, int positionY, double radius) {
        super(positionX, positionY, radius);
    }

    public void moveUp() {
        if (!(getCenterY() - GameViewModel.movement <= GameViewModel.playerRadius))
            setCenterY(getCenterY() - 5);
    }

    public void moveDown() {
        if (!(getCenterY() + GameViewModel.movement >= GameViewModel.sceneHeight - GameViewModel.playerRadius))
            setCenterY(getCenterY() + 5);
    }

    public void moveLeft() {
        if (!(getCenterX() - GameViewModel.movement <= GameViewModel.playerRadius))
            setCenterX(getCenterY() - 5);
    }

    public void moveRight() {
        if (!(getCenterX() + GameViewModel.movement >= GameViewModel.sceneWidth - GameViewModel.playerRadius))
            setCenterX(getCenterX() + 5);
    }

    public boolean isDead(Obstacle[] obstacles){
        for (Obstacle o : obstacles){
            if(this.intersects(o.getLayoutBounds())){
//                System.out.println("ZDECHLEM");
                return true;
            }
        }
        return false;
    }
}
