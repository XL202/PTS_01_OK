package du;

import java.util.LinkedList;

public class AtLeastNEmptyDecks implements EndGameStrategy {
    final int count;
    final LinkedList<BuyDeck> bd;
    public AtLeastNEmptyDecks(int count, LinkedList<BuyDeck> bd) {
        this.count = count;
        this.bd = new LinkedList<>(bd);
    }

    @Override
    public boolean isGameOver() {
        int tmp = 0;

        for (BuyDeck buyDeck : bd) {
            //card 7 is province
            if (bd.get(7).cardCount() == 0) return true;
            if (buyDeck.cardCount() == 0) tmp++;
        }

        return tmp >= count;
    }
}
