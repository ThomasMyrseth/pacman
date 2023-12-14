package packman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
   private GameController gameController;
   private AnimationTimer gameLoop;
   private static final long UPDATE_INTERVALL = 1000000000/10; // nanosekunder
   private long lastUpDateTime=0;

   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws IOException {
         primaryStage.setTitle("Packman");
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/packman/GameApp.fxml"));
         Scene scene = new Scene(loader.load());
         primaryStage.setScene(scene);

         gameController = loader.getController();

         scene.setOnKeyPressed(e -> onKeyPressed(e));
         primaryStage.show();

         gameLoop = new AnimationTimer() {
            public void handle(long now) {
               if (now -lastUpDateTime>UPDATE_INTERVALL) {
               update();
               render();
               lastUpDateTime=now;
               }
           }
         };
         gameLoop.start();
  }

   public void onKeyPressed(KeyEvent e) {
      gameController.handle(e);
   }
   public void update() {
      this.gameController.updateGame();
      if (gameController.getGame().isGameOver()) {
         gameController.showGameoverScreen();
         gameLoop.stop();
         gameController.handle(null);
      }
   }
   public void render() {
      gameController.renderGame();
   }
      
}