public class GameTest {
    public static void main(String[] args){
        String[] playerName = {"P0", "P1", "P2", "P3"};
        CardGame cardGame = new CardGame("Game0", 4, playerName, 0);
        System.out.println(cardGame);

        for (int i = 0; i < 50000; i++) {
            cardGame.getDeckOfCards().initializeDeck();
            cardGame.getDeckOfCards().shuffle();

            for (int j = 0; j < 52; j++) {
                cardGame.deal(1,(j%4));
            }
//            System.out.println(cardGame);
            cardGame.playGame();
            if (i < 10) {
                System.out.println(cardGame);
            }

        }
        System.out.println(cardGame);
    }
}
