package du;

import java.util.LinkedList;

public class BuyDeck {
    private final LinkedList<CardInterface> cards;
    private final GameCardType gct;
    public BuyDeck(GameCardType gct, int count) {
        this.gct = gct;
        cards = new LinkedList<>();
        for(int i=0; i<count; i++) {
            cards.add(new GameCard(gct));
        }
    }
    public int cardCount() {
        return cards.size();
    }
    public CardInterface buy() {
        return cards.removeLast();
    }
    public GameCardType getGameCardType() {
        return gct;
    }
    public int getCostOfCard() {
        return gct.getCost();
    }
    public String getCardName() {
        return gct.getName();
    }
    public String getDescription() {
        return gct.getDescription();
    }
}
