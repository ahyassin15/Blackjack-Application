package loginassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import loginassignment.Blackjack.BlackjackMenuController;
import loginassignment.Blackjack.Game;
import loginassignment.Blackjack.LeaderboardController;
import loginassignment.LoginGUI.Login;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Login.showLogin();
    }

    // Opens the game menu on the same stage as the login screen
    public void startBlackjackMenu(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack/BlackjackMenu.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        BlackjackMenuController controller = loader.getController();
        stage.setScene(scene);
        controller.getScene(stage);
        stage.show();
    }

    // Opens the game on the same stage as the game menu screen
    public void startBlackjackGame(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack/Blackjack.fxml"));
        Pane root = (Pane) loader.load();
        Scene scene = new Scene(root);
        Game controller = loader.getController();
        stage.setScene(scene);
        controller.getScene(stage);
        stage.show();
    }

    // Opens the leaderboard on a different stage as the menu screen (pop up kinda)
    public void startLeaderboard() throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack/Leaderboard.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        LeaderboardController controller = loader.getController();
        stage.setScene(scene);
        controller.getScene(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}