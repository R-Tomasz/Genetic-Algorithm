package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import sample.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class GameView {
    private final GameViewModel viewModel;

    GeneticAlgorithm ga = new GeneticAlgorithm(0.2, 0.25);
    Population population = ga.initializePopulation(40);

    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
//        Player player = new Player(500, 300, GameViewModel.playerRadius);

        Individual player = new Individual();

//        for (Individual p : population.getPopulation()){
//            System.out.println(Arrays.deepToString(p.getGenes()));
//        }

//        System.out.println("1:  "+ Arrays.deepToString(population.getPopulation().get(0).getGenes()));
//        System.out.println("2:  "+ Arrays.deepToString(population.getPopulation().get(1).getGenes()));
//        System.out.println("3:  "+ Arrays.deepToString(population.getPopulation().get(2).getGenes()));
//        System.out.println("4:  "+ Arrays.deepToString(population.getPopulation().get(3).getGenes()));
//        System.out.println("5:  "+ Arrays.deepToString(population.getPopulation().get(4).getGenes()));
        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[15];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 351) + 100);
        }

        Pane pane = new Pane();
        Group root = new Group(obstacles);
//        root.getChildren().addAll(player);
        root.getChildren().addAll(population.getPopulation());
        pane.getChildren().add(root);


        root.getChildren().add(point);

        Scene scene = new Scene(pane, GameViewModel.sceneWidth, GameViewModel.sceneHeight);
//        System.out.println(points.get(0).getLayoutBounds());

        // image przechowuje zdjęcie wygenerowanej planszy, słuzy do obliczania oddległości przeszkód od gracza
        Image image = pane.snapshot(new SnapshotParameters(), null);

        viewModel.moveOnKeyPressed(scene, player);

        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            int frames = 0;
            int moves = 5;

            @Override
            public void handle(long now) {
                frames++;
                frames %= 60;
//                player.calcDistancesToAllObstaclesAndPoint(image);
//                if (player.isDead(obstacles)){
//                    System.out.println("BUL");
//                    root.getChildren().remove(player);
//                }
//                player.calculateFitness();
//                System.out.println(player.getFitness());
//                player.calculateMove();

//                if (frames % 1 == 0) {
//                    outer: while (population.getPopulationFitness() != 1000) {
                    for (int i = 0; i < moves; i++) {

                        for (Individual individual : population.getPopulation()) {
                            if (!(individual.isDead(obstacles))) {
//                                int i = (int) (Math.random() * 4) + 1;
//                                if (i == 1) individual.moveUp();
//                                if (i == 2) individual.moveDown();
//                                if (i == 3) individual.moveLeft();
//                                if (i == 4) individual.moveRight();
                                individual.calcDistancesToAllObstaclesAndPoint(image);
                                individual.calculateMove();
                                individual.moveSomewhere();
                            } else {
                                individual.calculateFitness();
//                                System.out.println(individual + "     "+individual.getFitness());
                                root.getChildren().remove(individual);
                            }

//                        viewModel.moveOnKeyPressed(scene, individual, image, obstacles);

                            if (individual.pointObtained(point)) {
                                System.out.println("WIN");
                                individual.setPointReached(true);
                                root.getChildren().remove(individual);
                            }
                        }
                        if (Collections.disjoint(root.getChildren(), population.getPopulation()) || (moves % 5 == 0 && moves != 5)) { // sprawdzenie czy na planszy jest jakiś osobnik
                            population.setPopulationFitness();
                            root.getChildren().removeAll(population.getPopulation());
                            population = ga.makeNewPopulation(population);
                            population = ga.crossover(population);
                            population = ga.mutatePopulation(population);
                            root.getChildren().addAll(population.getPopulation());
                        }
                    }
                    moves++;
//                }
            }
        };
        timer.start();


    }
}
