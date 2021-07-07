package sample.gui.gameView;

import javafx.stage.Stage;

public class Navigator {
private final Stage primaryStage;
private final GameViewFactory gameViewFactory;

    public Navigator(Stage primaryStage, GameViewFactory gameViewFactory) {
        this.primaryStage = primaryStage;
        this.gameViewFactory = gameViewFactory;
    }

public void showMainWindow(){
gameViewFactory.create(primaryStage);
}
}
