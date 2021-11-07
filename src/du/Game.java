package du;

import java.util.LinkedList;

public class Game {
    Turn t;
    TurnStatus ts;
    LinkedList<BuyDeck> bd;
    AtLeastNEmptyDecks leastDecks;
    boolean ok = true;
    boolean actionPhase;
    boolean buyPhase;

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

        buyPhase = false;
        actionPhase = true;
        is_Action_phase_possible();

    }
    public void playCard(int handIdx) {
        if (t.playCard(handIdx, actionPhase)) {
            if (t.ts.getActions() == 0) {
                System.out.println("Počet Actions je 0");
                endPlayCardPhase();
            }
            is_Action_phase_possible();
        }

    }

    public boolean nextTurn() {
        if (leastDecks.isGameOver()) return false;
        t.turnNumber++;
        t.ts = new TurnStatus();
        return true;
    }
    public void endGame() {
        t.throwCardsToDiscardPile();

        if (!nextTurn()) {
            int points = 0;
            int cards = 0;
            for(int i=0; i<t.discardPile.getSize(); i++) {
                points += t.discardPile.cards.get(i).cardType().points;
                cards++;
            }
            for(int i=0; i<t.deck.deck.size(); i++) {
                points += t.deck.deck.get(i).cardType().points;
                cards++;
            }
            System.out.println("======================\n***** Game over *****");
            System.out.printf("Počet kariet: %d\n", cards);
            System.out.printf("Počet bodov v decku: %d\n", points);
            printBuyDeck();
            printDeck();
            printDiscardPile();
            printHand();
            printPlay();
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
        if (!t.is_Action_phase_possible()) {
            System.out.println("***V Hand nie sú žiadne action cards!***");
            endPlayCardPhase();
        }
    }
    public void buyCards(int idBuyDeck) {
        if (t.buyCards(idBuyDeck, buyPhase)) {
            if (t.ts.getBuys() == 0) {
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
