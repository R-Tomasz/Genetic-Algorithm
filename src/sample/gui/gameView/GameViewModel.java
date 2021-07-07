package sample.gui.gameView;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import sample.GeneticAlgorithm;
import sample.Individual;
import sample.Point;

public class GameViewModel {
    GeneticAlgorithm ga;

    public static final int playerRadius = 25;
    private static final int movement = 5;
    public static final int sceneWidth = 1000;
    public static final int sceneHeight = 600;
    public static final int playerX = 500;
    public static final int playerY = 300;


    public void moveOnKeyPressed(Scene scene, final Player circle) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> {
                    if (!(circle.getCenterY() - movement <= playerRadius))
                        circle.setCenterY(circle.getCenterY() - movement);
//                        circle.setVelocityY(-movement);
//                    System.out.println(circle.getVelocityY());
                }
                case D -> {
//                    circle.setVelocityX(movement);
//                    circle.setCenterX(circle.getVelocityX());
//                    System.out.println(circle.getVelocityX());
                    if (!(circle.getCenterX() + movement >= sceneWidth - playerRadius))
                        circle.setCenterX(circle.getCenterX() + movement);
                }
                case S -> {
//                    circle.setVelocityY(movement);
//                    System.out.println(circle.getVelocityY());
                    if (!(circle.getCenterY() + movement >= sceneHeight - playerRadius))
                        circle.setCenterY(circle.getCenterY() + movement);
                }
                case A -> {
//                    circle.setVelocityX(-movement);
//                    System.out.println(circle.getVelocityX());
                    if (!(circle.getCenterX() - movement <= playerRadius))
                        circle.setCenterX(circle.getCenterX() - movement);
                }
            }
        });
    }

    public GameViewModel(GeneticAlgorithm ga) {
        this.ga = ga;
    }
}
