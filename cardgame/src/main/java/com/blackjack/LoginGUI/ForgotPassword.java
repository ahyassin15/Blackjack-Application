package com.blackjack.javaedition.LoginGUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginassignment.FileOperations.ReadFile;

public class ForgotPassword {

    public static void showForgotPassword() {
        // Create a new stage for this GUI
        Stage stage = new Stage();

        // Creating all the Text's and Text Fields for this GUI
        Text forgotPasswordText = new Text("Forgot Password?");
        forgotPasswordText.setFont(Font.font("Arial", 36));

        Text forgotPasswordExplainText = new Text("Enter the email you signed up with");
        forgotPasswordExplainText.setFont(Font.font("Arial", 20));
        
        Text emailText = new Text("Enter Email or Username:");
        emailText.setFont(Font.font("Arial", 20));
        TextField emailField = new TextField();

        //Creating all the buttons and their actions for this GUI
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Check if the email or password entered is registered
            if (ReadFile.isEmail(emailField.getText()) || ReadFile.isUsername(emailField.getText())) {
                // get the passwod using the ReadFile class and get password method
                String password = ReadFile.getPassword(emailField.getText());
                // Make sure the returned password is valid
                if (!(password.equals(" "))) {
                    // check if they entered a mail or username
                    if (ReadFile.isEmail(emailField.getText())) {
                        // if they entered a email use text field to send to email address
                        SendEmail.sendMail(emailField.getText(), "Password Recovery", "The Password: " + password);
                    } else {
                        // if they entered a username use the username to get the mail address with the ReadFile class and getEmail method
                        SendEmail.sendMail(ReadFile.getEmail(emailField.getText()), "Password Recovery", "The Password: " + password);
                    }
                    AlertBox.display("Email Sent", "An email containing your password was sent", "Close");
                    stage.close();
                }
            } else {
                AlertBox.display("Error", "Not a registered email or username", "Close");
            }
        });

        //Creating all the layouts for this GUI
        // Create a hbox that has the email text, email text field and submit button side by side
        // Creating a hbox so I can have a big gap between the Forgot Password Text and the rest of the nodes
        HBox hbox = new HBox(emailText, emailField, submitButton);
        // Align all nodes to center
        hbox.setAlignment(Pos.CENTER);
        // Spacing
        hbox.setSpacing(10);

        // Create a VBox with the forgot password text and the smaller font text below it
        VBox vbox = new VBox(forgotPasswordText, forgotPasswordExplainText);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // Main layout. using this to have a big gap between all the nodes by using VGap
        GridPane gridpane = new GridPane();
        gridpane.setVgap(50);
        gridpane.add(vbox, 0, 0, 1, 1);
        gridpane.add(hbox, 0, 1, 1, 1);
        gridpane.setAlignment(Pos.CENTER);

        // Create scene
        Scene scene = new Scene(gridpane, 500, 300);

        // show scene
        stage.setScene(scene);
        stage.show();
    }
}
