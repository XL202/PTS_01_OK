package du;

import java.util.LinkedList;

public class AtLeastNEmptyDecks implements EndGameStrategy {
    private int count;
    private LinkedList<BuyDeck> bd;
    public AtLeastNEmptyDecks(int count, LinkedList<BuyDeck> bd) {
        this.count = count;
        this.bd = new LinkedList<>(bd);
    }

    @Override
    public boolean isGameOver() {
        int tmp = 0;

        for(int i=0; i<bd.size(); i++) {
            //card 7 is province
            if (bd.get(7).cardCount() == 0) return true;
            if (bd.get(i).cardCount() == 0) tmp++;
        }

        if (tmp >= count) return true;
        return false;
    }
}
