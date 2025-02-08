package main.java.com.blackjack.Game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    // List to store the cards in the hand
    private List<Card> cards;

    // Counter for the number of soft (Ace) cards in the hand
    private int soft;

    // Current value of the hand
    private int handValue;

    // Initialize the cards list as an empty ArrayList
    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        // If card is ace increment the soft counter for Ace card then add the card value to hand value
        if (card.isAce()) {
            soft += 1;
            handValue += card.getCardValue(true);
        } else {
            handValue += card.getCardValue(false);
        }
        // If the hand value is over 21 and there are soft cards, reduce the value by 10 (convert Ace from 11 to 1)
        if (soft > 0) {
            if (handValue > 21) {
                handValue -= 10;
                // Decrement the soft counter
                soft -=1;
            }
        }
        // Add the card to the hand
        cards.add(card);
    }

    public void clearHand() {
        // Clear the cards list
        cards.clear();

        // Reset the hand value to 0
        handValue = 0;

        // Reset the soft counter to 0
        soft = 0;
    }

    // Return the current hand value
    public int getHandValue() {
        return handValue;
    }

    // Get the card at the specified index in the cards list
    public Card getCard(int i) {
        return cards.get(i);
    }

    // Return the number of cards in the hand
    public int getNumberOfCards() {
        return cards.size();
    }

    // Return the number of soft (Ace) cards in the hand
    public int getSoft() {
        return soft;
    }
}