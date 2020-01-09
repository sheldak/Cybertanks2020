import agh.iet.cs.game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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

        // TODO read from json or config file
        int width = 600;
        int height = 600;
        int menuWidth = 300;

        GameView gameView = new GameView(width, height, menuWidth);
        Scene scene = new Scene(gameView, width + menuWidth, height);

        // mouse management
//        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,
//                mouseEvent -> gameView.handleClick(mouseEvent.getX(), mouseEvent.getY()));

        Game game = new Game(gameView);

        // different thread for calculations and visualization
        Thread thread = new Thread(() -> {
            Runnable runnable = game::nextFrame;
            while (true) {
                try {
                    // length of 1 day
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