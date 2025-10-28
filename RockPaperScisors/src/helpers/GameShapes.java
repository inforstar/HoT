package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
public enum GameShapes {
    // Shapes definition for the games
    ROCK, PAPER, SCISSORS;
    private static final Random PRNG = new Random();
    private static final GameShapes[] shapes = values();
    public static final List<GameShapes> shapesList = Arrays.asList(shapes);

    public static int getRandomShape()  {
        return weigh(shapes[PRNG.nextInt(shapes.length)]);
    }
    private static int weigh(GameShapes myShape) {
        // check how much power shape has
        int shapeWeight = shapesList.indexOf(myShape) + 1;
        return shapeWeight;
    }

    private static boolean isWeightOfFirst(int myShape) {
        // check if shape ist first in row
        return myShape == 1;
    }

    private static boolean isWeightOfLast(int myShape) {
        // check if shape ist last in row
        return myShape == shapesList.size();
    }

    public static int getWinner(GameShapes first, GameShapes second) {
        return getWinner(weigh(first), weigh(second));
    }

    public static int getWinner(int shapeP1, int shapeP2) {
        // returning 0 for draw, 1 for victory of player 1, and 2 for victory of player 2
        int winner = 0;
        if (shapeP1 != shapeP2) {
            if ((isWeightOfFirst(shapeP1) && isWeightOfLast(shapeP2)) ||
                    (isWeightOfFirst(shapeP2) && isWeightOfLast(shapeP1)))
                winner = ((shapeP1 < shapeP2) ? 1 : 2);
            else
                winner = ((shapeP2 < shapeP1) ? 1 : 2);
        }

        return winner;
    }
}
