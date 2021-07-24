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
import java.util.Collections;


public class GameView {
    private final GameViewModel viewModel;

    GeneticAlgorithm ga = new GeneticAlgorithm(0.4, 0.01);
    Population population = new Population(10);

    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
        Individual player = new Individual();
        Pane pane = new Pane();

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[15];
        for (int i = 0; i < obstacles.length - 5; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 351) + 100);
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
//        System.out.println(points.get(0).getLayoutBounds());

        // image przechowuje zdjęcie wygenerowanej planszy, słuzy do obliczania oddległości przeszkód od gracza
        Image image = pane.snapshot(new SnapshotParameters(), null);

        viewModel.moveOnKeyPressed(scene, player);

        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            int movesCounter = 0;
            int availableMoves = 5;
//            int generation = 1;
            int increaseMoves = 0; // zmienna pomocnicza do zwiększania liczby ruchów o 5 co 5 pokoleń

            @Override
            public void handle(long now) {
//                frames++;
//                frames %= 60;
//                player.calcDistancesToAllObstaclesAndPoint(image);
//                if (player.isDead(obstacles)){
//                    System.out.println("BUL");
//                    root.getChildren().remove(player);
//                }
//                player.calculateFitness();
//                System.out.println(player.getFitness());
//                player.calculateMove();


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
//                            individual.setFill(Color.CRIMSON);
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
//                        population.setPopulationFitness();
                        population.sortPopulationByFitness(); //sortowanie potrzebne do elitaryzmu w selekcji
                        root.getChildren().removeAll(population.getPopulation());
                        population = ga.makeNewPopulation(population);
                        population = ga.crossover(population);
                        population = ga.mutatePopulation(population);
                        root.getChildren().addAll(population.getPopulation());

                        for(Individual individ : population.getPopulation()){
                            System.out.println(Arrays.deepToString(individ.getGenes()));
                        }
//                        System.out.println("STOP");

                        increaseMoves++;
                        if(increaseMoves%5==0) availableMoves+=10;
                        movesCounter = 0;

                    }
                }

        };
        timer.start();




    }
}
