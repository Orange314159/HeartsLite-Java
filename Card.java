public class Card implements Comparable<Object>{
   private String name;
   private String suit;
   private int rank;

   public Card(){
      name = "default";
      suit = "default";
      rank = 0;
   }

   public Card(String n, String s, int r){
      name = n;
      suit = s;
      rank = r;
   }

   public String getName(){
      return name;
   }

   public String getSuit(){
      return suit;
   }

   public int getRank(){
      return rank;
   }

   public void setName(String n){
      name = n;
   }

   public void setSuit(String s){
      suit = s;
   }

   public void setRank(int r){
      rank = r;
   }

   public boolean equals(Object obj){
      if (obj == null){
         return false;
      }
      Card c = (Card) obj;
      return name.equals(c.getName())&&suit.equals(c.getSuit())&&rank == c.getRank();
   }

   public int compareTo(Object obj){
      if (this.equals(obj)){
         return 0;
      }
      Card c = (Card) obj;
      if (suit.equals(c.getSuit())){
         if (rank > c.getRank()){
            return 1;
         }
      }
      else if (suit.equals("clubs") || (suit.equals("spades") && !c.getSuit().equals("clubs")) || (suit.equals("hearts") && c.getSuit().equals("diamonds"))){
         return 1;
      }
      return -1;
   }

   public String toString(){
      return suit.charAt(0) + name + "(" + rank + ")";
   }
}