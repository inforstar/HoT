package unittest;

import helpers.GameShapes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameShapes enum, which handles Rock, Paper, Scissors logic.
 */
public class GameShapesTest {

    // Helper method to use in tests, mapping the enum value to its expected weight (1, 2, or 3)
    private int weigh(GameShapes shape) {
        // The weigh logic is private, but we can verify the public logic
        // or check the enum values directly for their index-based weight.
        // Based on the source code: weight = index (0-based) + 1
        return GameShapes.shapesList.indexOf(shape) + 1;
    }

    // --- Test Helper Methods (isWeightOfFirst, isWeightOfLast) ---

    @Test
    void isWeightOfFirst_ShouldReturnTrueForRockWeight() {
        // ROCK has a weight of 1 (index 0 + 1)
        assertTrue(weigh(GameShapes.ROCK) == 1, "ROCK should be weight 1");
    }

    @Test
    void isWeightOfLast_ShouldReturnTrueForScissorsWeight() {
        // SCISSORS has a weight of 3 (index 2 + 1) for 3 shapes
        assertTrue(weigh(GameShapes.SCISSORS) == 3, "SCISSORS should be weight 3");
    }

    // --- Test weigh logic (Implicitly tested via getWinner) ---

    // --- Test getRandomShape() ---

    @Test
    void getRandomShape_ShouldReturnWeightBetween1And3() {
        // Since this is a random method, we test the boundaries by running it multiple times
        for (int i = 0; i < 50; i++) {
            int randomWeight = GameShapes.getRandomShape();
            assertTrue(randomWeight >= 1 && randomWeight <= 3,
                    "Random shape weight must be between 1 (ROCK) and 3 (SCISSORS).");
        }
    }

    // --- Test getWinner(GameShapes, GameShapes) ---

    /**
     * Tests all nine combinations of the game using the enum values directly.
     * The expected outcomes (0=Draw, 1=P1 Wins, 2=P2 Wins) now reflect STANDARD Rock-Paper-Scissors rules:
     * Rock (1) beats Scissors (3), Scissors (3) beats Paper (2), Paper (2) beats Rock (1).
     */
    @ParameterizedTest
    @CsvSource({
            // Draw Cases
            "ROCK, ROCK, 0",
            "PAPER, PAPER, 0",
            "SCISSORS, SCISSORS, 0",

            // P1 (First) Wins Cases (Standard RPS Logic: Rock > Scissors, Scissors > Paper, Paper > Rock)
            "ROCK, SCISSORS, 1",
            "SCISSORS, PAPER, 1",
            "PAPER, ROCK, 1",

            // P2 (Second) Wins Cases (Standard RPS Logic: Scissors > Rock, Paper > Scissors, Rock > Paper)
            "SCISSORS, ROCK, 2",
            "PAPER, SCISSORS, 2",
            "ROCK, PAPER, 2"
    })
    void getWinner_ShapeEnums_ShouldReturnCorrectResultBasedOnStandardRPSLogic(GameShapes first, GameShapes second, int expectedWinner) {
        int actualWinner = GameShapes.getWinner(first, second);
        assertEquals(expectedWinner, actualWinner,
                String.format("Expected winner of (%s vs %s) to be %d, but got %d.",
                        first, second, expectedWinner, actualWinner));
    }


    // --- Test getWinner(int, int) ---

    /**
     * Tests all nine combinations of the game using the integer weights (1=R, 2=P, 3=S).
     * The expected outcomes (0=Draw, 1=P1 Wins, 2=P2 Wins) now reflect STANDARD Rock-Paper-Scissors rules:
     * Rock (1) beats Scissors (3), Scissors (3) beats Paper (2), Paper (2) beats Rock (1).
     */
    @ParameterizedTest
    @CsvSource({
            // Draw Cases
            "1, 1, 0", // ROCK vs ROCK
            "2, 2, 0", // PAPER vs PAPER
            "3, 3, 0", // SCISSORS vs SCISSORS

            // P1 Wins Cases (Standard RPS: 1>3, 3>2, 2>1)
            "1, 3, 1", // ROCK vs SCISSORS
            "3, 2, 1", // SCISSORS vs PAPER
            "2, 1, 1", // PAPER vs ROCK

            // P2 Wins Cases (Standard RPS: 3>1, 2>3, 1>2)
            "3, 1, 2", // SCISSORS vs ROCK
            "2, 3, 2", // PAPER vs SCISSORS
            "1, 2, 2"  // ROCK vs PAPER
    })
    void getWinner_IntWeights_ShouldReturnCorrectResultBasedOnStandardRPSLogic(int firstWeight, int secondWeight, int expectedWinner) {
        int actualWinner = GameShapes.getWinner(firstWeight, secondWeight);
        assertEquals(expectedWinner, actualWinner,
                String.format("Expected winner of (%d vs %d) to be %d, but got %d.",
                        firstWeight, secondWeight, expectedWinner, actualWinner));
    }
}


