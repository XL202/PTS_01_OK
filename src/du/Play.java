package du;

import java.util.LinkedList;
import java.util.Optional;

public class Play {
    LinkedList<CardInterface> a = new LinkedList();
    public Play(){

    }
    public void addCardToPlay(CardInterface c) {
        a.add(c);
    }
    public LinkedList<CardInterface> throwAll() {
        LinkedList<CardInterface> tmp = new LinkedList<>();
        int size = a.size();
        for(int i=0; i<size; i++) tmp.add(a.remove(0));
        return tmp;
    }
    public int getPlaySize() {
        return a.size();
    }
    public Optional<CardInterface> getTopPlayCard() {
        if (a.isEmpty()) return Optional.empty();
        return Optional.of(a.get(a.size()-1));
    }
    public LinkedList<CardInterface> playPile() {
        return a;
    }
}
