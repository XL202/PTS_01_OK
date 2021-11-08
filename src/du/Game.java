package du;

import java.util.LinkedList;

public class Game {
    private final Turn t;
    private TurnStatus ts;
    private final AtLeastNEmptyDecks leastDecks;
    private boolean ok = true;
    private boolean actionPhase;
    private boolean buyPhase;

    public Game(int m, int e, int c, int s, int v, int f, int l, int p, int empty_Buy_Decks_to_end_game, boolean shuffling) {
        if (m<1) m = 5;
        if (e<1) e = 5;
        if (c<1) c = 5;
        if (s<1) s = 5;
        if (v<1) v = 5;
        if (f<1) f = 5;
        if (l<1) l = 5;
        if (empty_Buy_Decks_to_end_game < 0 || empty_Buy_Decks_to_end_game > 6) empty_Buy_Decks_to_end_game = 3;
        ts = new TurnStatus();
        LinkedList<BuyDeck> bd = new LinkedList<>();
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_MARKET, m));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_ESTATE, e));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_COPPER, c));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_SMITHY, s));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_VILLAGE, v));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_FESTIVAL, f));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_LABORATORY, l));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_PROVINCE, p));
        t = new Turn(ts, bd, shuffling);
        leastDecks = new AtLeastNEmptyDecks(empty_Buy_Decks_to_end_game, bd);
        System.out.println("Game starts.");
        System.out.println("Turn 1, action phase.\n-----------");

        buyPhase = false;
        actionPhase = true;
        is_Action_phase_possible();

    }
    public void playCard(int handIdx) {
        if (!actionPhase) {
            System.err.println("Nie je možné hrať kartu pokiaľ nie je ActionPhase!");
            return;
        }
        if (t.playCard(handIdx)) {
            if (t.getActions() == 0) {
                System.out.println("Počet Actions je 0");
                endPlayCardPhase();
            }
            is_Action_phase_possible();
        }
    }

    private boolean nextTurn() {
        if (leastDecks.isGameOver()) return false;
        t.turnNumber++;
        t.resetTurnStatus();
        return true;
    }
    private void endGame() {
        t.throwCardsToDiscardPile();
        if (!nextTurn()) {
            t.printFinalStatus();
            ok = false;
        }
        else {
            ts = new TurnStatus();
            t.drawCardsFromDeck(5);
            System.out.printf("============\nNext turn: Turn %d, action phase.\n", t.getTurnNumber());
            is_Action_phase_possible();
        }
    }
    public void endPlayCardPhase() {
        if (buyPhase) {
            buyPhase = false;
            actionPhase = true;
            endGame();
        }
        else if (actionPhase) {
            System.out.printf("============\nTurn %s, buy phase\n------------\n", t.getTurnNumber());
            actionPhase = false;
            buyPhase = true;
        }
    }

    private void is_Action_phase_possible() {
        if (!t.hasMoreActions()) {
            System.out.println("Počet akcii je 0.");
            endPlayCardPhase();
            return;
        }

        if (!t.is_Action_phase_possible()) {
            System.out.println("***V Hand nie sú žiadne action cards!***");
            endPlayCardPhase();
        }
    }
    public void buyCard(int idBuyDeck) {
        if (!buyPhase) {
            System.err.println("Nie je možné kupovať kartu pokiaľ nie je BuyPhase!");
            return;
        }
        if (t.buyCards(idBuyDeck)) {
            if (t.getBuys() == 0) {
                System.out.println("Počet Buys je 0.");
                endPlayCardPhase();
            }
        }
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
    public void currentPhase() {
        if (actionPhase) System.out.printf("Actual phase: Action, Turn %d\n", t.getTurnNumber());
        if (buyPhase) System.out.printf("Actual phase: Buy, Turn %d\n", t.getTurnNumber());
    }
    public boolean getGameStatus() {
        return ok;
    }
}
