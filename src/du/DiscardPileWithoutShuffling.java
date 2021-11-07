package du;

import java.util.*;

public class DiscardPileWithoutShuffling implements DiscardPile {
    LinkedList<CardInterface> cards = new LinkedList<>();

    public DiscardPileWithoutShuffling(LinkedList<CardInterface> cards) {
        for(int i=0; i<cards.size(); i++) this.cards.add(cards.get(i));
    }
    @Override
    public Optional<CardInterface> getTopCard() {
        if (cards.isEmpty()) return Optional.empty();
        return Optional.of(cards.get(cards.size()-1));
    }
    @Override
    public void addCards(List<CardInterface> cards) {
        this.cards.addAll(cards);
    }
    @Override
    public int getSize() {
        return cards.size();
    }
    @Override
    public LinkedList<CardInterface> shuffle() {
        Collections.shuffle(cards);
        LinkedList<CardInterface> cards_to_send = cards;
        cards = new LinkedList<CardInterface>();
        return cards_to_send;
    }
    @Override
    public LinkedList<CardInterface> get_dp() {
        return cards;
    }
    @Override
    public GameCardType getCard(int i) {
        return cards.get(i).cardType();
    }
}



