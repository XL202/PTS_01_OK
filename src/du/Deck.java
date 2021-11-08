package du;

import java.util.LinkedList;
import java.util.Optional;

public interface Deck {
    int getDeckSize();
    GameCardType getCard(int i);
    Optional<CardInterface> getTopDeckCard();
    LinkedList<CardInterface> draw(int count);
    LinkedList<CardInterface> deck();
}
