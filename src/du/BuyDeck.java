package du;

import java.util.LinkedList;

public class BuyDeck {
    LinkedList<CardInterface> cards;
    public BuyDeck(GameCardType gc, int count) {
        cards = new LinkedList();
        for(int i=0; i<count; i++) {
            cards.add(new GameCard(gc));
        }
    }
    public int cardCount() {
        return cards.size();
    }
    public CardInterface buy() {
        return cards.removeLast();
    }
}
