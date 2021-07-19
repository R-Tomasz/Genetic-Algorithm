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
    public static final int pointX = 950;
    public static final int pointY = 550;


    public void moveOnKeyPressed(Scene scene, Player circle /*Individual individual, Image image, Obstacle[] obstacles*/) {
        scene.setOnKeyPressed(event -> {
//            switch (event.getCode()){
//                case F -> {
//                    individual.calcDistancesToAllObstaclesAndPoint(image);
//                    individual.calculateMove();
//                    individual.moveSomewhere();
//                    if(individual.isDead(obstacles)){
////                        System.out.println("SIEMA");
////                        root.getChildren().remove(individual);
////                        population.getPopulation().remove(individual);
//                    }
//                }
//            }

            switch (event.getCode()) {
                case W -> circle.setCenterY(circle.getCenterY() - movement);
                case D -> circle.setCenterX(circle.getCenterX() + movement);
                case S -> circle.setCenterY(circle.getCenterY() + movement);
                case A -> circle.setCenterX(circle.getCenterX() - movement);

            }
        });
    }

    public GameViewModel(GeneticAlgorithm ga) {
        this.ga = ga;
    }
}
