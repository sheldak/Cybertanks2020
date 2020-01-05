import game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import view.GameView;

import java.util.concurrent.TimeUnit;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Cybertanks 2020");

        GameView gameView = new GameView();
        Scene scene = new Scene(gameView, 1100, 400);

        // mouse management
//        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,
//                mouseEvent -> gameView.handleClick(mouseEvent.getX(), mouseEvent.getY()));

        Game game = new Game();

        // different thread for calculations and visualization
        Thread thread = new Thread(() -> {
            Runnable runnable = game::step;
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