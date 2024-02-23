import java.util.*;
public class Deck{
   public static final String[] NAMES = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
   public static final String[] SUITS = {"clubs", "spades", "hearts", "diamonds"};
   public static final int[]    RANKS = {2,3,4,5,6,7,8,9,10,11,12,13,14}; 
   private ArrayList<Card>      deck  = new ArrayList<Card>();
   
   public Deck(){
      this.initializeDeck();
   }
   
   public void initializeDeck(){
      deck = new ArrayList<Card>();
      for (int i = 0; i < 4; i++){ // loop through each suit
         for (int j = 0; j < 13; j++){ // loop through each number
            deck.add(new Card(NAMES[j], SUITS[i], RANKS[j])); // add card to deck
         }
      }
   }
   
   public ArrayList<Card> getDeck(){
      return deck;
   }
   
   public void setDeck(ArrayList<Card> deck){
      this.deck = deck;
   }
   
   public Card getCard(int idx){
      return deck.get(idx);
   }
   
   public Card dealTopCard(){
      return deck.remove(0);
   }
   
   public void shuffle2(){
      Random rand = new Random(); // rand is a random number thing
      ArrayList<Card> d = new ArrayList<Card>(); // make a temp deck
      int cs = rand.nextInt(8)+2; // how many cards to move
      for (int i = 0; i < cs; i ++){ // loop through to get the cards
         d.add(deck.remove(0)); // adds and removes cards at the same time
      }
      int off = rand.nextInt(deck.size())+1; // random place to put the cards
      deck.addAll(off, d); // put them there
   }

   public void shuffle(){
      Collections.shuffle(deck);
   }
   
   public String toString(){
      if (deck == null || deck.isEmpty()){ // check for null
         return "No cards in the deck!"; // return
      }
      String ret = ""; // return string
      for (Card c : deck){ // loop through each card in the deck 
         ret += c.toString(); // add
      }
      return ret; // final return 
   }
}