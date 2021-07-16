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


    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
//        Player player = new Player(500, 300, GameViewModel.playerRadius);
        GeneticAlgorithm ga = new GeneticAlgorithm(0.2, 0.25);
        final Population[] population = {ga.initializePopulation(4)};

        Individual player = new Individual();

//        for (Individual p : population.getPopulation()){
//            System.out.println(Arrays.deepToString(p.getGenes()));
//        }

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[40];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 351) + 100);
        }

        Pane pane = new Pane();
        Group root = new Group(obstacles);
//        root.getChildren().addAll(player);
        root.getChildren().addAll(population[0].getPopulation());
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

            @Override
            public void handle(long now) {
                frames++;
                frames %= 60;


//                player.calcDistancesToAllObstaclesAndPoint(image);
//                player.calculateFitness();
//                System.out.println(player.getFitness());
//                player.calculateMove();

                if (frames % 4 == 0) {
//                    outer: while (population.getPopulationFitness() != 1000) {

                        for (Individual individual : population[0].getPopulation()) {
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
                                root.getChildren().remove(individual);
                            }

//                        viewModel.moveOnKeyPressed(scene, individual, image, obstacles);

                            if (individual.pointObtained(point)) {
                                individual.setPointReached(true);
                                root.getChildren().remove(individual);
                            }
                            if (Collections.disjoint(root.getChildren(), population[0].getPopulation())){ // sprawdzenie czy na planszy jest jakiś osobnik
                                population[0].setPopulationFitness();
                                population[0] = ga.makeNewPopulation(population[0]);
                                population[0] = ga.crossover(population[0]);
                                population[0] = ga.mutatePopulation(population[0]);
                                population[0].sortPopulationByFitness();
                                System.out.println(population[0].getPopulationFitness());
                            }
                        }
                    }
                }
//            }
        };
        timer.start();


    }
}
