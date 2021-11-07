package du;

import java.util.LinkedList;
import java.util.Optional;

public class Play {
    LinkedList<CardInterface> cards = new LinkedList();
    public Play(){

    }
    public void addCardToPlay(CardInterface c) {
        cards.add(c);
    }
    public LinkedList<CardInterface> throwAll() {
        LinkedList<CardInterface> tmp = new LinkedList<>();
        int size = cards.size();
        for(int i=0; i<size; i++) tmp.add(cards.remove(0));
        return tmp;
    }
    public int getPlaySize() {
        return cards.size();
    }
    public Optional<CardInterface> getTopPlayCard() {
        if (cards.isEmpty()) return Optional.empty();
        return Optional.of(cards.get(cards.size()-1));
    }
    public LinkedList<CardInterface> playPile() {
        return cards;
    }
    public GameCardType getCard(int i) {
        return cards.get(i).cardType();
    }
}
