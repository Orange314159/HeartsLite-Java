import java.util.*;
public class CardPlayerRobson2024 extends CardPlayer{
    public CardPlayerRobson2024(){
        super();
    }
    public CardPlayerRobson2024(String name, int score, ArrayList<Card> hand){
        super(name, score, hand);
    }

    public int specificCardPlay(Card card){
        Collections.sort(super.getHand());
        for (int i = 0; i < super.getHand().size(); i++) {
            if (card.equals(super.getHand().get(i))){
                return i;
            }
        }
        return -1;
    }

    public int playedSuit(String suit, ArrayList<Card> gameCards){
        // this method returns how many cards of a specific suit (suit) have been played in the entire game
        int ret = 0;
        for (Card gameCard : gameCards) {
            if (gameCard.getSuit().equals(suit)) {
                ret++;
            }
        }
        return ret;
    }


    @Override
    public Card chooseCard(ArrayList<Card> round, ArrayList<Card> game){
        Random rand = new Random();
        Card twoOfClubs    = new Card("2", "clubs",  2);
        Card queenOfSpades = new Card("Q", "spades", 12);
        Card kingOfSpades  = new Card("K", "spades", 13);
        Card aceOfSpades   = new Card("A", "spades", 14);

        Collections.sort(super.getHand()); // sort my hand
        // first check if we have 2 of clubs
        if (specificCardPlay(twoOfClubs) >= 0){
            return this.playCard(specificCardPlay(twoOfClubs));
        }

        // next check for if I am first player to play this round
        if (round.isEmpty()){
            // we want to get rid of the queen of spades if the only spades left are the king or ace
            if (playedSuit("spades", game) > 9 && !(game.contains(kingOfSpades) || game.contains(aceOfSpades)) && specificCardPlay(queenOfSpades) >= 0){
                return this.playCard(specificCardPlay(queenOfSpades));
            }
            // we want to play low cards but also want to be careful of 

            // final choice is to play a random card
            return this.playCard(rand.nextInt(super.getHand().size()));
        } // end empty round

        if (playedSuit(round.get(0).getSuit(), super.getHand()) == 0){
            // we can't follow suit
            
            // we will play the queen of spades if we have it because it is worth 13 points, and we don't want it
            if (specificCardPlay(queenOfSpades) >= 0){
                return this.playCard(specificCardPlay(queenOfSpades));
            }
            
            // if the queen of spades has not yet been played it is very good to get rid of the king / ace of spades because they could result in us getting the queen
            if (!game.contains(queenOfSpades)){
                // play Ace of Spades
                if (specificCardPlay(aceOfSpades) >= 0){
                    return this.playCard(specificCardPlay(aceOfSpades));
                }
                // play King of Spades
                if (specificCardPlay(kingOfSpades) >= 0){
                    return this.playCard(specificCardPlay(kingOfSpades));
                }
            }
            
            // the next best choice is to play our highest heart
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getSuit().equals("hearts") && !super.getHand().get(((i+1) % (super.getHand().size()))).getSuit().equals("hearts")){
                    return this.playCard(i);
                }
            }
            
            // the next and final choice is to play our highest card or a card that would make it such that we have none of a specific suit
            // we want to not have any of a suit because if we don't have any cards from that suit we are able to play whatever we want if someone 
            // else leads with it, additionally we would want to play the highest card that we have because we don't want to
            // lead this late in the game (we know it is late in the game because we are unable to follow suit and don't have any
            // hearts either)
            // FOR NOW I WILL ONLY IMPLEMENT PLAYING OUR HIGHEST CARD 
            int highestCard = 0;
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getRank() > super.getHand().get(highestCard).getRank()){
                    highestCard = i;
                }
            }
            return this.playCard(highestCard);
            
        }      // we can't follow suit
        else if (playedSuit(round.get(0).getSuit(), super.getHand()) == 1){
            // we have only one option of what to play
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getSuit().equals(round.get(0).getSuit())){
                    return this.playCard(i);
                }
            }
        } // we have one option to follow suit
        else{
            // we have multiple options of what to play, but we must still follow suit

            // find our highest and lowest card that follows suit
            int highestFollowSuit = -1;
            int lowestFollowSuit = 100;
            ArrayList<Integer> followCards = new ArrayList<>();
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getSuit().equals(round.get(0).getSuit())){
                    followCards.add(super.getHand().get(i).getRank());
                    if (super.getHand().get(i).getRank() > highestFollowSuit){
                        highestFollowSuit = super.getHand().get(i).getRank();
                    }
                    if (super.getHand().get(i).getRank() < lowestFollowSuit){
                        lowestFollowSuit = super.getHand().get(i).getRank();
                    }
                }
            }

            // find the highest card that has been played that follow suit (we can play a card one less than that)
            int highestCardPossible = -1;
            for (Card card : round) {
                if (card.getSuit().equals(round.get(0).getSuit())) {
                    if (highestCardPossible < card.getRank()){
                        highestCardPossible = card.getRank();
                    }
                }
            }

            // if our lowest card is above highest card possible we play our highest card of that suit
            if (highestCardPossible < lowestFollowSuit){
                String[] rankToName = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
                Card card = new Card(rankToName[highestFollowSuit] , round.get(0).getSuit(), highestFollowSuit);
                return this.playCard(specificCardPlay(card));
            }
            
            // if we have low enough cards we will play our highest card that is still less than the lowest played already
            for (int i = 0; i < followCards.size(); i++) {
                if (followCards.get(i) < highestCardPossible && i == followCards.size()-1 || followCards.get(i) < highestCardPossible && followCards.get(i+1) > highestCardPossible){
                    String[] rankToName = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
                    Card card = new Card(rankToName[followCards.get(i)] , round.get(0).getSuit(), followCards.get(i));
                    return this.playCard(specificCardPlay(card));
                }
            }
            
            // this should be it all but if not ill play the last card and print something out to show that there is an error
            System.out.println("ERROR; Follow Card Broken");
            return this.playCard(super.getHand().size()-1); 
        }                                                               // we have many options but still follow suit
        
        
        // the code should never get here but if it does it will print out an error and play the last card in our hand
        System.out.println("ERROR; did not play anything");
        return this.playCard(super.getHand().size()-1);
    }
}
