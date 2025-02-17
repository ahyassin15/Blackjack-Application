package main.java.com.blackjack.Game;

// Class to make the Leaderboard Enteries more organized
public class LeaderboardEntry {

    private int rank;
    private String username;
    private int balance;

    // Constructor to initialize the leaderboard entry
    public LeaderboardEntry(int rank, String username, int balance) {
        this.rank = rank;
        this.username = username;
        this.balance = balance;
    }

    // Method to get the rank of the entry
    public int getRank() {
        return rank;
    }

    // Method to get the username of the player
    public String getUsername() {
        return username;
    }

    // Method to get the balance of the player
    public int getBalance() {
        return balance;
    }
}
