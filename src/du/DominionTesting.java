package du;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.*;


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

    private DiscardPileWithShuffling discardPile;
    private DeckWithShuffling deck;
    private Hand hand;
    private Play play;

    void setUpTurn() {
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
    public void game_card_type() {
        GameCardType gctVillage = GameCardType.GAME_CARD_TYPE_VILLAGE;

        assertEquals(gctVillage.getPlusActions(), 2);
        assertEquals(gctVillage.getPlusBuys(), 0);
        assertEquals(gctVillage.getPlusCards(), 1);
        assertEquals(gctVillage.getPlusCoins(), 0);
        assertEquals(gctVillage.getPoints(), 0);
        assertEquals(gctVillage.getCost(), 3);
        assertTrue(gctVillage.isAction());
        assertEquals(gctVillage.getName(), "Village");
        assertEquals(gctVillage.getDescription(), "+2 Actions; +1 Card");

        GameCardType gctNew1 = new GameCardType(4, 1, 0, 3, 5, 9,false, "name", "desc");

        assertEquals(gctNew1.getPlusActions(), 4);
        assertEquals(gctNew1.getPlusBuys(), 1);
        assertEquals(gctNew1.getPlusCards(), 0);
        assertEquals(gctNew1.getPlusCoins(), 3);
        assertEquals(gctNew1.getPoints(), 5);
        assertEquals(gctNew1.getCost(), 9);
        assertFalse(gctNew1.isAction());
        assertEquals(gctNew1.getName(), "name");
        assertEquals(gctNew1.getDescription(), "desc");

    }
    @Test
    public void game_test_end_game_by_0_provinces() {
        Game game = new Game(2,2,2,2,1,2,2,1,3,false);
        //na začiatku sa nedá hrať action phase
        game.t.setCoinsTo8();
        assertTrue(game.buyCard(7));
        assertEquals(game.t.actualPoints(), 9); //3 uvodne a 6 za province
        assertEquals(game.t.actualCountOfCard(), 11); //bola uspesne kupena len 1 karat +  10 povodnych


    }
    @Test
    public void game_test_without_shuffling() {
        Game game = new Game(2,2,2,2,1,2,2,2,3,false);
        //na začiatku sa nedá hrať action phase
        assertFalse(game.playCard(5));
        assertFalse(game.buyCard(8));
        assertFalse(game.buyCard(-1));

        //na začiatku má 3 estaty a 2 copper (2 mince)
        //buy deck 8 neexistuje, je len od 0 po 7
        assertEquals(game.t.getCoins(), 2);
        assertEquals(game.t.getActions(), 1);
        assertEquals(game.t.getBuys(), 1);

        assertFalse(game.buyCard(8));
        //buying estate (cost 2 coins)
        assertTrue(game.buyCard(1));

        //nie je buy phase, pretože zostalo 0 buys a preplo do action phase

        //a hneď preplo aj do buy phase, pretož na ruke neboli action cards
        //kúpená festival card
        assertTrue(game.buyCard(5));
        // action phase, turn 3
        assertEquals(game.t.turnNumber, 3);
        assertFalse(game.actionPhase);
        assertTrue(game.buyPhase);
        //premeňené 1 copper na 1 coins
        assertEquals(game.t.getCoins(), 1);
        assertEquals(game.t.getActions(), 1);
        assertEquals(game.t.getBuys(), 1);
        assertFalse(game.playCard(0));
        //preskočenie buy phase
        game.endPlayCardPhase();
        assertEquals(game.t.turnNumber, 4);
        assertTrue(game.actionPhase);
        assertFalse(game.buyPhase);
        //premeňené 4 copper na 1 coins
        assertEquals(game.t.getCoins(), 4);
        assertEquals(game.t.getActions(), 1);
        assertEquals(game.t.getBuys(), 1);
        assertFalse(game.playCard(4));
        assertTrue(game.playCard(0));
        //bola zahraná karta festival +2A +1B, +2coins (jedna action bola odpočítaná za zahranie karty festival)
        assertEquals(game.t.getCoins(), 6);
        assertEquals(game.t.getActions(), 2);
        assertEquals(game.t.getBuys(), 2);
        assertEquals(game.t.turnNumber, 4);
        assertFalse(game.actionPhase);
        assertTrue(game.buyPhase);
        assertTrue(game.buyCard(4)); //village
        assertFalse(game.buyCard(4)); //už nie je v buy decku
        assertFalse(game.buyCard(7)); //malo coins
        assertTrue(game.buyCard(1)); //buy estate
        //konec buy phase, pocet buys je 0
        //next (5) phase, na ruke nie su action cards, tak ide buy phase
        assertTrue(game.buyCard(2)); // buy copper
        //minute buys a coins
        // play Phase, turns 6
        assertFalse(game.playCard(4)); //mimo decku
        game.t.printHand();
        assertTrue(game.playCard(1)); //zahrana village + 2B + 1Card
        game.t.printHand();
        assertEquals(game.t.getCoins(), 3);
        assertEquals(game.t.getActions(), 2);
        assertEquals(game.t.getBuys(), 1);
        assertFalse(game.buyCard(4)); //buy village, nie je v už v buy decku
        game.endPlayCardPhase();
        assertTrue(game.playCard(0)); //zahrana festival +2A +1B +1C
        assertTrue(game.buyCard(6)); //buy laboratory
        assertTrue(game.buyCard(2)); //buy copper
        //END of game because 3 of decks are empty
        assertEquals(game.t.actualCountOfCard(),17);
        //bought cards:7
        //estate cards: 5 (3 from start deck and 2 bought)
        assertEquals(game.t.actualCountOfCard(),5);
        assertTrue(game.gameEnded);
        assertFalse(game.playCard(0));
        assertFalse(game.buyCard(0));

    }
    @Test
    public void buy_deck_test() {
        CreateBuyDecks cbd = new CreateBuyDecks(1,2,3,4, 5, 6, 7, 8);
        for(int i=0; i<cbd.buyDecks().size(); i++) {
            assertEquals(cbd.buyDecks().get(i).cardCount(), i+1);
        }
    }

}