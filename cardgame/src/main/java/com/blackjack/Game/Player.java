package main.java.com.blackjack.Game;

import blackjack.FileOperations.ReadFile;
import blackjack.FileOperations.WriteFile;

// Extends dealer class since they share methods and vairables
public class Player extends Dealer{
    private String username;

    public Player(String username) {
        this.username = username;
    }

    // Method to deduct the bet amount from the player's balance
    public void placeBet(int amount) {
        WriteFile.setBalance(username, ReadFile.getBalance(username) - amount);
    }

    // Method to get the player's balance
    public int getBalance() {
        return ReadFile.getBalance(username);
    }

    // Method to add winnings to the player's balance
    public void addWinnings(int amount) {
        WriteFile.setBalance(username, ReadFile.getBalance(username) + amount);
    }
    
}
