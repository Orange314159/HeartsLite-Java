import java.util.*;
public class CardGame {
    private Deck deckOfCards;
    private String nameOfGame;
    private ArrayList<CardPlayer> players;
    private int numberOfPlayers;
    private int currentPlayer;

    public CardGame(){
        this.deckOfCards = new Deck();
        this.nameOfGame = "";
        this.players = new ArrayList<CardPlayer>();
        this.numberOfPlayers = 0;
        this.currentPlayer = 0;
    }

    public CardGame(String nameOfGame, int numberOfPlayers, String[] playerNames, int currentPlayer){
        this.nameOfGame = nameOfGame;
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayer = currentPlayer;
        this.players = new ArrayList<CardPlayer>();
        this.deckOfCards = new Deck();
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i % 2 == 1){
                this.players.add(new CardPlayerLevel2(playerNames[i], 0, new ArrayList<Card>()));
            }
            else {
                this.players.add(new CardPlayerLevel1(playerNames[i], 0, new ArrayList<Card>()));
            }
        }

    }

    public void deal(int cardsToDeal, int playerIndex){
        for (int i = 0; i < cardsToDeal; i++) {
            players.get(playerIndex).addCard(deckOfCards.dealTopCard());
        }
    }

    private void setCurrentPlayerToStartingPlayer(){
        Card twoClub = new Card("2", "clubs", 2);
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getHand().contains(twoClub)){
                currentPlayer = i;
                break;
            }
        }
    }

    private int takesRound(int startPlayer, ArrayList<Card> roundCards){
        String startSuit = roundCards.get(startPlayer).getSuit();
        int highCard = roundCards.get(startPlayer).getRank();
        int ret = startPlayer;
        for (int i = 0; i < roundCards.size(); i++) {
            if (roundCards.get(i).getSuit().equals(startSuit) && highCard <= roundCards.get(i).getRank()){
                // previous statement essentially checks if the card is the right suit and is a higher value than the
                // highest already found
                highCard = roundCards.get(i).getRank();
                ret = (i + startPlayer) % numberOfPlayers;
            }
        }
        return ret;
    }

    public ArrayList<Card> playRound(ArrayList<Card> gc){
        int playerNum;
        Card queenOfSpades = new Card("Q", "spades", 12);
        ArrayList<Card> gameCards = new ArrayList<Card>(gc);
        ArrayList<Card> roundCards = new ArrayList<Card>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerNum = (currentPlayer + i) % numberOfPlayers;
            Card addCard = players.get(playerNum).chooseCard(roundCards, gameCards);
            roundCards.add(addCard);
            gameCards.add(addCard);
        }
        checkRound(roundCards, currentPlayer);
        int winningPlayer = takesRound(currentPlayer, roundCards);
        int addedPoints = 0;

        for (Card roundCard : roundCards) {
            if (roundCard.getSuit().equals("hearts")) {
                addedPoints += 1; // add one point for a heart
            } else if (roundCard.equals(queenOfSpades)){
                addedPoints += 13;
            }
        }
        players.get(winningPlayer).setScore(players.get(winningPlayer).getScore() + addedPoints);
        players.get(winningPlayer).getTakenCards().addAll(roundCards);
        currentPlayer = winningPlayer;

        return gameCards;
    }
    public void playGame(){
        // first step, set current player to player that has two of clubs
        this.setCurrentPlayerToStartingPlayer();
        // next step, each player must play their car
        int playerNum = 0; // init playerNum
        ArrayList<Card> gameCards = new ArrayList<Card>(); // this var lasts for the entire game
        // 13 rounds in one game
        for (int i = 0; i < 13; i++) {
            gameCards = this.playRound(gameCards);
        }
        // clear taken cards
        for (CardPlayer player : players){
            player.setTakenCards(new ArrayList<Card>());
        }
    }


    // Teacher supplied method
    static int errorsInCheckRound;
    public void checkRound(ArrayList<Card> round, int startingPlayer) {
        if (errorsInCheckRound < 5) {
            String roundSuit = round.get(0).getSuit();  // Suit that was led
            for (int i = 1; i < round.size(); i++) {     // for all other cards played in the round
                if (!round.get(i).getSuit().equals(roundSuit)) {
                    CardPlayer player = players.get((i + startingPlayer) % round.size());
                    for (Card c : player.getHand()) {   // check each card in that players hand
                        if (c.getSuit().equals(roundSuit)) {
                            System.out.println("*** DID NOT FOLLOW SUIT ***\n  round=" + round + "\n  played=" + round.get(i) + "\n  hand=" + player.getHand());
                            errorsInCheckRound++;
                            break;
                        }
                    }
                }
            }
        }
    }
    // End of teacher supplied method

    // Getter and setter methods //////////////////////////////////////////////////////////////////////////
    public Deck getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(Deck deckOfCards) {
        this.deckOfCards = deckOfCards;
    }
    public String getNameOfGame() {
        return nameOfGame;
    }

    public void setNameOfGame(String nameOfGame) {
        this.nameOfGame = nameOfGame;
    }

    public ArrayList<CardPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<CardPlayer> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    // End getter and setter methods //////////////////////////////////////////////////////////////////////

    @Override
    public String toString(){
        String ret = "";
        ret = ret + "***Hearts***\n";
        for(CardPlayer cp : players){
            ret = ret + cp.toString() + "\n";
        }
        ret = ret + "deck -> " + deckOfCards.toString();
        return ret;
    }
}
