package du;

import java.util.*;

public class DiscardPile implements DiscardPileInterface {
    LinkedList<CardInterface> cards = new LinkedList<>();
    public DiscardPile(LinkedList<CardInterface> cards) {
        this.cards.addAll(cards);
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
        cards = new LinkedList<>();
        return cards_to_send;
    }
    @Override
    public GameCardType getCard(int i) {
        return cards.get(i).cardType();
    }
}
        
        
