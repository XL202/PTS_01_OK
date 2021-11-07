package du;

import java.util.Scanner;

public class testing {
    public static void main(String[] args) {
        Game game = new Game(0,0,0,0,0,0,0,0);
        Scanner sc = new Scanner(System.in);
        String command = null;
        while (game.ok) {
            command = sc.next();
            switch (command) {
                case "E" -> game.endPlayCardPhase();
                case "P" -> game.playCard(sc.nextInt());
                case "B" -> game.buyCards(sc.nextInt());
                case "D" -> game.printDeck();
                case "BD" -> game.printBuyDeck();
                case "BDwD" -> game.printBuyDeckDescription();
                case "DP" -> game.printDiscardPile();
                case "H" -> game.printHand();
                case "PP" -> game.printPlay();
                default -> {
                    System.out.println("E: EndTurn");
                    System.out.println("P <int>: PlayCard <int> (číslovanie od nuly!)");
                    System.out.println("B <int>: BuyCard <int> (číslovanie od nuly!)");
                    System.out.println("D: PrintDeck");
                    System.out.println("BD: PrintBuyDeck");
                    System.out.println("BD: PrintBuyDeckWithDescription");
                    System.out.println("DP: PrintDiscardPile");
                    System.out.println("H: PrintHand");
                    System.out.println("PP: PrintPlay");
                }

            }
        }
        //game.playCard(0);
        //System.out.println(game.t.getTurnNumber());
        //System.out.println(game.ts.getCoins());


        //game.buyCards(2);
        /*game.endPlayCardPhase();
        game.endPlayCardPhase();
        /*game.endPlayCardPhase();
        game.endPlayCardPhase();
        game.endPlayCardPhase();
        game.endTurn();
        game.endTurn();*/

        //System.out.println(game.t.getTurnNumber());
        //System.out.println(game.hand_to_string());

    }
}
