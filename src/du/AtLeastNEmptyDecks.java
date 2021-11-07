package du;

import java.util.LinkedList;

public class AtLeastNEmptyDecks implements EndGameStrategy {
    int a;
    LinkedList<BuyDeck> bd;
    public AtLeastNEmptyDecks(int a, LinkedList<BuyDeck> bd) {
        this.a = a;
        this.bd = new LinkedList<>(bd);
    }

    @Override
    public boolean isGameOver() {
        int tmp = 0;

        for(int i=0; i<bd.size(); i++) {
            //card 1 is estate
            if (bd.get(1).cardCount() == 0) return true;
            if (bd.get(i).cardCount() == 0) tmp++;

        }

        if (tmp < a) return true;
        return false;
    }
}
