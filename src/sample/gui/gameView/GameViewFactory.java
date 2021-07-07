package sample.gui.gameView;

import javafx.stage.Stage;
import sample.GeneticAlgorithm;

public class GameViewFactory {
    public GameView create(Stage stage) {
        return new GameView(stage, new GameViewModel(new GeneticAlgorithm(0.02, 0.25, 10)));
    }

}
