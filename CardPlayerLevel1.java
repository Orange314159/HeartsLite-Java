import java.util.*;
class CardPlayerLevel1 extends CardPlayer{

    public CardPlayerLevel1(){
        super();
    }
    public CardPlayerLevel1(String name, int score, ArrayList<Card> hand){
        super(name, score, hand);
    }
    @Override
    public Card chooseCard(ArrayList<Card> round, ArrayList<Card> game){
        Card twoOfClubs = new Card("2", "clubs", 2);
        Card queenOfSpades = new Card("Q", "spades", 12);
        Random rand = new Random();
        // first check if we have 2 of clubs and play it
        if (specificCardPlay(twoOfClubs) >= 0){
            return this.playCard(specificCardPlay(twoOfClubs));
        }

        // next check for if I am first player, if so I play a random card
        if (round.isEmpty()){
            return this.playCard(rand.nextInt(super.getHand().size()));
        }

        Collections.sort(super.getHand());
        int startIdx = -1;
        int endIdx = -1;
        String startingSuit = round.get(0).getSuit();
        for (int i = 0; i < super.getHand().size(); i++) {
            String currentSuit = super.getHand().get(i).getSuit();
            if ((currentSuit.equals(startingSuit)) && (i == 0 || !super.getHand().get(i-1).getSuit().equals(startingSuit))){
                startIdx = i;
            }
            if ((currentSuit.equals(startingSuit))){
                endIdx = i;
            }
        }
        if (startIdx == endIdx && endIdx == -1){ // cant follow suit
            // play QOS
            if (specificCardPlay(queenOfSpades) >= 0){
                return this.playCard(specificCardPlay(queenOfSpades));
            }
            for (int i = super.getHand().size()-1; i > -1; i--) { // look for hearts
                if (super.getHand().get(i).getSuit().equals("hearts")){
                    return super.playCard(i); // play the highest heart
                }
            }
            return this.playCard(rand.nextInt(super.getHand().size())); // play random card
        }
        int suitRand;
        if (endIdx == startIdx){
            suitRand = startIdx;
        }
        else {
            suitRand = rand.nextInt(endIdx - startIdx + 1) + startIdx;
            return this.playCard(suitRand);
        }
        return this.playCard(suitRand); // play random card that follow suit
    }

}