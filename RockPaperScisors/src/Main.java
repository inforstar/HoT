import helpers.PlayGame;
import helpers.ReadInput;

import java.util.Arrays;

import static helpers.OutputScreenText.*;

public class Main {
    public static void main(String[] args) {

        try {

            int gameType;
            do {
                WelcomeScreen();
                gameType = ReadInput.getGameType();

                String gameTypeName = "";

                switch (gameType) {
                    case 1:
                        gameTypeName = "Player vs. Computer";
                        break;
                    case 2:
                        gameTypeName = "Computer vs. Computer";
                        break;
                    case 9:
                        gameTypeName = "Exit game";
                        break;
                    default: {
                        gameTypeName = "Invalid selection.";
                        gameType = -1;
                    }
                }
                System.out.println("You selected: " + gameTypeName);

                if (Arrays.asList(1,2).contains(gameType))
                    PlayGame.StartGame(gameType);
            }
            while (gameType != 9);

            GoodbyeScreen();
            ReadInput.scanner.close();
        } catch (Exception exception) {
            OutputErrorScreen();
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}