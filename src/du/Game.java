package du;

public class Game {
    //všetko by bol private, keby som nerobil testy
    final Turn t;
    private TurnStatus ts;
    private final AtLeastNEmptyDecks leastDecks;
    boolean gameEnded = false;
    boolean actionPhase;
    boolean buyPhase;
    //potiaľto private

    public Game(int market, int estate, int copper, int smithy, int village, int festival, int laboratory, int province, int empty_Buy_Decks_to_end_game, boolean shuffling) {
        if (market<1) market = 5;
        if (estate<1) estate = 5;
        if (copper<1) copper = 5;
        if (smithy<1) smithy = 5;
        if (village<1) village = 5;
        if (festival<1) festival = 5;
        if (laboratory<1) laboratory = 5;
        if (empty_Buy_Decks_to_end_game < 0 || empty_Buy_Decks_to_end_game > 7) empty_Buy_Decks_to_end_game = 3;
        ts = new TurnStatus();
        CreateBuyDecks cbd = new CreateBuyDecks(market,estate, copper, smithy, village, festival, laboratory, province);

        t = new Turn(ts, cbd.buyDecks(), shuffling);
        leastDecks = new AtLeastNEmptyDecks(empty_Buy_Decks_to_end_game, cbd.buyDecks());
        System.out.println("Game starts.");
        System.out.println("Turn 1, action phase.\n-----------");

        buyPhase = false;
        actionPhase = true;
        is_Action_phase_possible();

    }
    public boolean playCard(int handIdx) {
        if (gameEnded) {
            System.err.println("Hra sa už skončila!");
            return false;
        }
        if (!actionPhase) {
            System.err.println("Nie je možné hrať kartu pokiaľ nie je ActionPhase!");
            return false;
        }
        if (t.playCard(handIdx)) {
            if (t.getActions() == 0) {
                System.out.println("Počet Actions je 0");
                endPlayCardPhase();
            }
            is_Action_phase_possible();
            return true;
        }
        return false;
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
            buyPhase = false;
            gameEnded = true;
        }
        else {
            ts = new TurnStatus();
            t.drawCardsFromDeck(5);
            System.out.printf("============\nNext turn: Turn %d, action phase.\n", t.getTurnNumber());
            is_Action_phase_possible();
        }
    }
    public void endPlayCardPhase() {
        if (gameEnded) {
            System.err.println("Hra sa už skončila!");
            return;
        }
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
    public boolean buyCard(int idBuyDeck) {
        if (gameEnded) {
            System.err.println("Hra sa už skončila!");
            return false;
        }
        if (!buyPhase) {
            System.err.println("Nie je možné kupovať kartu pokiaľ nie je BuyPhase!");
            return false;
        }
        if (t.buyCards(idBuyDeck)) {
            if (t.getBuys() == 0) {
                System.out.println("Počet Buys je 0.");
                endPlayCardPhase();
            }
            return true;
        }
        return false;
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
        if (gameEnded) {
            System.err.println("Hra sa už skončila!");
        }
    }
}
