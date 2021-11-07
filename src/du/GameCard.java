package du;

public class GameCard implements CardInterface {
    GameCardType g;
    TurnStatus ts;
    public GameCard(GameCardType g) {
        this.g = g;
    }
    public TurnStatus returnNewTurnStatus() {
        return ts;
    }
    @Override
    public int evaluate(TurnStatus ts) {
        this.ts = ts;
        this.ts.setCoins(ts.getCoins() + g.getPlusCoins());
        this.ts.setActions(ts.getActions() + g.getPlusActions());
        this.ts.setBuys(ts.getBuys() + g.getPlusBuys());
        System.out.printf("*** [Card <%s> played: C +%d, A +%d, B +%d, Cards +%d.] ***\n", g.name, g.plusCoins, g.plusActions, g.plusBuys, g.plusCards);
        return g.plusCards;
    }

    @Override
    public GameCardType cardType() {
        return g;
    }
}
