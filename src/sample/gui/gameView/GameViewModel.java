package sample.gui.gameView;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import sample.GeneticAlgorithm;
import sample.Individual;
import sample.Obstacle;
import sample.Point;

public class GameViewModel {
    GeneticAlgorithm ga;

    public static final int playerRadius = 25;
    public static final int movement = 5;
    public static final int sceneWidth = 1000;
    public static final int sceneHeight = 600;
    public static final int pointX = 900;
    public static final int pointY = 500;



    public void moveOnKeyPressed(Scene scene, Individual individual, Image image, Obstacle[] obstacles) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case F -> {
                    individual.calcDistancesToAllObstaclesAndPoint(image);
                    individual.calculateMove();
                    individual.moveSomewhere();
                    if(individual.isDead(obstacles)){
                        System.out.println("SIEMA");
//                        root.getChildren().remove(individual);
//                        population.getPopulation().remove(individual);
                    }
                }
            }

//            switch (event.getCode()) {
//                case W -> {
//                    if (!(circle.getCenterY() - movement <= playerRadius))
//                        circle.setCenterY(circle.getCenterY() - movement);
//                }
//                case D -> {
//                    if (!(circle.getCenterX() + movement >= sceneWidth - playerRadius))
//                        circle.setCenterX(circle.getCenterX() + movement);
//                }
//                case S -> {
//                    if (!(circle.getCenterY() + movement >= sceneHeight - playerRadius))
//                        circle.setCenterY(circle.getCenterY() + movement);
//                }
//                case A -> {
//                    if (!(circle.getCenterX() - movement <= playerRadius))
//                        circle.setCenterX(circle.getCenterX() - movement);
//                }
//            }
        });
    }

    public GameViewModel(GeneticAlgorithm ga) {
        this.ga = ga;
    }
}
