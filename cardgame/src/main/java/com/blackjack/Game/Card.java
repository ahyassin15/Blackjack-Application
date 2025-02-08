package main.java.com.blackjack.Game;

import java.io.File;

import javafx.scene.image.Image;

public class Card {
    // The suit of the card
    private Suit suit;
    // The rank of the card
    private Rank rank;
    // The file path of the card image
    private String imagePath;

    public Card(Suit suit, Rank rank) {
        // Set the suit of the card
        this.suit = suit;
        // Set the rank of the card
        this.rank = rank;
        // Generate the file path of the card image based on the suit and rank
        imagePath = "cardgame/src/main/java/com/blackjack/Images/Cards/" + rank.toString().toLowerCase()
                + "_of_" + suit.toString().toLowerCase() + ".png";
    }

    // Get the value of the card based on the rank
    public int getCardValue(boolean useAltValue) {
        return rank.getValue(useAltValue);
    }

    // Check if the card is an Ace
    public boolean isAce() {
        return rank == Rank.ACE;
    }

    // Return the file path of the card image
    public String getImagePath() {
        return imagePath;
    }

    // Create an Image object from the card image file
    public Image getCardImage() {
        return new Image(new File(imagePath).toURI().toString());
    }

    // Getter and setter methods for suit and rank

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

}