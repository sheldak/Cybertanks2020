import agh.iet.cs.game.Game;
import agh.iet.cs.game.GameState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import agh.iet.cs.view.GameView;

import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Cybertanks 2020");

        int width = 600;
        int height = 600;
        int menuWidth = 300;

        GameState gameState = new GameState();

        GameView gameView = new GameView(width, height, menuWidth, gameState);
        Scene scene = new Scene(gameView, width + menuWidth, height);

        Game game = new Game(gameView, gameState);

        // mouse management
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    if (mouseEvent.isPrimaryButtonDown())
                        game.handleLeftClick((int) mouseEvent.getX(), (int) mouseEvent.getY());
                    else if (mouseEvent.isSecondaryButtonDown())
                        game.handleRightClick((int) mouseEvent.getX(), (int) mouseEvent.getY());
                });

        // different thread for calculations and visualization
        Thread thread = new Thread(() -> {
            Runnable runnable = game::changesListener;
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("View thread sleep problem");
                }

                Platform.runLater(runnable);
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}