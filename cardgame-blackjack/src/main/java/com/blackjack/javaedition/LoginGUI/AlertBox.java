package com.blackjack.javaedition.LoginGUI;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String title, String message, String buttonText) {
        // Create new stage
        Stage window = new Stage();

        // Set Modality
        window.initModality(Modality.APPLICATION_MODAL);

        // Set the to the passed variable
        window.setTitle(title);

        // Create the content of the AlertBox with the passed in message variable
        Label label = new Label(message);
        // Create the button with the passed in text for the button
        Button closeButton = new Button(buttonText);
        // Close the alert box when the button is clicked
        closeButton.setOnAction(e -> window.close());

        // Create a VBox layout 
        VBox layout = new VBox(10);
        // Add the label and the button to the layout
        layout.getChildren().addAll(label, closeButton);
        // Center everything
        layout.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(layout, 250, 100);
        // Use the style.css to make the button underlined
        scene.getStylesheets().add(new File("cardgame-blackjack/src/main/java/com.blackjack.javaedition/style.css").toURI().toString());

        // show the window
        window.setScene(scene);

        window.showAndWait();
    }
}
