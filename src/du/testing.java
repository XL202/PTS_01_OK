package du;

import java.util.InputMismatchException;
import java.util.Scanner;

public class testing {
    public static void main(String[] args) {
        Game game = new Game(1,1,1,1,1,1,1,1,3, true);
        Scanner sc = new Scanner(System.in);
        String command;
        boolean cycle = true;
        while (cycle) {
            command = sc.next();
            switch (command) {
                case "E" -> game.endPlayCardPhase();
                case "P" -> {
                    try {
                        game.playCard(sc.nextInt());
                    }
                    catch (InputMismatchException e) {
                        System.err.println("Nebol zadaný argument typu INTEGER!");
                    }
                }
                case "B" -> {
                    try {
                        game.buyCard(sc.nextInt());
                    }
                    catch (InputMismatchException e) {
                        System.err.println("Nebol zadaný argument typu INTEGER!");
                    }
                }
                case "D" -> game.printDeck();
                case "BD" -> game.printBuyDeck();
                case "BDwD" -> game.printBuyDeckDescription();
                case "DP" -> game.printDiscardPile();
                case "H" -> game.printHand();
                case "PP" -> game.printPlay();
                case "Phase" ->game.currentPhase();
                case "Exit" -> cycle = false;
                default -> {
                    System.out.println("E: EndTurn");
                    System.out.println("P <int>: PlayCard <int> (číslovanie od nuly!)");
                    System.out.println("B <int>: BuyCard <int> (číslovanie od nuly!)");
                    System.out.println("D: PrintDeck");
                    System.out.println("BD: PrintBuyDeck");
                    System.out.println("BDwD: PrintBuyDeckWithDescription");
                    System.out.println("DP: PrintDiscardPile");
                    System.out.println("H: PrintHand");
                    System.out.println("PP: PrintPlay");
                    System.out.println("Phase: Current turn and phase");
                    System.out.println("Exit: End of this program");
                }

            }
        }
    }
}
