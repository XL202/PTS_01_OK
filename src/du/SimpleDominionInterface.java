package du;

import java.util.Optional; 
 
interface SimpleDominionInterface {
    public Optional<GameState> playCard(int handIdx);
    public Optional<GameState> endPlayCardPhase();
    public Optional<GameState> buyCard(int buyCardIdx);
    public Optional<GameState> endTurn();
}
