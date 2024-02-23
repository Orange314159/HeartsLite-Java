import java.util.*;
class CardPlayer extends Player{
   private ArrayList<Card> hand;
   private ArrayList<Card> takenCards;
   
   public CardPlayer(){
      super();
      hand = new ArrayList<Card>();
      takenCards = new ArrayList<Card>();
   }

   public CardPlayer(String name, int score, ArrayList<Card> hand){
      super(name,score);
      this.hand = hand;
      takenCards = new ArrayList<Card>();
   }
   public void setHand(ArrayList<Card> hand){
      this.hand = hand;
   }
   
   public void setTakenCards(ArrayList<Card> takenCards){
      this.takenCards = takenCards;
   } 
   
   public ArrayList<Card> getHand(){
      return hand;
   }
   
   public ArrayList<Card> getTakenCards(){
      return takenCards;
   }
   
   public void addCard(Card card){
      if (hand == null){
         hand = new ArrayList<Card>();
      }

      hand.add(card);
   }
   public Card playCard(int idx){
      return hand.remove(idx);
   }

   public int specificCardPlay(Card card){
      Collections.sort(hand);
      for (int i = 0; i < hand.size(); i++) {
         if (card.equals(hand.get(i))){
            return i;
         }
      }
      return -1;
   }
   public Card chooseCard(ArrayList<Card> round, ArrayList<Card> game){
      Card twoOfClubs = new Card("2", "clubs", 2);
      Random rand = new Random();
      // first check if we have 2 of clubs and play it
      if (specificCardPlay(twoOfClubs) >= 0){
         return this.playCard(specificCardPlay(twoOfClubs));
      }

      // next check for if I am first player, if so I play a random card
      if (round.isEmpty()){
         return this.playCard(rand.nextInt(hand.size()));
      }

      Collections.sort(hand);
      int startIdx = -1;
      int endIdx = -1;
      String startingSuit = round.get(0).getSuit();
      for (int i = 0; i < hand.size(); i++) {
         String currentSuit = hand.get(i).getSuit();
         if ((currentSuit.equals(startingSuit)) && (i == 0 || !hand.get(i-1).getSuit().equals(startingSuit))){
            startIdx = i;
         }
         if ((currentSuit.equals(startingSuit))){
            endIdx = i;
         }
      }
      if (startIdx == endIdx && endIdx == -1){ // cant follow suit
         for (int i = 0; i < hand.size(); i++) { // look for hearts
            if ((hand.get(i).getSuit().equals("hearts") && i == 0) || (hand.get(i).getSuit().equals("hearts") && !hand.get(i-1).getSuit().equals("hearts"))){
               startIdx = i;
            }
            if ((hand.get(i).getSuit().equals("hearts") && i == hand.size()-1) || (hand.get(i).getSuit().equals("hearts") && !hand.get(i+1).getSuit().equals("hearts"))){
               endIdx = i;
            }
         }
         if (startIdx == endIdx && endIdx == -1) {
            return this.playCard(rand.nextInt(hand.size())); // play random card
         }
         int suitRand;
         if (endIdx == startIdx){
            suitRand = endIdx;
         }
         else {
            suitRand = rand.nextInt(endIdx - startIdx + 1) + startIdx;
         }
         return this.playCard(suitRand); // play random heart
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
   public int indexOf(Card c){
      for (int i = 0; i < hand.size(); i++){
         if (c.equals(hand.get(i))){
            return i;
         }
      }
      return -1;
   }
   
   public String toString(){
      Collections.sort(hand);
      return super.getName() + " (" + super.getScore() + ") " + hand;
   }
}