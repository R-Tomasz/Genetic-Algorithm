package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import sample.*;

import java.util.Collections;


public class GameView {
    private final GameViewModel viewModel;

    GeneticAlgorithm ga = new GeneticAlgorithm(0.2, 0.25);
    Population population = ga.initializePopulation(100);

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
            int frames = 0;
            int temp = 0;
            int counter = 5;

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
                        temp++;
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
                            System.out.println("WIN");
                            individual.setPointReached(true);
                            root.getChildren().remove(individual);
                        }
                    }
                    if (Collections.disjoint(root.getChildren(), population.getPopulation()) || temp % (population.getPopulation().size() * counter) == 0) { // sprawdzenie czy na planszy jest jakiś osobnik
                        population.setPopulationFitness();
                        root.getChildren().removeAll(population.getPopulation());
                        population = ga.makeNewPopulation(population);
                        population = ga.crossover(population);
                        population = ga.mutatePopulation(population);
                        root.getChildren().addAll(population.getPopulation());
                        counter+=5;
                        temp = 0;
                        System.out.println(counter);
                    }
                }

        };
        timer.start();




    }
}
