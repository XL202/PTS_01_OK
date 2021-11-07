package du;

//import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameState {
    public List<Card> handCards;
    public List<Map<Integer, Card>> buyCards;
    public int deckSize;
    public int discardPileSize;
    public Optional<Card> discardPileTop;
    public int points;
    public int turn;
}
