package du;

import java.util.LinkedList;

public class CreateBuyDecks {
    private final LinkedList<BuyDeck> bd;
    public CreateBuyDecks(int m, int e, int c, int s, int v, int f, int l, int p) {
        bd = new LinkedList<>();
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_MARKET, m));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_ESTATE, e));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_COPPER, c));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_SMITHY, s));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_VILLAGE, v));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_FESTIVAL, f));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_LABORATORY, l));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_PROVINCE, p));
    }

    public LinkedList<BuyDeck> buyDecks() {
        return bd;
    }
}
