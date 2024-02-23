import java.util.*;
public class PlayerTest{
   public static void main(String[] args){
       System.out.println("Card Player - Matt Robson");

       Card c1 = new Card("K", "spades",   13);
       Card c2 = new Card("J", "spades",   11);
       Card c3 = new Card("Q", "diamonds", 12);
       Card c8 = new Card("Q", "spades",   12);
       Card c4 = new Card("Q", "hearts",   12);
       Card c5 = new Card("2", "clubs",    2);
       Card c6 = new Card("5", "spades",   5);
       Card c7 = new Card("A", "clubs",    14);
       Card c9 = new Card("A", "hearts",   14);

       Card c19 = new Card("5", "diamonds",   5);
       Card c92 = new Card("6", "spades",   6);

       Card c96 = new Card("9", "diamonds",   9);
       Card c79 = new Card("7", "clubs",   7);
       Card c89 = new Card("T", "clubs",   10);
       Card c99 = new Card("J", "clubs",   11);
       Card c98 = new Card("4", "hearts",   4);

       CardPlayer p2 = new CardPlayerLevel1();
       p2.setName("MATT2");
//       p2.addCard(c96);
       p2.addCard(c79);
       p2.addCard(c89);
       p2.addCard(c99);
       p2.addCard(c98);
//       p2.addCard(c8);
       p2.addCard(c9);
       CardPlayer p1 = new CardPlayer();
       p1.setName("Matt");
       p1.addCard(c1);
       p1.addCard(c2);
       p1.addCard(c3);
       p1.addCard(c4);
       p1.addCard(c8);
//       p1.addCard(c5);
       p1.addCard(c9);
       ArrayList<Card> h = new ArrayList<Card>();
       h.add(c19);
       h.add(c92);
       System.out.println(p2);
       Card c0 = p2.chooseCard(h,h);
       System.out.println(h);
       System.out.println(p2);
       System.out.println(c0);

   }
}