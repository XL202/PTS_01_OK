package du;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class Deck implements DeckInterface {
    LinkedList<CardInterface> deck = new LinkedList<>();
    private final DiscardPileInterface dp;
    public Deck(LinkedList<CardInterface> deck, DiscardPileInterface dp) {
        this.dp = dp;
        if (deck == null) {
            this.deck = new LinkedList<>();
            for(int i=0; i<3; i++) this.deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            for(int i=0; i<7; i++) this.deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_COPPER));
            Collections.shuffle(this.deck);
        }
        else {
            this.deck.addAll(deck);
        }
    }
    @Override
    public int getDeckSize() {
        return deck.size();
    }
    @Override
    public GameCardType getCard(int i) {
        return deck.get(i).cardType();
    }
    @Override
    public Optional<CardInterface> getTopDeckCard() {
        if (deck.isEmpty()) return Optional.empty();
        return Optional.of(deck.get(deck.size()-1));
    }
    @Override
    public LinkedList<CardInterface> draw(int count) {
        int rest_count = 0;
        if (deck.size() < count) {
            rest_count = count - deck.size();
        }
        LinkedList<CardInterface> tmp = new LinkedList<>();
        for(int i=0; i<count - rest_count; i++) tmp.add(deck.removeFirst());
        if (rest_count > 0) {
            //System.out.println("ERR");
            deck = new LinkedList<>(dp.shuffle());
            for(int i=0; i<rest_count; i++) tmp.add(deck.removeFirst());
        }
        return tmp;
    }
    @Override
    public LinkedList<CardInterface> deck() {
        return deck;
    }
}
