import java.util.*;
public class CardPlayerLevel3 extends CardPlayer{
    public CardPlayerLevel3(){
        super();
    }
    public CardPlayerLevel3(String name, int score, ArrayList<Card> hand){
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
    @Override
    public Card chooseCard(ArrayList<Card> round, ArrayList<Card> game){
        Random rand = new Random();
        Card twoOfClubs    = new Card("2", "clubs",  2);
        Card twoOfSpades   = new Card("2", "spades", 2);
        Card threeOfSpades = new Card("3", "spades", 3);
        Card fourOfSpades  = new Card("4", "spades", 4);
        Card fiveOfSpades  = new Card("5", "spades", 5);
        Card sixOfSpades   = new Card("6", "spades", 6);
        Card sevenOfSpades = new Card("7", "spades", 7);
        Card eightOfSpades = new Card("8", "spades", 8);
        Card nineOfSpades  = new Card("9", "spades", 9);
        Card tenOfSpades   = new Card("T", "spades", 10);
        Card jackOfSpades  = new Card("J", "spades", 11);
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
            if ((game.contains(twoOfSpades) && game.contains(threeOfSpades) && game.contains(fourOfSpades) && game.contains(fiveOfSpades) && game.contains(sixOfSpades) && game.contains(sevenOfSpades) && game.contains(eightOfSpades) && game.contains(nineOfSpades) && game.contains(tenOfSpades) && game.contains(jackOfSpades)) && !(game.contains(kingOfSpades) || game.contains(aceOfSpades)) && specificCardPlay(queenOfSpades) >= 0){
                return this.playCard(specificCardPlay(queenOfSpades));
            }
            // because we have our hand sorted we can just go through it until we find a heart. the first heart we find will be the lowest and is the card we want to play
            int lowestCard = 100;
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getSuit().equals("hearts")){
                    return this.playCard(i);
                }
                if (super.getHand().get(i).getRank() < lowestCard){
                    lowestCard = super.getHand().get(i).getRank(); // we use this to find the lowest card in our hand if we have to play a non heart
                }
            }

            // next best choice is to play our lowest card
            for (int i = 0; i < super.getHand().size(); i++) {
                if (super.getHand().get(i).getRank() <= lowestCard){
                    return this.playCard(i);
                }
            }

            // final choice is to play a random card
            return this.playCard(rand.nextInt(super.getHand().size()));
        } // end empty round

        // first check if we have the suit that was started
        for (int i = 0; i < super.getHand().size(); i++) {
            if ((super.getHand().get(i).getSuit().equals(round.get(0).getSuit()) && !super.getHand().get(((i+1) % (super.getHand().size()))).getSuit().equals(round.get(0).getSuit())) || super.getHand().get(i).getSuit().equals(round.get(0).getSuit()) && super.getHand().get(i).getRank() < round.get(0).getRank()){
                return this.playCard(i);
            }
        }
        // play QOS
        if (specificCardPlay(queenOfSpades) >= 0){
            return this.playCard(specificCardPlay(queenOfSpades));
        }
        // play AOS
        if (specificCardPlay(aceOfSpades) >= 0){
            return this.playCard(specificCardPlay(aceOfSpades));
        }
        // play KOS
        if (specificCardPlay(kingOfSpades) >= 0){
            return this.playCard(specificCardPlay(kingOfSpades));
        }
        // play high heart
        for (int i = 0; i < super.getHand().size(); i++) {
            if (super.getHand().get(i).getSuit().equals("hearts") && !super.getHand().get(((i+1) % (super.getHand().size()))).getSuit().equals("hearts")){
                return this.playCard(i);
            }
        }
        // final option is to play the highest card you have
        return this.playCard(super.getHand().size()-1);
    }
}
