import java.util.*;

public class MuellerCardPlayer extends CardPlayer {
    public MuellerCardPlayer(String name, int score, ArrayList<Card> hand) {
        super(name, score, hand);
    }

    public ArrayList<Card> cardsOfSuit(ArrayList<Card> setOfCards, String suit) {
        ArrayList<Card> cardsOfSuit = new ArrayList<>();

        for (Card card : setOfCards) {
            if (card.getSuit().equals(suit)) {
                cardsOfSuit.add(card);
            }
        }

        return cardsOfSuit;
    }

    public Card maxCard(ArrayList<Card> setOfCards) {
        Card maxCard = setOfCards.get(0);

        for (Card card : setOfCards) {
            if (card.compareTo(maxCard) > 0) {
                maxCard = card;
            }
        }

        return maxCard;
    }

    public Card minCard(ArrayList<Card> setOfCards) {
        Card minCard = setOfCards.get(0);

        for (Card card : setOfCards) {
            if (card.compareTo(minCard) < 0) {
                minCard = card;
            }
        }

        return minCard;
    }

    public Card maxLimitCard(ArrayList<Card> setOfCards, Card limitCard) {
        Card maxLimitCard = setOfCards.get(0);

        for (Card card : setOfCards) {
            if (card.compareTo(limitCard) < 0 && card.compareTo(maxLimitCard) > 0) {
                maxLimitCard = card;
            }
        }

        if (maxLimitCard.compareTo(limitCard) > 0) {
            maxLimitCard = this.minCard(setOfCards);
        }

        return maxLimitCard;
    }

    @Override
    public Card chooseCard(ArrayList<Card> cardsPlayedThisRound, ArrayList<Card> cardsPlayedPreviousRounds) {
        int randCard;
        String[] suits = new String[]{"diamonds", "clubs", "spades", "hearts"};
        Card card = new Card();
        Card twoOfClubs = new Card("2", "clubs", 2);
        Card queenOfSpades = new Card("Q", "spades", 12);
        Card kingOfSpades = new Card("K", "spades", 13);
        Card aceOfSpades = new Card("A", "spades", 14);

        ArrayList<Card> myHand = this.getHand();
        Collections.sort(myHand);
        if (cardsPlayedPreviousRounds.isEmpty() && myHand.contains(twoOfClubs)) {
            card = twoOfClubs;
        } else if (cardsPlayedThisRound.isEmpty()) {
            int var15 = suits.length;

            for (String suit : suits) {
                ArrayList<Card> cardsToChooseFrom = this.cardsOfSuit(myHand, suit);
                if (cardsPlayedPreviousRounds.size() < 8 && suit.equals("spades")) {
                    cardsToChooseFrom.remove(queenOfSpades);
                    cardsToChooseFrom.remove(kingOfSpades);
                    cardsToChooseFrom.remove(aceOfSpades);
                }

                if (!cardsToChooseFrom.isEmpty()) {
                    if (cardsPlayedPreviousRounds.size() < 8) {
                        card = this.maxCard(cardsToChooseFrom);
                    } else {
                        card = this.minCard(myHand);
                    }
                    break;
                }
            }
        } else {
            Card cardLed = cardsPlayedThisRound.get(0);
            String cardLedSuite = cardLed.getSuit();

            ArrayList<Card> cardsInMyHandThatMatchSuitThatWasLed = this.cardsOfSuit(myHand, cardLedSuite);
            ArrayList<Card> heartsInMyHand = this.cardsOfSuit(myHand, "hearts");
            if (!cardsInMyHandThatMatchSuitThatWasLed.isEmpty()) {
                if (cardLedSuite.equals("spades") && myHand.contains(queenOfSpades) && (cardsPlayedThisRound.contains(kingOfSpades) || cardsPlayedThisRound.contains(aceOfSpades))) {
                    card = queenOfSpades;
                } else if (cardLedSuite.equals("spades") && myHand.contains(kingOfSpades) && cardsPlayedThisRound.contains(aceOfSpades)) {
                    card = kingOfSpades;
                } else if (cardsPlayedPreviousRounds.size() < 8) {
                    if (cardsInMyHandThatMatchSuitThatWasLed.size() > 1) {
                        cardsInMyHandThatMatchSuitThatWasLed.remove(queenOfSpades);
                    }

                    if (cardsInMyHandThatMatchSuitThatWasLed.size() > 1) {
                        cardsInMyHandThatMatchSuitThatWasLed.remove(aceOfSpades);
                    }

                    if (cardsInMyHandThatMatchSuitThatWasLed.size() > 1) {
                        cardsInMyHandThatMatchSuitThatWasLed.remove(kingOfSpades);
                    }

                    card = this.maxCard(cardsInMyHandThatMatchSuitThatWasLed);
                } else {
                    card = this.maxLimitCard(cardsInMyHandThatMatchSuitThatWasLed, cardLed);
                }
            } else if (myHand.contains(queenOfSpades)) {
                card = queenOfSpades;
            } else if (myHand.contains(aceOfSpades)) {
                card = aceOfSpades;
            } else if (myHand.contains(kingOfSpades)) {
                card = kingOfSpades;
            } else if (!heartsInMyHand.isEmpty()) {
                card = this.maxCard(heartsInMyHand);
            } else {
                card = this.maxCard(myHand);
            }
        }

        card = this.playCard(myHand.indexOf(card));
        return card;
    }
}