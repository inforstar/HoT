package helpers;

import java.util.ArrayList;
import java.util.List;

import static helpers.GameShapes.*;
import static helpers.OutputScreenText.*;
import static helpers.ReadInput.getNextComputerMove;
import static helpers.ReadInput.getPlayerShape;
public class PlayGame {
    public static void StartGame(int gameType) {

        List<Integer> scores = new ArrayList<>(List.of(0, 0, 0)); // scores for DRAW, P1 and P2
        List<String> players;

        if (gameType == 1) {
            players = new ArrayList<>(List.of("Draw", "Player", "Computer")); // scores for DRAW, P1 and P2
            PlayingPlayerVSComputer();
        }
        else {
            players = new ArrayList<>(List.of("Draw", "Computer 1", "Computer 2")); // scores for DRAW, P1 and P2
            PlayingComputerVSComputer();
        }

        for (int round = 1; round <= 3; round++ ) {
            int shapeP1 = -1, shapeP2 = 0;
            for (int move = 1; move <= 3; move++) {
                int nextMove = -1; // Players next move / command


                if (gameType == 1) {
                    // Pick shape for P1
                    shapeP1 = getPlayerShape(round, move);
                    nextMove = shapeP1;
                }
                else {
                    // Pick shape for P1 and players command
                    shapeP1 = getRandomShape();
                    nextMove = getNextComputerMove(round, move);
                    getRandomShape();
                }

                if (nextMove != 9) {
                    // continue playing

                    // Pick shape for P2
                    shapeP2 = getRandomShape();

                    // output shapes
                    ShowMyShape(players.get(1), shapeP1);
                    if (move < 3 && gameType == 1) {
                        shapeP2 = 0; // invalidate P2
                    }
                    ShowMyShape(players.get(2), shapeP2);
                } else {
                    // end game
                    GameTerminated();
                    return;
                }
            }
            // determine winner of the round & show results
            int winner = getWinner(shapeP1, shapeP2);
            scores.set(winner, scores.get(winner) + 1); // increase winning stats
            ShowRoundWinner(round, players, winner, scores,
                    shapesList.get(shapeP1 - 1)
                            + " vs. "
                            + shapesList.get(shapeP2 - 1));
        }

        // Game final screen
        OutputScreenText.ShowGameWinner(players, scores);
    }
}
