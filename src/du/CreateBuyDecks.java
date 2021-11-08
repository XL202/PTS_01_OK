package du;

import java.util.LinkedList;

public class CreateBuyDecks {
    private final LinkedList<BuyDeck> bd;
    public CreateBuyDecks(LinkedList<Integer> bdCapacity) {
        bd = new LinkedList<>();
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_MARKET, bdCapacity.get(0)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_ESTATE, bdCapacity.get(1)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_COPPER, bdCapacity.get(2)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_SMITHY, bdCapacity.get(3)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_VILLAGE, bdCapacity.get(4)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_FESTIVAL, bdCapacity.get(5)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_LABORATORY, bdCapacity.get(6)));
        bd.add(new BuyDeck(GameCardType.GAME_CARD_TYPE_PROVINCE, bdCapacity.get(7)));
    }

    public LinkedList<BuyDeck> buyDecks() {
        return bd;
    }
}
