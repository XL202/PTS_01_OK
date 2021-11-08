package du;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class FakeCard implements CardInterface {
    private final GameCardType cardType;

    FakeCard(GameCardType cardType) {
        this.cardType = cardType;
    }

    public int evaluate(TurnStatus ts) {
        ts.setCoins(ts.getCoins() + cardType.getPlusCoins());
        ts.setActions(ts.getActions() + cardType.getPlusActions());
        ts.setBuys(ts.getBuys() + cardType.getPlusBuys());
        return cardType.getPlusCards();
    }

    public GameCardType cardType() {
        return cardType;
    }
}


public class DominionTesting  {
    //*****************DiscardPileTest*********************//
    private DiscardPileWithShuffling pile1;
    private DiscardPileWithShuffling pile2;

    private void assertTopDiscardPileIs(DiscardPileWithShuffling pile, String string) {
        assertTrue(pile.getTopCard().isPresent());
        assertEquals(pile.getTopCard().get().cardType().getName(), string);
    }

    private void assertTopDiscardPileIsNone(DiscardPileWithShuffling pile) {
        assertTrue(pile.getTopCard().isEmpty());
    }

    void setUpDiscardPile() {
        pile1 = new DiscardPileWithShuffling(new LinkedList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }});
        pile2 = new DiscardPileWithShuffling(new LinkedList<>());
    }

    @Test
    public void test_get_top_card_from_DiscardPile() {
        setUpDiscardPile();
        assertTopDiscardPileIs(pile1, "Copper");
        assertTopDiscardPileIsNone(pile2);
    }

    @Test
    public void test_add_cards_and_get_size_DiscardPile() {
        setUpDiscardPile();
        assertEquals(pile2.getSize(), 0);
        pile2.addCards(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        }});
        assertEquals(pile2.getSize(), 1);
        assertTopDiscardPileIs(pile2, "Estate");
        pile2.addCards(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }});
        assertEquals(pile2.getSize(), 2);
        assertTopDiscardPileIs(pile2, "Copper");
    }
    //*****************DeckTest*********************//
    private DeckWithShuffling deck1;
    private DeckWithShuffling deck2;

    private void assertTopDeckIs(DeckWithShuffling deck, String string) {
        assertTrue(deck.getTopDeckCard().isPresent());
        assertEquals(deck.getTopDeckCard().get().cardType().getName(), string);
    }

    private void assertTopDeckIsNone(DeckWithShuffling deck) {
        assertTrue(deck.getTopDeckCard().isEmpty());
    }

    void setUpDeck() {
        deck1 = new DeckWithShuffling(new LinkedList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }}, null);
        deck2 = new DeckWithShuffling(null, null);
        deck2.deck.clear();
    }

    @Test
    public void test_get_top_card_from_Deck() {
        setUpDeck();
        assertTopDeckIs(deck1, "Copper");
        assertTopDeckIsNone(deck2);
    }

    @Test
    public void test_add_cards_and_get_size_Deck() {
        setUpDeck();
        assertEquals(deck2.getDeckSize(), 0);
        deck2.deck.addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        }});
        assertEquals(deck2.getDeckSize(), 1);
        assertTopDeckIs(deck2, "Estate");
        deck2.deck.addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }});
        assertEquals(deck2.getDeckSize(), 2);
        assertTopDeckIs(deck2, "Copper");
    }
    //*****************HandTest*********************//
    private Hand hand1;
    private Hand hand2;

    private void assertTopHandIs(Hand hand, String string) {
        assertTrue(hand.getTopHandCard().isPresent());
        assertEquals(hand.getTopHandCard().get().cardType().getName(), string);
    }

    private void assertTopHandIsNone(Hand hand) {
        assertTrue(hand.getTopHandCard().isEmpty());
    }

    void setUpHand() {
        DeckWithShuffling deck = new DeckWithShuffling(new LinkedList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_LABORATORY));
        }}, null);
        hand1 = new Hand(deck);
        deck = new DeckWithShuffling(new LinkedList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        }}, null);
        hand2 = new Hand(deck);
        hand2.removeCard(hand2.getSize()-1);
        hand2.removeCard(hand2.getSize()-1);
        hand2.removeCard(hand2.getSize()-1);
        hand2.removeCard(hand2.getSize()-1);
        hand2.removeCard(hand2.getSize()-1);
    }

    @Test
    public void test_get_top_card_from_Hand() {
        setUpHand();
        assertTopHandIs(hand1, "Laboratory");
        assertTopHandIsNone(hand2);
    }

    @Test
    public void test_add_cards_and_get_size_Hand() {
        setUpHand();
        assertEquals(hand2.getHandSize(), 0);
        hand2.getHand().addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        }});
        assertEquals(hand2.getHandSize(), 1);
        assertTopHandIs(hand2, "Estate");
        hand2.cards.addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }});
        assertEquals(hand2.getHandSize(), 2);
        assertTopHandIs(hand2, "Copper");
    }
    //*****************Play*********************//
    private Play play1;
    private Play play2;

    private void assertTopPlayIs(Play play, String string) {
        assertTrue(play.getTopPlayCard().isPresent());
        assertEquals(play.getTopPlayCard().get().cardType().getName(), string);
    }

    private void assertTopPlayIsNone(Play play) {
        assertTrue(play.getTopPlayCard().isEmpty());
    }

    void setUpPlay() {
        play1 = new Play();
        play2 = new Play();
        play1.playPile().add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
    }

    @Test
    public void test_get_top_card_from_Play() {
        setUpPlay();
        assertTopPlayIs(play1, "Estate");
        assertTopPlayIsNone(play2);
    }

    @Test
    public void test_add_cards_and_get_size_Play() {
        setUpPlay();
        assertEquals(play2.getPlaySize(), 0);
        play2.playPile().addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        }});
        assertEquals(play2.getPlaySize(), 1);
        assertTopPlayIs(play2, "Copper");
        play2.playPile().addAll(new ArrayList<>() {{
            add(new FakeCard(GameCardType.GAME_CARD_TYPE_LABORATORY));
        }});
        assertEquals(play2.getPlaySize(), 2);
        assertTopPlayIs(play2, "Laboratory");
    }

    //*****************Turn_evaluate_test_draw_cards_add_{A_B_C}*********************//

    private TurnStatus ts;
    private int turnNumber = 1;
    private DiscardPileWithShuffling discardPile;
    private DeckWithShuffling deck;
    private Hand hand;
    private Play play;
    private Turn t;

    void setUpTurn() {
        ts = new TurnStatus();
        int turnNumber = 1;
        LinkedList<CardInterface> tmp = new LinkedList<>();
        tmp.add(new FakeCard(GameCardType.GAME_CARD_TYPE_COPPER));
        tmp.add(new FakeCard(GameCardType.GAME_CARD_TYPE_LABORATORY));
        tmp.add(new FakeCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        //do disard pile sa dajú 3 karty (C, L, E)
        discardPile = new DiscardPileWithShuffling(tmp);

        tmp.add(new FakeCard(GameCardType.GAME_CARD_TYPE_VILLAGE));
        tmp.add(new FakeCard(GameCardType.GAME_CARD_TYPE_FESTIVAL));

        deck = new DeckWithShuffling(tmp, discardPile);
        //do decku sa dá 5 kariet (C, L, E, V, F)
        hand = new Hand(deck);
        //v hande sa zoberie z decku 5 kariet
        play = new Play();
        t = new Turn(ts, null, true);

        //hand.drawCards(new FakeCard(GameCardType.GAME_CARD_TYPE_SMITHY));
    }

    @Test
    public void test_draw_from_deck_where_is_enough_cards() {
        setUpTurn();
        assertEquals(hand.getHandSize(), 5);
        assertEquals(discardPile.getSize(),3);
        assertEquals(deck.getDeckSize(),0);
        assertTopPlayIsNone(play);
    }
    @Test
    public void test_draw_from_deck_where_is_not_enough_cards() {
        setUpTurn();
        assertEquals(hand.getHandSize(), 5);
        assertEquals(discardPile.getSize(),3);
        assertEquals(deck.getDeckSize(),0);
        hand.drawCards(deck.draw(2));
        assertEquals(hand.getHandSize(), 7);
        assertEquals(discardPile.getSize(), 0);
        assertEquals(deck.getDeckSize(), 1);
        discardPile.cards.add(new FakeCard(GameCardType.GAME_CARD_TYPE_VILLAGE));
        discardPile.cards.add(new FakeCard(GameCardType.GAME_CARD_TYPE_MARKET));
        discardPile.cards.add(new FakeCard(GameCardType.GAME_CARD_TYPE_SMITHY));
        assertEquals(discardPile.getSize(), 3);
        assertTopDiscardPileIs(discardPile, "Smithy");
        hand.drawCards(deck.draw(2));
        assertEquals(hand.getHandSize(), 9);
        assertEquals(discardPile.getSize(), 0);
        assertEquals(deck.getDeckSize(), 2);
    }
    @Test
    public void test_draw_from_deck_where_is_not_enough_cards2() {
        setUpTurn();
        assertEquals(hand.getHandSize(), 5);
        assertTopHandIs(hand, "Festival");
        assertEquals(discardPile.getSize(),3);
        assertTopDiscardPileIs(discardPile, "Estate");
        assertEquals(deck.getDeckSize(),0);
        hand.drawCards(deck.draw(1));
        assertEquals(hand.getHandSize(), 6);
        assertEquals(discardPile.getSize(),0);
        assertEquals(deck.getDeckSize(),2);
        hand.drawCards(deck.draw(1));
        assertEquals(hand.getHandSize(), 7);
        assertEquals(discardPile.getSize(),0);
        assertEquals(deck.getDeckSize(),1);
    }
    @Test
    public void test_play_cards_from_hand() {
        /*setUpTurn();
        assertEquals(t.getCoins(), 0);
        assertEquals(t.getBuys(), 1);
        assertEquals(t.getActions(), 1);
        assertEquals(hand.getHandSize(), 5);
        assertTopDiscardPileIs(discardPile, "Estate");
        assertTopHandIs(hand, "Festival"); //+2A +1B +2C +0Cards
        FakeCard c1 = new FakeCard(hand.getCard(4));
        c1.evaluate(ts);
        assertEquals(t.getCoins(), 2);
        assertEquals(t.getBuys(), 2);
        assertEquals(t.getActions(), 3);
        discardPile.cards.add(hand.removeCard(4));
        assertEquals(hand.getHandSize(), 4);
        assertEquals(discardPile.getSize(),4);
        assertTopDiscardPileIs(discardPile, "Festival");
        assertTopHandIs(hand, "Village");
        assertEquals(t.getCoins(), 2);
        assertEquals(t.getBuys(), 2);
        assertEquals(t.getActions(), 3);
        System.out.println(hand.getCard(1).getName()); // laboratory +1A +0B +0C +2Cards
        FakeCard c2 = new FakeCard(hand.getCard(1));
        hand.getHand().addAll(deck.draw(c2.evaluate(ts)));
        //assertEquals(t.getCoins(), 2);
        assertEquals(t.getBuys(), 2);
        assertEquals(t.getActions(), 4);
        discardPile.cards.add(hand.removeCard(1));
        assertEquals(hand.getHandSize(), 5);
        assertEquals(discardPile.getSize(),5);*/
    }
    @Test
    public void game_test_with_shuffling() {
        Game game = new Game(1,1,1,1,1,1,1,1,3,false);

    }
}