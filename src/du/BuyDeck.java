package du;

import java.util.LinkedList;

public class BuyDeck {
    private LinkedList<CardInterface> cards;
    private GameCardType gct;
    public BuyDeck(GameCardType gct, int count) {
        this.gct = gct;
        cards = new LinkedList();
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
        return gct.cost;
    }
    public String getCardName() {
        return gct.name;
    }
    public String getDescription() {
        return gct.description;
    }
}
