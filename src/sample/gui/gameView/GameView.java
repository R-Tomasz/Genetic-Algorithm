package sample.gui.gameView;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import sample.*;

import java.util.Collections;


public class GameView {
    private final GameViewModel viewModel;
    public int enteredPopulationSize;
    public int enteredObstaclesNumber;
    public double enteredCrossoverRate;
    public double enteredMutationRate;

    Obstacle obstacles;
    AnimationTimer timer;
    GeneticAlgorithm ga = new GeneticAlgorithm(0.2, 0.02);
    Population population = new Population(60);

    public GameView(Stage stage, GameViewModel viewModel) {
        this.viewModel = viewModel;
        stage.setTitle("Gra w kulki");
        Pane pane = new Pane();
        GridPane grid = new GridPane();

        Point point = new Point(GameViewModel.pointX, GameViewModel.pointY, 30);

        Group root = new Group();
//        root.getChildren().addAll(player);
//        root.getChildren().addAll(population.getPopulation());
        pane.getChildren().add(root);



        root.getChildren().add(point);

        Scene scene = new Scene(pane, GameViewModel.sceneWidth, GameViewModel.sceneHeight);

        // image przechowuje zdjęcie wygenerowanej planszy, słuzy do obliczania oddległości przeszkód od gracza
        Image image = pane.snapshot(new SnapshotParameters(), null);

//        viewModel.moveOnKeyPressed(scene, player);

        stage.setScene(scene);
        stage.show();


        // okienko do wprowadzania danych
        grid.setPadding(new Insets(200, 450, 300, 150));
        grid.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setVgap(5);
        grid.setHgap(5);

        //opis programu
        VBox descriptionRow = new VBox();
        Text description = new Text("Gra polega na przejściu z punktu startowego do celu oznaczonego na niebiesko.\nPrzeszkody oznaczone czerwonym kolorem i generują się losowo na planszy.\nJeżeli gracz dotknie przeszkody, przegrywa.\nGracze to czarne kółka.\nDwóch najlepszych graczy z poprzedniego pokolenia mają kolor karmazynowy.");
        descriptionRow.getChildren().add(description);
        GridPane.setConstraints(descriptionRow, 1, 0,1,4);
        description.setFont(Font.font("verdana", 13));
        description.setStyle("-fx-line-spacing: 4px;");
        GridPane.setConstraints(descriptionRow, 1, 0,1,2);
        descriptionRow.setPadding(new Insets(20, 0, 0, 30));
        grid.getChildren().add(descriptionRow);

        //wielkość populacji
        VBox popSizeRow = new VBox();
        final TextField inputPopSize = new TextField("70");
        Label popSizeLabel = new Label("Wielkość populacji, optymalna 50 - 100:");
        popSizeRow.getChildren().addAll(popSizeLabel, inputPopSize);
        GridPane.setConstraints(popSizeRow, 0, 0);
        grid.getChildren().add(popSizeRow);

        //ilość przeszkód
        VBox obstaclesRow = new VBox();
        final TextField inputObstaclesNumber = new TextField("30");
        Label obstaclesNumberLabel = new Label("Ilość przeszkód, optymalna 20 - 35:");
        obstaclesRow.getChildren().addAll(obstaclesNumberLabel, inputObstaclesNumber);
        GridPane.setConstraints(obstaclesRow, 0, 1);
        grid.getChildren().add(obstaclesRow);

        //xover
        VBox crossRow = new VBox();
        final TextField inputCrossRate = new TextField("0.2");
        Label crossRateLabel = new Label("Szansa na krzyżowanie, optymalna 0.2 - 0.4:");
        crossRow.getChildren().addAll(crossRateLabel, inputCrossRate);
        GridPane.setConstraints(crossRow, 0, 2);
        grid.getChildren().add(crossRow);

        //Szansa na mutację
        VBox mutateRow = new VBox();
        final TextField inputMutationRate = new TextField("0.02");
        Label mutateRateLabel = new Label("Szansa na mutację, optymalna 0.01 - 0.03:");
        mutateRow.getChildren().addAll(mutateRateLabel, inputMutationRate);
        GridPane.setConstraints(mutateRow, 0, 3);
        grid.getChildren().add(mutateRow);

        //start
        Button start = new Button("Start");
        start.setMaxWidth(100);
        start.setAlignment(Pos.CENTER);
        GridPane.setConstraints(start, 0, 5);
        GridPane.setHalignment(start, HPos.CENTER);
        grid.getChildren().add(start);

        GridPane.getVgrow(obstaclesRow);



        root.getChildren().add(grid);


        start.setOnAction(actionEvent -> {
            enteredPopulationSize = Integer.parseInt(inputPopSize.getText());
            if(enteredPopulationSize % 2 != 0) enteredPopulationSize +=1;
            ga = new GeneticAlgorithm(Double.parseDouble(inputCrossRate.getText()), Double.parseDouble(inputMutationRate.getText()));
            population = new Population(enteredPopulationSize);
            obstacles = new Obstacle (Integer.parseInt(inputObstaclesNumber.getText()));
            root.getChildren().remove(grid);
            root.getChildren().addAll(obstacles.getObstacles());
            root.getChildren().addAll(population.getPopulation());
            run();
        });


        timer = new AnimationTimer() {
            int movesCounter = 0;
            int availableMoves = 250;

            @Override
            public void handle(long now) {
                for (Individual individual : population.getPopulation()) {
                    if (!(individual.isDead(obstacles.getObstacles()))) {
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
                    individual.individualMovesCounter += 1;
                    movesCounter++;
                }
                if (Collections.disjoint(root.getChildren(), population.getPopulation()) || movesCounter % (population.getPopulation().size() * availableMoves) == 0) { // sprawdzenie czy na planszy jest jakiś osobnik
                    for (Individual individ : population.getPopulation()) {
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

                    availableMoves += 15;
                    movesCounter = 0;

                }
            }

        };





    }
    public void run(){
        timer.start();
    }
}