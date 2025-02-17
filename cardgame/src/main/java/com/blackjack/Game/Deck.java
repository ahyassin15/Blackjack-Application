package main.java.com.blackjack.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    // List to store the cards in the deck
    private List<Card> cards;

    // Number of decks in the deck object
    private int deckAmount;

    // Default constructor with a single deck
    public Deck() {
        this(1);
        this.deckAmount = 1;
    }
    
    public Deck(int deckAmount) {
        // Set the number of decks in the deck object
        this.deckAmount = deckAmount;

        // Initialize the cards list as an empty ArrayList
        cards = new ArrayList<>();

        // Initialize the deck by adding cards for each suit and rank
        for (int i = 0; i < deckAmount; i++) {
            initializeDeck();
        }
    }

    private void initializeDeck() {
        // Iterate over each suit and rank to create a card for each combination
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                // Create a new Card object and add it to the cards list
                cards.add(new Card(suit, rank));
            }
        }
    }

    // Shuffle the cards using the Collections.shuffle() method
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        // If the cards list is empty, create a new deck with the same number of decks, shuffle it, and recursively call drawCard()
        if (cards.isEmpty()) {
            Deck deck = new Deck(deckAmount);
            deck.shuffle();
            return deck.drawCard();
        }
        // Remove and return the top card from the cards list
        return cards.remove(0);
    }

    // Return the cards list
    public List<Card> getDeck() {
        return cards;
    }

}
