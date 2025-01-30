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

public class ForgotUsername {

    public static void showForgotUsername() {
        // Create a new stage for this GUI
        Stage stage = new Stage();

        // Creating all the Text's and Text Fields for this GUI
        Text forgotUsernameText = new Text("Forgot Username?");
        forgotUsernameText.setFont(Font.font("Arial", 36));

        Text forgotUsernameExplainText = new Text("Enter the email you signed up with");
        forgotUsernameExplainText.setFont(Font.font("Arial", 20));
        
        Text emailText = new Text("Enter Email:");
        emailText.setFont(Font.font("Arial", 20));
        TextField emailField = new TextField();

        //Creating all the buttons and their actions for this GUI
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // check if the email is registered
            if (ReadFile.isEmail(emailField.getText())) {
                // get the username linked with the email with the ReadFile class and getusername method
                String username = ReadFile.getUsername(emailField.getText());
                // check if the username returned is valid
                if (!(username.equals(" "))) {
                    // send a email to the email that they entered containing their username
                    SendEmail.sendMail(emailField.getText(), "Username Recovery", "The username: " + username);
                    AlertBox.display("Email Sent", "An email containing your username was sent", "Close");
                    stage.close();
                }
            } else {
                AlertBox.display("Email Error", "Not a registered email", "Close");
            }
        });

        //Creating all the layouts for this GUI
        // Create a hbox that has the email text, email text field and submit button side by side
        // Creating a hbox so I can have a big gap between the Forgot Username Text and the rest of the nodes
        HBox hbox = new HBox(emailText, emailField, submitButton);
        // align the nodes
        hbox.setAlignment(Pos.CENTER);
        // spacing
        hbox.setSpacing(10);

        // Create a VBox with the forgot username text and the smaller font text below it
        VBox vbox = new VBox(forgotUsernameText, forgotUsernameExplainText);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // Main layout. using this to have a big gap between all the nodes by using VGap
        GridPane gridpane = new GridPane();
        gridpane.setVgap(50);
        gridpane.add(vbox, 0, 0, 1, 1);
        gridpane.add(hbox, 0, 1, 1, 1);
        gridpane.setAlignment(Pos.CENTER);

        // create new scene
        Scene scene = new Scene(gridpane, 500, 300);

        // display the scene
        stage.setScene(scene);
        stage.show();
    }
    
}
