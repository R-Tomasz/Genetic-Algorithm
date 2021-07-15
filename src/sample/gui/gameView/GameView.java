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
        GeneticAlgorithm ga = new GeneticAlgorithm(0.2, 0.25);
        Population population = ga.initializePopulation(5);

        Individual player = new Individual();

//        for (Individual p : population.getPopulation()){
//            System.out.println(Arrays.deepToString(p.getGenes()));
//        }

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        // wygenerowanie przeszkód
        Obstacle[] obstacles = new Obstacle[20];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 20) + 20, (int) (Math.random() * 90) + 10);
            obstacles[i].setX((Math.random() * 901) + 20);
            obstacles[i].setY((Math.random() * 351) + 100);
        }
// min 20    max 980     min 100 max 500
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

//        population.getPopulation().get(0).setCenterX(850);
//        population.getPopulation().get(0).setCenterY(500);
//        population.getPopulation().get(1).setCenterX(750);
//        population.getPopulation().get(1).setCenterY(400);
//        population.getPopulation().get(2).setCenterX(650);
//        population.getPopulation().get(2).setCenterY(300);
//        population.getPopulation().get(0).calcDistancesToAllObstaclesAndPoint(image);
//        population.getPopulation().get(1).calcDistancesToAllObstaclesAndPoint(image);
//        population.getPopulation().get(2).calcDistancesToAllObstaclesAndPoint(image);
//        population.getPopulation().get(0).calculateFitness();
//        population.getPopulation().get(1).calculateFitness();
//        population.getPopulation().get(2).calculateFitness();
//        System.out.println("FITNESS: ");
//        System.out.println("1:  " + population.getPopulation().get(0).getFitness());
//        System.out.println("2:  " + population.getPopulation().get(1).getFitness());
//        System.out.println("3:  " + population.getPopulation().get(2).getFitness());
//        population.sortPopulationByFitness();
//        population.setPopulationFitness();
//        System.out.println(population.getPopulationFitness());

//        for (Individual p : population.getPopulation()) {
//            while(!p.isDead(obstacles)){
//                p.calcDistancesToAllObstaclesAndPoint(image);
//                p.calculateMove();
//                p.moveSomewhere();
//            }
//
//        }


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
                    while (!(population.getPopulationFitness() == 1000)) {
                        System.out.println(frames);
                        for (Individual individual : population.getPopulation()) {

                            int i = (int) (Math.random() * 4) + 1;
                            if (i == 1) individual.moveUp();
                            if (i == 2) individual.moveDown();
                            if (i == 3) individual.moveLeft();
                            if (i == 4) individual.moveRight();
                            individual.calcDistancesToAllObstaclesAndPoint(image);
//                            individual.calculateMove();
//                            individual.moveSomewhere();
//
                            individual.calculateFitness();

                            if (individual.isDead(obstacles)) {
                                individual.calculateFitness();
                                System.out.println("EEEE");
                                System.out.println(individual.getCenterX());
                                System.out.println(individual.getCenterY());
                                root.getChildren().remove(individual);
                                population.getPopulation().remove(individual);
                            }


                            individual.setPointReached(true);
//                        viewModel.moveOnKeyPressed(scene, individual, image, obstacles);
//                        System.out.println(ga.tournamentSelection(population));

//                        if (player.pointObtained(point)){
//                            root.getChildren().remove(player);
//                        }
                        }
                        break;
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


        viewModel.moveOnKeyPressed(scene, player);

        stage.setScene(scene);
        stage.show();
    }
}
