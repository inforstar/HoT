package helpers;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static helpers.OutputScreenText.*;

public class ReadInput {
    public static final Scanner scanner = new Scanner(System.in);

    public static int readChoice() {
        int myChoice = -1;

        try {
            myChoice = scanner.nextInt();
        } catch (InputMismatchException exc) {
            // nothing
        }
        scanner.nextLine();

        return myChoice;
    }
    public static int readEmptyLineOrChoice() {
        int myChoice = -1;

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            // Case 1: User just pressed Enter (empty line)
            return myChoice;
        }

        try {
            // Case 2: User entered a non-empty string, attempt to parse it
            myChoice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // nothing
        }

        return myChoice;
    }

    // Game types
    // 1: Player vs. Computer
    // 2: Computer vs. Computer

    public static int getGameType() {
        int gameType = -1;

        do {
            MenuGameType();
            MakeYourChoice();
            int myChoice = ReadInput.readChoice();

            if (!Arrays.asList(1,2,9).contains(myChoice))
                WrongOptionSelected();
            else
                gameType = myChoice;

        } while (gameType == -1);

        return gameType;
    }

    public static int getPlayerShape(int round, int move) {
        int playerShape = -1;

        do {
            MakeYourMove(round, move);
            int myChoice = ReadInput.readChoice();

            if (((myChoice > 0 && myChoice <= GameShapes.shapesList.size()) ||
                    myChoice == 9))
                playerShape = myChoice;
            else
                WrongOptionSelected();
        } while (playerShape == -1);

        return playerShape;
    }


    public static int getNextComputerMove(int round, int move) {
        int nextMove = -1;

        do {
            ComputerMove(round, move);
            int myChoice = ReadInput.readEmptyLineOrChoice();

            if (myChoice == 9)
                nextMove = myChoice;
            else
                nextMove = 0; // continue with game
        } while (nextMove == -1);

        return nextMove;
    }

}
