package main.java.com.blackjack.FileOperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class WriteFile {

    // writes the passed array to the database.txt file
    public static void write(String[] information) {
        try {
            FileWriter writer = new FileWriter("database.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // joins the array that was passed in to the method with a , and space
            String info = String.join(", ", information);

            // write the joined string into the database.txt file
            bufferedWriter.write(info + ", " + 100);
            // create a new line
            bufferedWriter.newLine();
            
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sets the balance of the passed username to the passed balance
    // goes through the file find the username that matches the passed username
    // changes the balance for that username
    // makes it into a new line
    // rewrites the entire file into a new temporary file to have the old values
    // changes the temp file name to the database.txt so no confusion
    public static void setBalance(String username, int newBalance) {
        try {
            File tempFile = new File("temp.txt");
            File databaseFile = new File ("database.txt");

            FileWriter writer = new FileWriter(tempFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            FileReader reader = new FileReader(databaseFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(", ")[0].equalsIgnoreCase(username) || line.split(", ")[1].equalsIgnoreCase(username)) {
                    line = line.replace(line.split(", ")[3], Integer.toString(newBalance));
                }
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            
            reader.close();
            bufferedReader.close();
            bufferedWriter.close();
            
            Path inputPath = databaseFile.toPath();
            Path tempPath = tempFile.toPath();
            Files.move(tempPath, inputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
