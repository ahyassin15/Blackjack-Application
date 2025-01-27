package com.blackjack.javaedition.LoginGUI;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginassignment.App;
import loginassignment.Blackjack.Game;
import loginassignment.FileOperations.ReadFile;
import loginassignment.FileOperations.WriteFile;

public class Login {

    // Define two static field variables so I can alter them from different classes
    // Username variable is so I can later use it to pass in a Username for the Username Field
    public static String username = "";
    // So I can close the the stage of the login window from another class
    static Stage stage = new Stage();

    // GUI for the login window
    public static void showLogin() {

        // Windows Title
        stage.setTitle("Login");
        
        // Creating all the Text's and Text Fields for this GUI
        Text loginText = new Text("Login");
        loginText.setFont(Font.font("Arial", 36));
        
        Text usernameText = new Text("Enter Username:");
        TextField usernameField = new TextField(username);
        usernameText.setFont(Font.font("Arial", 20));

        Text passwordText = new Text("Enter Password:");        
        PasswordField passwordField = new PasswordField(); 
        passwordText.setFont(Font.font("Arial", 20));

        //Creating all the buttons and their actions for this GUI
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(150);
        // The set on action is down below after creating the layouts

        Button signUpButton = new Button("Sign Up");
        signUpButton.setMaxWidth(150);
        signUpButton.setOnAction(e -> {
            // Open the Sign Up GUI
            NewUser.showNewUser();
        });
        
        Button forgotUsername = new Button("Forgot Username?");
        forgotUsername.setOnAction(e -> {
            // Opens the Forgot Username GUI
            ForgotUsername.showForgotUsername();
        });
        // Removes the background of the button
        forgotUsername.setStyle("-fx-background-color: transparent;");
        
        Button forgotPassword = new Button("Forgot Password?");
        forgotPassword.setOnAction(e -> {
            // Opens the Forgot password GUI
            ForgotPassword.showForgotPassword();
        });
        // Removes the background of the button
        forgotPassword.setStyle("-fx-background-color: transparent;");

        // Creating the Red Asterics Labels to identify missing fields to user
        Label errorUsername = new Label("*");
        // Makes the text Red
        errorUsername.setStyle("-fx-text-fill: red;");
        Label errorPassword = new Label("*");
        errorPassword.setStyle("-fx-text-fill: red;");

        //Creating all the layouts for this GUI
        // VBox for the username text field and the forgot username button
        VBox vboxUsername = new VBox(0);
        vboxUsername.getChildren().add(forgotUsername);
        vboxUsername.getChildren().add(usernameField);
        vboxUsername.setAlignment(Pos.CENTER_RIGHT);

        // VBox for the password text field and the forgot pasword button
        VBox vboxPassword = new VBox(0);
        vboxPassword.getChildren().add(forgotPassword);
        vboxPassword.getChildren().add(passwordField);
        vboxPassword.setAlignment(Pos.CENTER_RIGHT);

        // Main Layout for the GUI
        GridPane gridPane = new GridPane();
        // Spacing
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        // Adding all nodes including the two VBoxs created prior
        gridPane.add(loginText, 0, 0, 2, 1);
        gridPane.add(usernameText, 0, 1, 1, 1);
        gridPane.add(vboxUsername, 1, 1, 1, 1); 
        gridPane.add(passwordText, 0, 2, 1, 1);
        gridPane.add(vboxPassword, 1, 2, 1, 1);
        gridPane.add(loginButton, 0, 4, 1, 1);
        gridPane.add(signUpButton, 1, 4, 1, 1);
        
        // Center everything in the node
        gridPane.setAlignment(Pos.CENTER);
        
        //Horizontal and vertical allignments for all the necesarry nodes
        GridPane.setHalignment(loginText, HPos.CENTER);
        
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(signUpButton, HPos.CENTER);
        
        GridPane.setValignment(usernameText, VPos.BOTTOM);
        GridPane.setValignment(passwordText, VPos.BOTTOM);

        GridPane.setValignment(errorUsername, VPos.BOTTOM);
        GridPane.setValignment(errorPassword, VPos.BOTTOM);

        // login button on action
        loginButton.setOnAction(e -> {
            // remove the red asterik's to make sure no error gets caused
            gridPane.getChildren().remove(errorUsername);
            gridPane.getChildren().remove(errorPassword);

            // Check if username and password field has anything in it
            if (usernameField.getLength() > 0 && passwordField.getLength() > 0) {
                // Checks if it is a registered username or a email
                if (ReadFile.isUsername(usernameField.getText()) || ReadFile.isEmail(usernameField.getText())) {
                    // Check if the password matches the email or username entered
                    if (ReadFile.passwordMatch(usernameField.getText(), passwordField.getText())) {
                        Game.username = usernameField.getText();
                        App app = new App();
                        try {
                            app.startBlackjackMenu(stage);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    } else {
                        AlertBox.display("Password Error", "Wrong Password", "Close");
                    }
                } else {
                    AlertBox.display("Username Error", "Username Not Found", "Close");
                }
            } else {
                // Check which fields are missing and put asteriks next to it
                if (!(usernameField.getLength() > 0)) {
                    gridPane.add(errorUsername, 2, 1, 1, 1);
                }
                if (!(passwordField.getLength() > 0)) {
                    gridPane.add(errorPassword, 2, 2, 1, 1);
                }
                AlertBox.display("Missing Information", "Fill out all the fields", "Close");
            }
        });
        
        //Create scene
        Scene scene = new Scene(gridPane, 600, 400);

        // Use the style.css document
        scene.getStylesheets().add(new File("cardgame-blackjack/src/main/java/com.blackjack.javaedition/style.css").toURI().toString());

        // Show the GUI
        stage.setScene(scene);
        stage.show();
    }
    
}
