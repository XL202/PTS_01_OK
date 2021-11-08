package du;

import java.util.LinkedList;

public class Turn {
    private TurnStatus ts;
    int turnNumber;
    final private Hand hand;
    private final Deck deck;
    DiscardPile discardPile;
    Play play;
    LinkedList<BuyDeck> bd;
    public Turn(TurnStatus ts, LinkedList<BuyDeck> bd, boolean shuffling) {
        this.bd = bd;
        play = new Play();
        if (shuffling) discardPile = new DiscardPileWithShuffling(new LinkedList<>());
        else discardPile = new DiscardPileWithoutShuffling(new LinkedList<>());
        if (shuffling) deck = new DeckWithShuffling(null, discardPile);
        else deck = new DeckWithoutShuffling(null, discardPile);
        hand = new Hand(deck);
        this.ts = ts;
        turnNumber = 1;
    }
    public void evaluate_card(CardInterface card) {
        int drawCount = card.evaluate(ts);
        hand.drawCards(deck.draw(drawCount));
    }

    public int getTurnNumber() {
        return turnNumber;
    }
    public void resetTurnStatus() {
        ts = new TurnStatus();
    }
    public int getActions() {
        return ts.getActions();
    }
    public int getBuys() {
        return ts.getBuys();
    }
    public boolean playCard(int handIdx) {

        if (ts.getActions() > 0) {
            if (hand.getSize() > handIdx && handIdx > -1) {
                if (hand.isActionCard(handIdx)) {
                    evaluate_card(hand.play(handIdx));
                    play.addCardToPlay(hand.removeCard(handIdx));
                    ts.setActions(ts.getActions() - 1);
                    return true;
                }
                else {
                    System.err.println("Zvolená karta nie je ActionCard!");
                    return false;
                }
            }
            else {
                System.err.println("Zvolená karta nie je v ruke!");
                return false;
            }


        }
        else System.err.println("Nedostatok akcii!");
        return false;
    }

    public void throwCardsToDiscardPile() {
        discardPile.addCards(hand.throwCards());
        discardPile.addCards(play.throwAll());
    }
    public void drawCardsFromDeck(int a) {
        hand.drawCards(deck.draw(a));
    }

    public boolean is_Action_phase_possible() {
        playTreasureCards();
        for(int i=0; i<hand.getHand().size(); i++) {
            if (hand.getHand().get(i).cardType().isAction()) return true;
        }
        return false;
    }
    public boolean hasMoreActions() {
        return ts.getActions() > 0;
    }
    public boolean buyCards(int idBuyDeck) {

        if (idBuyDeck > 7 || idBuyDeck < 0) {
            System.err.println("Takýto buy deck neexistuje.");
            return false;
        }
        if (ts.getBuys() < 1) {
            System.err.print("Nie je dostatočný počet Buys pre nákup karty.\n");
            return false;
        }
        if (bd.get(idBuyDeck).getCostOfCard() > ts.getCoins()) {
            System.err.printf("Nie je dostatočný počet mincí pre nákup karty %s, pretože je potrebných %d a máte len %d. \n", bd.get(idBuyDeck).getCardName(), bd.get(idBuyDeck).getCostOfCard(), ts.getCoins());
            return false;
        }
        if (bd.get(idBuyDeck).cardCount() > 0) {
            LinkedList<CardInterface> a = new LinkedList<>();
            a.add(bd.get(idBuyDeck).buy());
            discardPile.addCards(a);
            ts.setCoins(ts.getCoins() - bd.get(idBuyDeck).getCostOfCard());
            System.out.printf("Karta %s bola úspešne kúpená za %d coins. Zostatok coins: %d.\n", bd.get(idBuyDeck).getCardName(), bd.get(idBuyDeck).getCostOfCard(), ts.getCoins());
            ts.setBuys(ts.getBuys() -1);

            return true;
        }
        else {
            System.err.println("Nie je možné kúpiť kartu, lebo už nie je v BuyDecku");
        }
        return false;
    }
    public void printFinalStatus() {
        int points = 0;
        int cards = 0;
        for(int i=0; i<discardPile.getSize(); i++) {
            points += discardPile.getCard(i).getPoints();
            cards++;
        }
        for(int i=0; i<deck.getDeckSize(); i++) {
            points += deck.getCard(i).getPoints();
            cards++;
        }
        System.out.println("======================\n***** Game over *****");
        System.out.printf("Počet kariet: %d\n", cards);
        System.out.printf("Počet bodov v Decku a DiscardPile: %d\n", points);
        printBuyDeck();
        printDeck();
        printDiscardPile();
    }
    public void printBuyDeck() {
        StringBuilder sb = new StringBuilder();
        for (BuyDeck buyDeck : bd) {
            sb.append(buyDeck.getCardName()).append(": ");
            sb.append(buyDeck.cardCount()).append(", ");
        }
        System.out.printf("BuyDeck: [%s].\n", sb);
    }

    public void printBuyDeckDescription() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bd.size(); i++) {
            sb.append("[").append(i).append("]: ");
            sb.append(bd.get(i).getCardName()).append(": [");
            sb.append(bd.get(i).cardCount()).append(", ").append(bd.get(i).getCostOfCard()).append(" coins, {");
            sb.append(bd.get(i).getDescription()).append("}]\n");
        }
        System.out.printf("*** BuyDeck ***\nCard_type: [count in BD, Cost, {Description}]\n%s", sb);
    }
    public void printPlay() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<play.playPile().size(); i++) {
            sb.append(play.playPile().get(i).cardType().getName()).append(" ");
        }
        System.out.printf("PlayPile: [%s].\n", sb);
    }
    public void printDeck() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<deck.getDeckSize(); i++) {
            sb.append(deck.getCard(i).getName()).append(" ");
        }
        System.out.printf("Deck: [%s].\n", sb);
    }
    public void printDiscardPile() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<discardPile.getSize(); i++) {
            sb.append(discardPile.getCard(i).getName()).append(" ");
        }
        System.out.printf("Discard Pile: [%s].\n", sb);
    }
    public void printHand() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<hand.getHand().size(); i++) {
            sb.append(hand.getHand().get(i).cardType().getName()).append(" ");
        }
        System.out.printf("A: %d, B: %d, C: %d, Hand: [%s].\n", ts.getActions(), ts.getBuys(), ts.getCoins(), sb);
    }
    public void playTreasureCards() {
        int tmp = hand.getHand().size();
        int tmp_c = 0;
        for(int i=0; i<tmp; i++) {
            if (hand.getHand().get(i).cardType() == GameCardType.GAME_CARD_TYPE_COPPER) {
                ts.setCoins(ts.getCoins() + 1);
                play.addCardToPlay(hand.getHand().remove(i));
                i--;
                tmp--;
                tmp_c++;
            }
        }
        if (tmp_c != 0) System.out.printf("%d kariet COPPER bolo premenených na coins (+%d Coins)\n", tmp_c, tmp_c);
    }

}
