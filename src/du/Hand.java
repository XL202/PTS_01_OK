package du;

import java.util.*;

public class Hand {
    LinkedList<CardInterface> cards;
    public Hand(Deck c){
        this.cards = new LinkedList<>();
        LinkedList<CardInterface> a = c.draw(5);
        for(int i = 0; i < 5; i++) cards.add(a.get(i));
    }
    public boolean isActionCard(int idx) {
        return cards.get(idx).cardType().isAction();
    }

    public Optional<CardInterface> getTopHandCard() {
        if (cards.isEmpty()) return Optional.empty();
        return Optional.of(cards.get(cards.size()-1));
    }
    public int getHandSize() {
        return cards.size();
    }
    public CardInterface play(int idx) {
        if (cards.size()> idx) return cards.get(idx);
        else return null;
    }
    public LinkedList<CardInterface> getHand() {
        return cards;
    }
    public void drawCards(LinkedList<CardInterface> c) {
        cards.addAll(c);
    }
    public LinkedList<CardInterface> throwCards() {
        LinkedList<CardInterface> tmp = new LinkedList<>(cards);
        cards = new LinkedList<>();
        //for(int i=0; i<cards.size(); i++) tmp.add(cards.remove(0));
        return tmp;
    }

    public int getSize() {
        return cards.size();
    }
    public CardInterface removeCard(int i) {
        return cards.remove(i);
    }
}
