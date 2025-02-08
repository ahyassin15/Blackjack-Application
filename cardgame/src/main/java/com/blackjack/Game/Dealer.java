package main.java.com.blackjack.Game;

import javafx.scene.image.ImageView;

public class Dealer {
    // The dealer's hand
    private Hand hand;

    // Counter to keep track of the next card position
    private int counter = 0;
    
    public Dealer() {
        // Create a new hand object for the dealer
        hand = new Hand();
    }

    // Return the value of the dealer's hand
    public int getHandValue() {
        return hand.getHandValue();
    }

     public void recieveCard(Card card, ImageView image) {
        // Add the received card to the dealer's hand
        hand.addCard(card);

        // Set the image of the card in the passed ImageView
        image.setImage(hand.getCard(counter).getCardImage());

        // Make the image visible
        image.setVisible(true);

        // Increment the counter to keep track of the next card position
        counter++;
    }

     // Return the soft value of the dealer's hand
    public int getSoft() {
        return hand.getSoft();
    }

    // Return the dealer's hand
    public Hand getHand() {
        return hand;
    }

    // Reset the dealer's hand by creating a new hand object
    public void reset() {
        hand = new Hand();
        // Reset the counter to 0
        counter = 0;
    }
    
}
