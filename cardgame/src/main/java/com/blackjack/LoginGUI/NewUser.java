package com.blackjack.javaedition.LoginGUI;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginassignment.FileOperations.ReadFile;
import loginassignment.FileOperations.WriteFile;

public class NewUser {

    public static void showNewUser() {
        // create a new stage and set the title
        Stage stage = new Stage();
        stage.setTitle("Sign Up");

        // Creating all the Text's and Text Fields for this GUI
        Text signUpText = new Text("Sign Up");
        signUpText.setFont(Font.font("Arial", 36));

        Text emailText = new Text("Enter E-Mail:");
        TextField emailField = new TextField();
        emailText.setFont(Font.font("Arial", 20));

        Text usernameText = new Text("Enter Username:");
        TextField usernameField = new TextField();
        usernameText.setFont(Font.font("Arial", 20));

        Text passwordText = new Text("Enter Password:");
        PasswordField passwordField = new PasswordField();
        passwordText.setFont(Font.font("Arial", 20));

        Text confirmPasswordText = new Text("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordText.setFont(Font.font("Arial", 20));

        //Creating all the buttons and their actions for this GUI
        Button signUpButton = new Button("Sign Up");
        signUpButton.setAlignment(Pos.CENTER);
        signUpButton.setMaxWidth(150);
        // The action is below the gridpane layout so the asteriks can be added

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.CENTER);
        closeButton.setMaxWidth(150);
        closeButton.setOnAction(e -> {
            // closes the window
            stage.close();
        });

        // Creating the Red Asterics Labels to identify missing fields to user
        Label errorEmail = new Label("*");
        // Makes the text Red
        errorEmail.setStyle("-fx-text-fill: red;");
        Label errorUsername = new Label("*");
        errorUsername.setStyle("-fx-text-fill: red;");
        Label errorPassword = new Label("*");
        errorPassword.setStyle("-fx-text-fill: red;");
        Label errorConfirmPassword = new Label("*");
        errorConfirmPassword.setStyle("-fx-text-fill: red;");

        // Main layout for GUI
        GridPane gridPane = new GridPane();
        // spacing
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        //adding all the nodes
        gridPane.add(signUpText, 0, 0, 2, 1);
        gridPane.add(emailText, 0, 1, 1, 1);
        gridPane.add(emailField, 1, 1, 1, 1);
        gridPane.add(usernameText, 0, 2, 1, 1);
        gridPane.add(usernameField, 1, 2, 1, 1);
        gridPane.add(passwordText, 0, 3, 1, 1);
        gridPane.add(passwordField, 1, 3, 1, 1);
        gridPane.add(confirmPasswordText, 0, 4, 1, 1);
        gridPane.add(confirmPasswordField, 1, 4, 1, 1);
        gridPane.add(signUpButton, 0, 5, 1, 1);
        gridPane.add(closeButton, 1, 5, 1, 1);

        // setting the alingments for the necesarry nodes
        gridPane.setAlignment(Pos.CENTER);

        GridPane.setHalignment(signUpText, HPos.CENTER);

        // login for the sign up button
        signUpButton.setOnAction(e -> {

            //remove all the red asteriks is so they dont get written over
            gridPane.getChildren().remove(errorEmail);
            gridPane.getChildren().remove(errorUsername);
            gridPane.getChildren().remove(errorPassword);
            gridPane.getChildren().remove(errorConfirmPassword);

            // checks if all the fields have information in them
            if (emailField.getLength() > 0 && usernameField.getLength() > 0 && passwordField.getLength() > 0
                    && confirmPasswordField.getLength() > 0) {
                // boolean values that calls on all the isValid methods to see if the information entered is valid. Gets used later
                boolean isEmailValid = isEmailValid(emailField.getText());
                boolean isUsernameValid = isUsernameValid(usernameField.getText());
                boolean isPasswordValid = isPasswordValid(passwordField.getText());
                boolean isConfirmPasswordValid = false;

                // if anyting is not valid it adds a red asteriks beside the text field
                // checks if email is not valid with the isEmailValid method
                if (!(isEmailValid)) {
                    gridPane.add(errorEmail, 2, 1, 1, 1);
                }
                // checks if username is not valid with the isUsernameValid method
                if (!(isUsernameValid)) {
                    gridPane.add(errorUsername, 2, 2, 1, 1);
                }
                // checks if password is not valid with the isPasswordValid method
                if (!(isPasswordValid)) {
                    gridPane.add(errorPassword, 2, 3, 1, 1);
                }
                // checks if password is valid with the isPasswordValid method
                if (isPasswordValid) {
                    // then sets the boolean valid of isConfirmPasswordValid to the output of isConfirmPasswordValid
                    isConfirmPasswordValid = isConfirmPasswordValid(passwordField.getText(),
                            confirmPasswordField.getText());
                    // checks if confirmPassword is not valid with the isConfirmPasswordValid method
                    if (!(isConfirmPasswordValid)) {
                        gridPane.add(errorConfirmPassword, 2, 4, 1, 1);
                    }
                }

                // checks if all four fields and entered values are valid
                if (isEmailValid && isUsernameValid && isPasswordValid && isConfirmPasswordValid) {
                    // it passes all the entered information into an array named info
                    String[] info = { emailField.getText(), usernameField.getText(), passwordField.getText() };
                    // writes all the information into the database.txt file using the WriteFile class and write method
                    WriteFile.write(info);
                    stage.close();

                    // closes the login window
                    Login.stage.close();
                    // passes the username that was just used to sign up with into the field variable
                    Login.username = usernameField.getText();
                    // reopens the window so the username that was passed appears in it
                    Login.showLogin();
                }
            } else {
                // if all the fields were not filled it checks which ones were not filled and adds a red asteriks beside it and tells the user to fill missing info
                if (!(emailField.getLength() > 0)) {
                    gridPane.add(errorEmail, 2, 1, 1, 1);
                }
                if (!(usernameField.getLength() > 0)) {
                    gridPane.add(errorUsername, 2, 2, 1, 1);
                }
                if (!(passwordField.getLength() > 0)) {
                    gridPane.add(errorPassword, 2, 3, 1, 1);
                }
                if (!(confirmPasswordField.getLength() > 0)) {
                    gridPane.add(errorConfirmPassword, 2, 4, 1, 1);
                }
                AlertBox.display("Missing Information", "Fill out all the fields", "Close");
            }
        });

        // creates a new scene
        Scene scene = new Scene(gridPane, 500, 300);

        // use style.css to style the GUI
        scene.getStylesheets().add(NewUser.class.getResource("style.css").toExternalForm());

        // show the scene
        stage.setScene(scene);
        stage.show();
    }

    // checks if the passed in email string is valid
    public static boolean isEmailValid(String email) {
        // chekcs if its a email address by chceking if the string has an @ in it
        if (!(email.contains("@"))) {
            AlertBox.display("Invalid Email", "Invalid email", "Close");
            return false;
        }
        // checks if the email is already in use by using the isEmail method in the ReadFile class
        if (ReadFile.isEmail(email)) {
            AlertBox.display("Invalid Email", "Email already in use", "Close");
            return false;
        }
        // checks if the email contains a space or a comma
        if (email.contains(" ") || email.contains(",")) {
            AlertBox.display("Invalid Email", "Email cannot contain a space or comma", "Close");
            return false;
        }
        return true;
    }

    // chceks if the username string that was passed is valid
    public static boolean isUsernameValid(String username) {
        // checks if the username is less than 6 charachters
        if (username.length() < 6) {
            AlertBox.display("Username Error", "Username is too short", "Close");
            return false;
        }
        // checks if the username is more than 10 charachters
        if (username.length() > 10) {
            AlertBox.display("Username Error", "Username is too long", "Close");
            return false;
        }
        // checks if the username is taken by using the isUsername method in the ReadFile class
        if (ReadFile.isUsername(username)) {
            AlertBox.display("Username Taken", "Username taken", "Close");
            return false;
        }
        // checks if the username contains a space or a comma
        if (username.contains(" ") || username.contains(",")) {
            AlertBox.display("Username Error", "Username cannot contain a space or comma", "Close");
            return false;
        }
        return true;
    }

    // checks if the password that was pased is valid
    public static boolean isPasswordValid(String password) {
        // boolean varibales to indentify if there is a number, uppercase and special charachter in the password. Used later
        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasSpecial = false;

        // checks if the password is less than 6 charachters
        if (password.length() < 6) {
            AlertBox.display("Password Error", "Password is too short", "Close");
            return false;
        }
        // checks if the password is more than 10 charachters
        if (password.length() > 10) {
            AlertBox.display("Password Error", "Password is too long", "Close");
            return false;
        }
        // checks if the password contains a space or a comma
        if (password.contains(" ") || password.contains(",")) {
            AlertBox.display("Password Error", "Password cannot contain a space or comma", "Close");
            return false;
        }
        // loops through all the charachters in the password
        for (int i = 0; i < password.length(); i++) {
            // gets the charachter at the index it is at
            Character c = password.charAt(i);
            // chceks if the charachter is a number
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            // checks if the charachter is uppercase
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            // checks if the charachter is a special charachter
            if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isWhitespace(c)) {
                hasSpecial = true;
            }
        }
        // checks which of the conditions for the password was not met and tells the user what was not met
        if (!hasDigit) {
            AlertBox.display("Password Error", "Password needs at least 1 number", "Close");
            return false;
        }
        if (!hasUppercase) {
            AlertBox.display("Password Error", "Password needs at least 1 uppercase letter", "Close");
            return false;
        }
        if (!hasSpecial) {
            AlertBox.display("Password Error", "Password needs at least 1 special character", "Close");
            return false;
        }
        return true;
    }

    // checks if the confirm password is valid (compares the password and the confrim password fields)
    public static boolean isConfirmPasswordValid(String password, String confirmPassword) {
        // if the password and the confirm password are not equal tells the user
        if (!(confirmPassword.equals(password))) {
            AlertBox.display("Confirm Password Error", "Passwords do not match", "Close");
            return false;
        }
        return true;
    }

}
