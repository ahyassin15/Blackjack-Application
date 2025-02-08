package main.java.com.blackjack.Game;

// Enumeration representing the values of playing cards
public enum Rank {
    ACE(1, 11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    // The default value of the rank
    private int value; 
    // An alternative value of the rank (for ACE)
    private int altValue;

    // Constructor for ranks with a single value
    Rank(int value) {
        this.value = value;
    }

    // Constructor for ranks with both default value and alternative value
    Rank(int value, int altValue) {
        this.value = value;
        this.altValue = altValue;
    }

    // Method to get the value of the rank, optionally using the alternative value
    public int getValue(boolean useAltValue) {
        if (useAltValue) {
            return altValue;
        }
        return value;
    }
    
}
