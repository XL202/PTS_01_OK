package du;

import java.util.LinkedList;

public class Game {
    Turn t;
    TurnStatus ts;
    LinkedList<BuyDeck> bd;
    LinkedList<GameCardType> gct;
    AtLeastNEmptyDecks leastDecks;
    boolean ok = true;

    public Game(int m, int e, int c, int s, int v, int f, int l, int empty_Buy_Decks_to_end_game) {
        if (m<5) m = 5;
        if (e<5) e = 5;
        if (c<5) c = 5;
        if (s<5) s = 5;
        if (v<5) v = 5;
        if (f<5) f = 5;
        if (l<5) l = 5;
        if (empty_Buy_Decks_to_end_game < 0 || empty_Buy_Decks_to_end_game > 6) empty_Buy_Decks_to_end_game = 3;

        ts = new TurnStatus();

        bd = new LinkedList<>();
        this.gct = new LinkedList<>();
        gct.add(GameCardType.GAME_CARD_TYPE_MARKET);
        gct.add(GameCardType.GAME_CARD_TYPE_ESTATE);
        gct.add(GameCardType.GAME_CARD_TYPE_COPPER);
        gct.add(GameCardType.GAME_CARD_TYPE_SMITHY);
        gct.add(GameCardType.GAME_CARD_TYPE_VILLAGE);
        gct.add(GameCardType.GAME_CARD_TYPE_FESTIVAL);
        gct.add(GameCardType.GAME_CARD_TYPE_LABORATORY);

        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_MARKET, m));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_ESTATE, e));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_COPPER, c));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_SMITHY, s));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_VILLAGE, v));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_FESTIVAL, f));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_LABORATORY, l));
        t = new Turn(ts, bd);
        leastDecks = new AtLeastNEmptyDecks(empty_Buy_Decks_to_end_game, bd);
        System.out.println("Game starts.");
        System.out.println("Turn 1, action phase.\n-----------");

        t.buyPhase = false;
        t.actionPhase = true;
        is_Action_phase_possible();

    }
    public boolean playCard(int handIdx) {
        return t.playCard(handIdx);
    }

    public boolean endPlayCardPhase() {
        if (t.buyPhase) {
            t.buyPhase = false;
            t.actionPhase = true;
            t.endTurn();
        }
        else if (t.actionPhase) {
            System.out.printf("============\nTurn %s, buy phase\n------------\n", t.getTurnNumber());
            t.actionPhase = false;
            t.buyPhase = true;
        }
        return false;
    }

    private void is_Action_phase_possible() {
        t.is_Action_phase_possible();
    }
    public boolean buyCards(int idBuyDeck) {
        return t.buyCards(idBuyDeck);
    }
    public void printBuyDeck() {
        t.printBuyDeck();
    }
    public void printBuyDeckDescription() {
        t.printBuyDeckDescription();
    }
    public void printPlay() {
        t.printPlay();
    }
    public void printDeck() {
        t.printDeck();
    }
    public void printDiscardPile() {
        t.printDiscardPile();
    }
    public void printHand() {
        t.printHand();
    }

}
