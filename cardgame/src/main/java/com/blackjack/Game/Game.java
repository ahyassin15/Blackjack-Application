package main.java.com.blackjack.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    Scene gameScene;
    Stage stage;

    public static String username;

    private Deck deck;
    private Player player;
    private Dealer dealer;
    private int betAmount;
    private int betAmountPrev;

    @FXML
    private AnchorPane main;

    @FXML
    private Label playerBalance, playerBet, winnings;

    @FXML
    private Label dealerHand, playerHand, dealerStatus, playerStatus;

    @FXML
    private ImageView one, five, ten, twentyFive, oneHundred, fiveHundred, oneThousand;

    @FXML
    private ImageView deal, undo, hit, stand, doubleBet, rebetDeal, rebet2xDeal, rebet;

    @FXML
    private ImageView playerCardOne, playerCardTwo, playerCardThree, playerCardFour, playerCardFive, playerCardSix, playerCardSeven, playerCardEight, playerCardNine, playerCardTen;

    @FXML
    private ImageView dealerCardOne, dealerCardTwo, dealerCardThree, dealerCardFour, dealerCardFive, dealerCardSix, dealerCardSeven, dealerCardEight, dealerCardNine, dealerCardTen;
    
    @FXML
    private VBox firstDecision, secondDecision, thirdDecision;

    @FXML
    private HBox betMenu;
    
    // Used to keep track of the images
    private List<ImageView> playerCardImages = new ArrayList<ImageView>();
    private List<ImageView> dealerCardImages = new ArrayList<ImageView>();

    // vairbales to check game state (who busted or blackjack)
    private boolean isPlayerBusted;
    private boolean isDealerBusted;
    private boolean isBlackjack;
    
    // position of the image
    int playerImageNum = -1;
    int dealerImageNum = -1;

    // Constructor
    // Create a Deck player and dealer
    public Game() {
        deck = new Deck(1);
        player = new Player(username);
        dealer = new Dealer();
    }

    public void initialize() {
        // Initliaze the screen
        // Initlize player and dealer card locations
        initializePlayerCards();
        initializeDealerCards();

        // set all image buttons to invisible
        dealerHand.setVisible(false);
        playerHand.setVisible(false);

        dealerStatus.setVisible(false);
        playerStatus.setVisible(false);
        winnings.setVisible(false);

        deal.setVisible(false);
        undo.setVisible(false);

        hit.setVisible(false);
        stand.setVisible(false);
        doubleBet.setVisible(false);

        rebetDeal.setVisible(false);
        rebet2xDeal.setVisible(false);
        rebet.setVisible(false);

        // initliaze the labels for the bet and balance
        playerBet.setText("Bet: ");
        playerBalance.setText("Balance: " + player.getBalance());
    }

    public void startGame(MouseEvent event) {
        // dont let people bet
        betMenu.setMouseTransparent(true);

        // resets the game by setting everything to null, false, originial amounts and calling reset methods
        playerStatus.setText(null);
        dealerStatus.setText(null);
        winnings.setText(null);

        isPlayerBusted = false;
        isDealerBusted = false;

        player.getHand().clearHand();
        dealer.getHand().clearHand();

        player.reset();
        dealer.reset();

        playerImageNum = -1;
        dealerImageNum = -1;

        // reinitilize cards
        initializePlayerCards();
        initializeDealerCards();

        // place the betted amount (removed from balance in txt)
        player.placeBet(betAmount);
        // updates the amount betted
        playerBet.setText("Bet: " + betAmount);
        //updates balance
        playerBalance.setText("Balance: " + (player.getBalance()));
        // shuffle
        deck.shuffle();
        //deal cards
        dealInitialCard();
        // sets the second card of the dealer to backside so player cant see
        dealerCardTwo.setImage(new Image(new File("loginassignment/src/main/java/loginassignment/Images/Cards/backside.png").toURI().toString()));
        
        // checks if player got blackjack from start
        if (player.getHandValue() == 21) {
            isBlackjack = true;
            dealerPlay();
        }
        
        // shows players card value
        showPlayerValue();
        // checks if the card is ace and updates the dealers hand label accordingly
        if (dealer.getHand().getCard(0).isAce()) {
            dealerHand.setText(((dealer.getHandValue() - dealer.getHand().getCard(1).getCardValue(false)) - 10) + "/" + (dealer.getHandValue() - dealer.getHand().getCard(1).getCardValue(false)));
        } else {
            if (dealer.getHand().getCard(1).isAce()) {
                dealerHand.setText("" + (dealer.getHandValue() - dealer.getHand().getCard(1).getCardValue(true)));
            } else {
                dealerHand.setText("" + (dealer.getHandValue() - dealer.getHand().getCard(1).getCardValue(false)));
            }
        }
        // shows dealers hand value
        dealerHand.setVisible(true);
        
        // shows the hit, stand and double buttons
        hitStandDoubleVisible();
    }

    // places bet to the amount put in
    public void placeBet(int amount) {
        // shows the deal and undo butotns
        dealUndoVisible();

        // sets the previous bet amount to the passed amount
        betAmountPrev = amount;
        // adds the amount to the bet amount that will be betted
        betAmount += amount;
        //updates bet amount
        playerBet.setText("Bet: " + betAmount);
        //updates balance
        playerBalance.setText("Balance: " + (player.getBalance() - betAmount));
    }

    // undos the most recent bet
    public void undoBet(MouseEvent event) {
        // removes the most revent bet from the amount
        if (!(betAmount - betAmountPrev < 0)) {
            betAmount -= betAmountPrev;
        }
        //pdates bet and balance accordinly
        playerBet.setText("Bet: " + betAmount);
        playerBalance.setText("Balance: " + (player.getBalance() - betAmount));
    }

    // deals the cards using the recieve card function
    // draws the most recent card
    // uses the getNextPlayerImage to get where to put the image of card
    public void dealInitialCard() {
        player.recieveCard(deck.drawCard(), getNextPlayerImage());
        dealer.recieveCard(deck.drawCard(), getNextDealerImage());
        player.recieveCard(deck.drawCard(), getNextPlayerImage());
        dealer.recieveCard(deck.drawCard(), getNextDealerImage());
    }

    // player hit
    public void playerHit(MouseEvent event) {
        // draws a card
        Card card = deck.drawCard();
        // recieves the draw card
        player.recieveCard(card, getNextPlayerImage());
        // shows hand value
        showPlayerValue();

        // checks if player hit the max of 21 if so calls the dealer play method
        if (player.getHandValue() + card.getCardValue(true) == 21) {
            Service<Void> dealerPlayService = new Service<>() {
            @Override
                protected Task<Void> createTask() {
                    return new Task<>() {
                        @Override
                        protected Void call() {
                            dealerPlay();
                            return null;
                        }
                    };
                }
            };
            dealerPlayService.start();
        }

        // if the player hand value exceeds 21 after hit sets isPlayerBusted to true to indicate player busted
        // calls dealer play method for dealers turn
        if (player.getHandValue() + card.getCardValue(true) > 21) {
            if (player.getHandValue() + card.getCardValue(false) > 21) {
                isPlayerBusted = true;
                Service<Void> dealerPlayService = new Service<>() {
                @Override
                    protected Task<Void> createTask() {
                        return new Task<>() {
                            @Override
                            protected Void call() {
                                dealerPlay();
                                return null;
                            }
                        };
                    }
                };
                dealerPlayService.start();
            }
        }
    }

    // player stand
    //gives turn to dealer
    public void playerStand(MouseEvent event) {
        Service<Void> dealerPlayService = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    dealerPlay();
                    return null;
                }
            };
        }
    };

    dealerPlayService.start();
    }

    // doubles the bet
    public void playerDouble(MouseEvent event) {
        //adds the bet amount to the bet amount to double it
        betAmount += betAmount;
        //updates accordingly
        playerBalance.setText("Balance: " + (player.getBalance() - betAmount));
        playerBet.setText("Bet: " + betAmount);
        // removes bet from player balance
        player.placeBet(betAmount);
    }

    // dealers turn to play
    public void dealerPlay() {
        // turns of the hit,stand and double buttons
        hit.setVisible(false);
        stand.setVisible(false);
        doubleBet.setVisible(false);
        // 1 secod cooldown
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //sets the second dealer card image to the card
        Platform.runLater(() -> dealerCardTwo.setImage(dealer.getHand().getCard(1).getCardImage()));

        // shows dealers hand value
        Platform.runLater(this::showDealerValue);
        // while dealer is below 17 keeps recieving cards
        while (dealer.getHandValue() < 17) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // recives card
            dealer.recieveCard(deck.drawCard(), getNextDealerImage());
            // shows value
            Platform.runLater(this::showDealerValue);
        }

        // if hand is above 21 dealer busts
        if (dealer.getHandValue() > 21) {
            isDealerBusted = true;
        }

        // checks for win
        Platform.runLater(this::checkWin);
        
        // shows the rebet and replay buttons
        rebetVisible();
    }

    // lets to replay with the same bet amount
    public void rebetDealClick(MouseEvent event) {
        startGame(event);
    }

    // doubles your bet and lets you replay
    public void rebet2xDealClick(MouseEvent event) {
        betAmount += betAmount;
        startGame(event);
    }

    //lets you go back to the initial betting screen
    public void rebetClick(MouseEvent event) {
        // lets you click on the chips again
        betMenu.setMouseTransparent(false);
        // turns off variables so they are not on screen
        dealUndoVisible();
        initializePlayerCards();
        initializeDealerCards();
        playerHand.setText(null);
        dealerHand.setText(null);
        playerStatus.setText(null);
        dealerStatus.setText(null);
        winnings.setText(null);
    }

    // only leaves deal and undo bet visible and clickable
    public void dealUndoVisible() {
        deal.setVisible(true);
        undo.setVisible(true);

        hit.setVisible(false);
        stand.setVisible(false);
        doubleBet.setVisible(false);
        
        rebetDeal.setVisible(false);
        rebet2xDeal.setVisible(false);
        rebet.setVisible(false);

        firstDecision.setMouseTransparent(false);
        secondDecision.setMouseTransparent(true);
        thirdDecision.setMouseTransparent(true);
    }

    // only leaves hit, stand and double visible and clickable
    public void hitStandDoubleVisible() {
        deal.setVisible(false);
        undo.setVisible(false);

        hit.setVisible(true);
        stand.setVisible(true);
        doubleBet.setVisible(true);
        
        rebetDeal.setVisible(false);
        rebet2xDeal.setVisible(false);
        rebet.setVisible(false);

        firstDecision.setMouseTransparent(true);
        secondDecision.setMouseTransparent(false);
        thirdDecision.setMouseTransparent(true);
    }

    // only leaves the rebetting options visible and clickable
    public void rebetVisible() {
        deal.setVisible(false);
        undo.setVisible(false);

        hit.setVisible(false);
        stand.setVisible(false);
        doubleBet.setVisible(false);

        rebetDeal.setVisible(true);
        rebet2xDeal.setVisible(true);
        rebet.setVisible(true);

        firstDecision.setMouseTransparent(true);
        secondDecision.setMouseTransparent(true);
        thirdDecision.setMouseTransparent(false);
    }

    // checks if player got blackjack at the start
    public void checkWin() {
        if (isBlackjack) {
            // Updates the text above the players hand value to indicate
            playerStatus.setText("BJ");
            dealerStatus.setText("Lose");
            // Tells you how much you won by changing text on label
            winnings.setText("YOU WON: " + betAmount * 3);
            // sets the label visible
            winnings.setVisible(true);
            //adds winnigs to balance
            player.addWinnings(betAmount * 3);
            //restarts game
            startGame(null);
        } else {
            // EXACT SAME STUFF JUST WITH DIFFERENT VALUES AND AMOUNT SAME LOGIC NO NEED TO COMMENT
            if (isPlayerBusted) {
                // Updates the text above the players hand value to indicate
                dealerStatus.setText("Win");
                playerStatus.setText("Bust");
            } else {
                if (isDealerBusted) {
                    // Updates the text above the players hand value to indicate
                    playerStatus.setText("Win");
                    dealerStatus.setText("Bust");
                    winnings.setText("YOU WON: " + betAmount * 2);
                    winnings.setVisible(true);
                    player.addWinnings(betAmount * 2);
                } else {
                    if (dealer.getHandValue() == player.getHandValue()) {
                        // Updates the text above the players hand value to indicate
                        playerStatus.setText("Push");
                        dealerStatus.setText("Push");
                        winnings.setText("YOU WON: " + betAmount);
                        winnings.setVisible(true);
                        player.addWinnings(betAmount);
                    } else if (dealer.getHandValue() > player.getHandValue()) {
                        // Updates the text above the players hand value to indicate
                        dealerStatus.setText("Win");
                        playerStatus.setText("Lose");
                    } else if (dealer.getHandValue() < player.getHandValue()) {
                        // Updates the text above the players hand value to indicate
                        playerStatus.setText("Win");
                        dealerStatus.setText("Lose");
                        winnings.setText("YOU WON: " + betAmount * 2);
                        winnings.setVisible(true);
                        player.addWinnings(betAmount * 2);
                    }
                }
            }
        }
        // sets the labels visible 
        playerStatus.setVisible(true);
        dealerStatus.setVisible(true);
    }

    // shows player hand value by setting it to label
    public void showPlayerValue() {
        // checks if there is ace in hand
        if (player.getSoft() > 0) {
            // updates the label accordingly by subtracting 10 from 1 side. So both numbers are represented
            playerHand.setText((player.getHandValue() - 10) + "/" + player.getHandValue());
        } else {
            // if there isnt ace it just displays the hand value
            playerHand.setText("" + player.getHandValue());
        }
        playerHand.setVisible(true);
    }

    // SAME LOGIC AS showPlayerValue just for dealer
    public void showDealerValue() {
        if (dealer.getHand().getCard(0).isAce()) {
            dealerHand.setText(dealer.getHandValue()+ "/" + (dealer.getHandValue() - 10));
        } else {
            dealerHand.setText("" + dealer.getHandValue());
        }
        dealerHand.setVisible(true);
    }

    // returns the next image to draw to for player by iterating through the images array
    public ImageView getNextPlayerImage() {
        playerImageNum++;
        if (playerImageNum == playerCardImages.size()) {
            System.out.println("Cant draw more cards");
            return null;
        }
        return playerCardImages.get(playerImageNum);
    }

    // returns the next image to draw to for the dealer by iterating through the images array
    public ImageView getNextDealerImage() {
        dealerImageNum++;
        if (dealerImageNum == dealerCardImages.size()) {
            System.out.println("Cant draw more cards");
            return null;
        }
        return dealerCardImages.get(dealerImageNum);
    }

    // adds all the card images to the arraylist for the player
    public void initializePlayerCards() {
        playerCardImages.add(playerCardOne);
        playerCardImages.add(playerCardTwo);
        playerCardImages.add(playerCardThree);
        playerCardImages.add(playerCardFour);
        playerCardImages.add(playerCardFive);
        playerCardImages.add(playerCardSix);
        playerCardImages.add(playerCardSeven);
        playerCardImages.add(playerCardEight);
        playerCardImages.add(playerCardNine);
        playerCardImages.add(playerCardTen);

        //sets all the images to invisible
        for (ImageView image : playerCardImages) {
            image.setVisible(false);
        }
    }

    // adds all the card images to the arraylist for the dealer
    public void initializeDealerCards() {
        dealerCardImages.add(dealerCardOne);
        dealerCardImages.add(dealerCardTwo);
        dealerCardImages.add(dealerCardThree);
        dealerCardImages.add(dealerCardFour);
        dealerCardImages.add(dealerCardFive);
        dealerCardImages.add(dealerCardSix);
        dealerCardImages.add(dealerCardSeven);
        dealerCardImages.add(dealerCardEight);
        dealerCardImages.add(dealerCardNine);
        dealerCardImages.add(dealerCardTen);

        //sets all the images to invisible
        for (ImageView image : dealerCardImages) {
            image.setVisible(false);
        }
    }

    public void getScene(Stage stage) {
        this.stage = stage;
		gameScene = stage.getScene();
	}

    // ALL THE METHODS FOR THE DIFFERENT CHIPS AND PLACING BET
    // SAME LOGIC FOR ALL JUST DIFFERENT NUMBERS

    public void add1(MouseEvent event) {
        if (player.getBalance() - 1 > 0) {
            placeBet(1);
        }
    }

    public void add5(MouseEvent event) {
        if (player.getBalance() - 5 > 0) {
            placeBet(5);
        }
    }

    public void add10(MouseEvent event) {
        if (player.getBalance() - 10 > 0) {
            placeBet(10);
        }
    }

    public void add25(MouseEvent event) {
        if (player.getBalance() - 25 > 0) {
            placeBet(25);
        }
    }

    public void add100(MouseEvent event) {
        if (player.getBalance() - 100 > 0) {
            placeBet(100);
        }
    }

    public void add500(MouseEvent event) {
        if (player.getBalance() - 500 > 0) {
            placeBet(500);
        }
    }

    public void add1000(MouseEvent event) {
        if (player.getBalance() - 1000 > 0) {
            placeBet(1000);
        }
    }
    
}
