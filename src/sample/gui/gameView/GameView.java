package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import sample.*;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GameView {
    private final GameViewModel viewModel;


    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
        Player player = new Player(GameViewModel.playerX, GameViewModel.playerY, GameViewModel.playerRadius);
//        Individual player = new Individual();
        Population population = new Population(10);
//        Player p1 = new Player(GameViewModel.playerX, GameViewModel.playerY, GameViewModel.playerRadius);
//        Player p2 = new Player(GameViewModel.playerX, GameViewModel.playerY, GameViewModel.playerRadius);

        //System.out.println(population);

        int pointsSize = 10;
        ArrayList<Point> points = new ArrayList<>(pointsSize);


        Obstacle[] obstacles = new Obstacle[10];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * (GameViewModel.sceneWidth - 49) + 25));
            obstacles[i].setY(Math.random() * (GameViewModel.sceneHeight - 49) + 25);
        }

        Group root = new Group(obstacles);
        root.getChildren().addAll(player);
        //root.getChildren().addAll(population.getPopulation());

        for (int i = 0; i < pointsSize; i++) {
            points.add(new Point(((Math.random() * (GameViewModel.sceneWidth - 49)) + 25), ((Math.random() * (GameViewModel.sceneHeight - 49)) + 25), (Math.random() * 30) + 8, (Math.random() * 30) + 8));
            root.getChildren().add(points.get(i));
        }

        Scene scene = new Scene(root, GameViewModel.sceneWidth, GameViewModel.sceneHeight);
        System.out.println(points.get(0).getLayoutBounds());


        final double[] rangeRight = {0};
        final double[] rangeLeft = {0};


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
//                if (player.getLayoutBounds().getCenterX() < obstacles[0].getBoundsInLocal().getCenterX()) {
//                    rangeRight[0] = obstacles[0].getBoundsInLocal().getCenterX() - player.centerXProperty().get();
//                    rangeLeft[0] = 0;
//                } else {
//                    rangeLeft[0] = player.centerXProperty().get() - obstacles[0].getBoundsInLocal().getCenterX() ;
//                    rangeRight[0] = 0;
//                }

                for (Obstacle obstacle : obstacles) {

//                    for(Individual player : population.getPopulation()){
                    if (player.intersects(obstacle.getLayoutBounds())) {
                        root.getChildren().remove(player);
                            population.getPopulation().remove(player); //tu cos jakis blad chyba
//                    }
                    }
                }
                for (Point point : points) {
//                    for(Individual player : population.getPopulation()){
                    if (player.intersects(point.getLayoutBounds()) && !point.isObtained()) {
                        player.addPoint(point.getPointValue());
                        root.getChildren().remove(point);
                        points.remove(point);
                        Point newPoint = new Point(((Math.random() * (GameViewModel.sceneWidth - 49)) + 25), ((Math.random() * (GameViewModel.sceneHeight - 49)) + 25), (Math.random() * 30) + 8, (Math.random() * 30) + 8);
                        points.add(newPoint);
                        root.getChildren().add(newPoint);
                        point.setObtained(true);
                        }
//                    }
                    System.out.println(player.getPoints());
//                    System.out.println("odleglosc z prawej: " + rangeRight[0]);
//                    System.out.println("odleglosc z lewej: " + rangeLeft[0]);
                }
            }
        };
        timer.start();
        obstacles[0].setFill(Color.YELLOW);
population.getPopulation().get(1).setFill(Color.CHOCOLATE);

//        double rangeRight = player.centerXProperty().get() + obstacles[0].getLayoutBounds().getCenterX();

//        p1.setFill(Color.CHOCOLATE);
//        p2.setFill(Color.CRIMSON);
//        root.getChildren().addAll(p1, p2);
//        viewModel.moveOnKeyPressed(scene, p1);
        viewModel.moveOnKeyPressed(scene, player);


//        System.out.println(rangeRight);



        stage.setScene(scene);


        GeneticAlgorithm pop = new GeneticAlgorithm(0.25, 0.02, 10);
        //Population population = pop.initializePopulation(10);


        stage.show();
    }
}
