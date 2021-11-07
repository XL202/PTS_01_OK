package du;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface DiscardPile {
    Optional<CardInterface> getTopCard();
    void addCards(List<CardInterface> cards);
    int getSize();
    LinkedList<CardInterface> shuffle();
    LinkedList<CardInterface> get_dp();
    GameCardType getCard(int i);
}
