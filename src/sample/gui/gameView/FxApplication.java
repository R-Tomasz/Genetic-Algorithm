package sample.gui.gameView;

import javafx.application.Application;
import javafx.stage.Stage;

public class FxApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navigator navigator = new Navigator(
                primaryStage,
                new GameViewFactory()
                );
        navigator.showMainWindow();
    }
}
