package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Licencjat");
        GridPane root = new GridPane();
        primaryStage.setScene(new Scene(root, 500, 500));

//        GeneticAlgorithm pop = new GeneticAlgorithm(0.25,0.02, 10);
//        Population population = pop.initializePopulation(10);
//        System.out.println(population);

//        Population pop = new Population(10).initializePopulation();

        Button btn = new Button("txt");




        btn.setOnAction(e ->
        {
//            Label txt2 = new Label(pop.toString());
//            root.getChildren().add(txt2);
            //Population pop2 = mutatePopulation(pop);
//            System.out.println(pop.rouletteChances);
//            for(int i = 0 ; i<200; i++){
////                if((int) (Math.random() * pop.getPopulationSize() +1) <0.05){
////                    System.out.println((int) (Math.random() * pop.getPopulationSize()));
////                }
//            }
//            System.out.println("pop size" + pop.getPopulation());
        });
//        root.getChildren().add(txt);
        //root.getChildren().add(inputPopSize);
        root.getChildren().add(btn);


        primaryStage.show();
    }



}
