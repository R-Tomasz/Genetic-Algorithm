package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import sample.*;

import java.util.Arrays;
import java.util.Collections;


public class GameView {
    private final GameViewModel viewModel;

    GeneticAlgorithm ga = new GeneticAlgorithm(0.20, 0.02);
    Population population = new Population(70);

    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
        Individual player = new Individual();
        Pane pane = new Pane();

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[40];
        for (int i = 0; i < obstacles.length - 5; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 341) + 100);
        }
        for (int i = obstacles.length - 5; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 100);
            obstacles[i].setY(Math.random() * 101);
        }

        Group root = new Group(obstacles);
//        root.getChildren().addAll(player);
        root.getChildren().addAll(population.getPopulation());
        pane.getChildren().add(root);


        root.getChildren().add(point);

        Scene scene = new Scene(pane, GameViewModel.sceneWidth, GameViewModel.sceneHeight);

        // image przechowuje zdjęcie wygenerowanej planszy, słuzy do obliczania oddległości przeszkód od gracza
        Image image = pane.snapshot(new SnapshotParameters(), null);

        viewModel.moveOnKeyPressed(scene, player);

        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            int movesCounter = 0;
            int availableMoves = 250;

            @Override
            public void handle(long now) {
                    for (Individual individual : population.getPopulation()) {
                        if (!(individual.isDead(obstacles))) {
                            individual.calcDistancesToAllObstaclesAndPoint(image);
                            individual.calculateMove();
                            individual.moveSomewhere();
                        } else {
                            root.getChildren().remove(individual);
                        }

                        if (individual.pointObtained(point)) {
                            System.out.println("WIN");
                            individual.setPointReached(true);
                            root.getChildren().remove(individual);
                        }
                        individual.individualMovesCounter +=1;
                        movesCounter++;
                    }
                    if (Collections.disjoint(root.getChildren(), population.getPopulation()) || movesCounter % (population.getPopulation().size() * availableMoves) == 0) { // sprawdzenie czy na planszy jest jakiś osobnik
                        for(Individual individ : population.getPopulation()){
                            individ.calculateFitness();
                        }
                        population.sortPopulationByFitness(); //sortowanie potrzebne do elitaryzmu
                        root.getChildren().removeAll(population.getPopulation());
                        population = ga.makeNewPopulation(population);
                        population = ga.crossover(population);
                        population = ga.mutatePopulation(population);

                        root.getChildren().addAll(population.getPopulation());
                        population.getPopulation().get(0).setFill(Color.CRIMSON);
                        population.getPopulation().get(1).setFill(Color.CRIMSON);
                        population.getPopulation().get(0).toFront();
                        population.getPopulation().get(1).toFront();

                        availableMoves+=15;
                        movesCounter = 0;

                    }
                }

        };
        timer.start();




    }
}
