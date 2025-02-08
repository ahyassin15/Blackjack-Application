package main.java.com.blackjack.Game;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import loginassignment.App;

public class BlackjackMenuController {

    Stage stage;
    Scene gameScene;

    @FXML
    private Button openGameGUIButton;
    
    @FXML
    private Button openTopGUIButton;

    @FXML
    private void initialize() {

    }

    public void openGameGUI(ActionEvent event) throws IOException {
        App app = new App();
        try {
            // Opens Game
            app.startBlackjackGame(stage);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void openTopGUI(ActionEvent event) {
        App app = new App();
        try {
            // Opens Leaderboard
            app.startLeaderboard();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void getScene(Stage stage) {
        this.stage = stage;
		gameScene = stage.getScene();
	}
    
}  
