package du;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface DiscardPileInterface {
    Optional<CardInterface> getTopCard();
    void addCards(List<CardInterface> cards);
    int getSize();
    LinkedList<CardInterface> shuffle();
    GameCardType getCard(int i);
}
