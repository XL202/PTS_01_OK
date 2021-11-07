package du;

import java.util.LinkedList;

public class Turn {
    TurnStatus ts;
    int turnNumber;
    Hand hand;
    Deck deck;
    DiscardPile discardPile;
    Play play;
    LinkedList<BuyDeck> bd;
    public Turn(TurnStatus ts, LinkedList<BuyDeck> bd) {
        this.bd = bd;
        play = new Play();
        discardPile = new DiscardPile(new LinkedList<>());
        deck = new Deck(null, discardPile);
        hand = new Hand(deck);
        this.ts = ts;
        turnNumber = 1;
    }
    public boolean evaluate_card(CardInterface card) {
        int drawCount = card.evaluate(ts);
        hand.drawCards(deck.draw(drawCount));
        return false;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean playCard(int handIdx, boolean actionPhase) {
        if (!actionPhase) {
            System.err.println("Nie je možné hrať kartu pokiaľ nie je ActionPhase!");
            return false;
        }
        if (ts.getActions() > 0) {
            if (hand.cards.size() > handIdx && handIdx > -1) {
                if (hand.isActionCard(handIdx)) {
                    evaluate_card(hand.play(handIdx));
                    play.addCardToPlay(hand.cards.remove(handIdx));
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
            if (hand.getHand().get(i).cardType().isAction) return true;
        }
        return false;
    }

    public boolean buyCards(int idBuyDeck, boolean buyPhase) {
        if (!buyPhase) {
            System.err.println("Nie je možné kupovať kartu pokiaľ nie je BuyPhase!");
            return false;
        }
        if (idBuyDeck > 6 && idBuyDeck < 0) {
            System.err.println("Takýto buy deck neexistuje.");
            return false;
        }
        if (ts.getBuys() < 1) {
            System.err.printf("Nie je dostatočný počet Buys pre nákup karty.\n");
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
    public void printBuyDeck() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bd.size(); i++) {
            sb.append(bd.get(i).getCardName() + ": ");
            sb.append(bd.get(i).cardCount() + ", ");
        }
        System.out.printf("BuyDeck: [%s].\n", sb);
    }

    public void printBuyDeckDescription() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bd.size(); i++) {
            sb.append("[" + i + "]:");
            sb.append(bd.get(i).getCardName() + ": [");
            sb.append(bd.get(i).cardCount() + ", " + bd.get(i).getCostOfCard() + " {");
            sb.append(bd.get(i).getDescription() + "}]\n");
        }
        System.out.printf("*** BuyDeck ***\nCard_type: [count in BD, Cost, {Description}]\n%s", sb);
    }
    public void printPlay() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<play.playPile().size(); i++) {
            sb.append(play.playPile().get(i).cardType().name + " ");
        }
        System.out.printf("PlayPile: [%s].\n", sb);
    }
    public void printDeck() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<deck.deck.size(); i++) {
            sb.append(deck.deck.get(i).cardType().name + " ");
        }
        System.out.printf("Deck: [%s].\n", sb);
    }
    public void printDiscardPile() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<discardPile.get_dp().size(); i++) {
            sb.append(discardPile.get_dp().get(i).cardType().name + " ");
        }
        System.out.printf("Discard Pile: [%s].\n", sb);
    }
    public void printHand() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<hand.getHand().size(); i++) {
            sb.append(hand.getHand().get(i).cardType().name + " ");
        }
        System.out.printf("A: %d, B: %d, C: %d, Hand: [%s].\n", ts.getActions(), ts.getBuys(), ts.getCoins(), sb.toString());
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
