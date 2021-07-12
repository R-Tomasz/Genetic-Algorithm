package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import sample.*;



public class GameView {
    private final GameViewModel viewModel;


    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Super gra");
//        Player player = new Player(500, 300, GameViewModel.playerRadius);
        Individual player = new Individual();
        Population population = new Population(1);

//        for (Individual p : population.getPopulation()){
//            System.out.println(Arrays.deepToString(p.getGenes()));
//        }

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[10];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * (GameViewModel.sceneWidth - 49) + 25));
            obstacles[i].setY(Math.random() * (GameViewModel.sceneHeight - 49) + 25);
        }

        Pane pane = new Pane();
        Group root = new Group(obstacles);
        root.getChildren().addAll(player);
        root.getChildren().addAll(population.getPopulation());
        pane.getChildren().add(root);


        root.getChildren().add(point);

        Scene scene = new Scene(pane, GameViewModel.sceneWidth, GameViewModel.sceneHeight);
//        System.out.println(points.get(0).getLayoutBounds());
        Image image = pane.snapshot(new SnapshotParameters(), null); // image przechowuje zdjęcie wygenerowanej planszy,
                                                                                // słuzy do obliczania oddległości przeszkód od gracza


//        for (Individual p : population.getPopulation()) {
//            while(!p.isDead(obstacles)){
//                p.calcDistancesToAllObstaclesAndPoint(image);
//                p.calculateMove();
//                p.moveSomewhere();
//            }
//
//        }



        AnimationTimer timer = new AnimationTimer() {
            int klatki = 0;
            @Override
            public void handle(long now) {
                klatki++;
                klatki %= 60;


//                player.calcDistancesToAllObstaclesAndPoint(image);
//                player.calculateMove();
//                if(player.isDead(obstacles)){
//                    System.out.println("SIEMA");
//                }

                if(klatki%4==0){
                    for (Individual individual : population.getPopulation()) {
//                    int i = (int) (Math.random() * 4) + 1;
//                    if (i == 1) player.moveUp();
//                    if (i == 2) player.moveDown();
//                    if (i == 3) player.moveLeft();
//                    if (i == 4) player.moveRight();
                        viewModel.moveOnKeyPressed(scene, individual, image, obstacles);
//                        individual.calcDistancesToAllObstaclesAndPoint(image);
//                        individual.calculateMove();
//                        individual.moveSomewhere();
//                        if(individual.isDead(obstacles)){
//                            System.out.println("SIEMA");
//                            root.getChildren().remove(individual);
//                            population.getPopulation().remove(individual);
//                        }
                    }
                }



//                for (Obstacle obstacle : obstacles) {
//                    for (Individual p : population.getPopulation()) {
//
//                        if (p.intersects(obstacle.getLayoutBounds())) {
//                            root.getChildren().remove(p);
//                            population.getPopulation().remove(p);
//                        }
//                    }
//                }
//                if (player.intersects(point.getLayoutBounds()) && !point.isObtained()) {
//                    root.getChildren().remove(point);
//                    point.setObtained(true);
//                }
            }
        };
        timer.start();

//        double rangeRight = player.centerXProperty().get() + obstacles[0].getLayoutBounds().getCenterX();


//        viewModel.moveOnKeyPressed(scene, player);


//        GeneticAlgorithm pop = new GeneticAlgorithm(0.25, 0.02, 10);
        //Population population = pop.initializePopulation(10);

        stage.setScene(scene);
        stage.show();
    }
}
