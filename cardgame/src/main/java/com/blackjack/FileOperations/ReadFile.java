package main.java.com.blackjack.FileOperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadFile {

    // checks if the username is already taken
    public static boolean isUsername(String username) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                //loops through all the lines in the file
                while ((line = bufferedReader.readLine()) != null) {
                    // split the line and check if index 1 (username) is equal to the username pased
                    if (line.split(", ")[1].equalsIgnoreCase(username)) {
                        return true;
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return false;
    }

    //checks if the email is taken
    public static boolean isEmail(String email) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[0].equalsIgnoreCase(email) ) {
                        return true;
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return false;
    }

    // checks if the password passed matches the password for the username that was also passed by stopping at the line of the passed usernma and comparing the password on that line to the passed password
    public static boolean passwordMatch(String username, String password) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[1].equalsIgnoreCase(username) || line.split(", ")[0].equalsIgnoreCase(username)) {
                        if (line.split(", ")[2].equals(password)) {
                            return true;
                        }
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return false;
    }

    // gets the username when the email is given
    public static String getUsername(String email) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[0].equalsIgnoreCase(email)) {
                        return line.split(", ")[1];
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return " ";
    }

    //gets the email when the username is given
    public static String getEmail(String username) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[1].equalsIgnoreCase(username)) {
                        return line.split(", ")[0];
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return " ";
    }

    // gets the password when the username or email is given
    public static String getPassword(String username) {
        try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[0].equalsIgnoreCase(username) || line.split(", ")[1].equalsIgnoreCase(username)) {
                        return line.split(", ")[2];
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return " ";
    }

    public static int getBalance(String username) {
         try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.split(", ")[0].equalsIgnoreCase(username) || line.split(", ")[1].equalsIgnoreCase(username)) {
                        return Integer.parseInt(line.split(", ")[3]);
                    }
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return -1;
    }

    // hasmap for the scores, has a username and integer (balance) pair 
    public static HashMap<String, Integer> playerScores = new HashMap<>();

    // method that returns a hashmap for bubblesort to use
    public static HashMap<String, Integer> getPlayerScores() {
         try {
            FileReader reader = new FileReader("database.txt");
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;

                // for every line we put the username and the balance into the hasmap together
                while ((line = bufferedReader.readLine()) != null) {
                    playerScores.put(line.split(", ")[1], Integer.parseInt(line.split(", ")[3]));
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            reader.close();

        } catch (IOException e) {
            
        }
        return playerScores;
    }
}
