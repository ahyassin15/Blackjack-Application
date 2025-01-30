module blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.logging;
    
    opens cardgame to javafx.fxml;
    exports cardgame;

    opens blackjack.Game;
    exports blackjack.Game;
}
