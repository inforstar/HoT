package unittest;

import helpers.GameShapes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Negative unit tests for the GameShapes enum, focusing on invalid and boundary inputs
 * to ensure the logic handles them gracefully.
 */
public class GameShapesNegativeTest {

    // --- Tests for Invalid Weights (< 1 or > 3) ---

    /**
     * Test cases where P1 provides an invalid low weight (0 or negative).
     * The current implementation does not explicitly validate inputs, so these tests
     * confirm the actual, albeit likely unintended, behavior of the comparison logic
     * when weights are outside the defined range (1, 2, 3).
     * * Current Logic Analysis (for 0 vs 1):
     * 1. first != second (true)
     * 2. Wrap-around check is false (0 is not first, 1 is not last)
     * 3. Falls to else: winner = ((second < first) ? 2 : 1)
     * 4. (1 < 0) is false. Result: 1 (P1 wins)
     */
    @ParameterizedTest(name = "P1 Weight {0} vs P2 Weight {1} should result in Winner {2}")
    @CsvSource({
            // P1 Invalid Low Weight vs Valid P2 Weight
            "0, 1, 1",   // P1 (0) vs P2 (1). P1 wins (Unintended behavior)
            "0, 3, 1",   // P1 (0) vs P2 (3). P1 wins (Unintended behavior)
            "-1, 2, 1",  // P1 (-1) vs P2 (2). P1 wins (Unintended behavior)

            // P2 Invalid Low Weight vs Valid P1 Weight
            "1, 0, 2",   // P1 (1) vs P2 (0). P2 wins (Unintended behavior)
            "3, -1, 1"   // P1 (3) vs P2 (-1). P1 wins (Unintended behavior)
    })
    void getWinner_ShouldHandleInvalidLowWeights(int firstWeight, int secondWeight, int expectedWinner) {
        int actualWinner = GameShapes.getWinner(firstWeight, secondWeight);
        assertEquals(expectedWinner, actualWinner,
                "Expected winner based on existing logic for low weights.");
    }

    /**
     * Test cases where P1 or P2 provides an invalid high weight (4 or more).
     * The goal is to document the behavior when inputs exceed the defined number of shapes.
     * * Current Logic Analysis (for 4 vs 1):
     * 1. first != second (true)
     * 2. Wrap-around check is false (4 is not first, 1 is not last)
     * 3. Falls to else: winner = ((second < first) ? 2 : 1)
     * 4. (1 < 4) is true. Result: 2 (P2 wins)
     */
    @ParameterizedTest(name = "P1 Weight {0} vs P2 Weight {1} should result in Winner {2}")
    @CsvSource({
            // P1 Invalid High Weight vs Valid P2 Weight
            "4, 1, 2",   // P1 (4) vs P2 (1). P2 wins (Unintended behavior)
            "100, 3, 2", // P1 (100) vs P2 (3). P2 wins (Unintended behavior)

            // P2 Invalid High Weight vs Valid P1 Weight
            "1, 4, 1",   // P1 (1) vs P2 (4). P1 wins (Unintended behavior)
            "3, 100, 1"  // P1 (3) vs P2 (100). P1 wins (Unintended behavior)
    })
    void getWinner_ShouldHandleInvalidHighWeights(int firstWeight, int secondWeight, int expectedWinner) {
        int actualWinner = GameShapes.getWinner(firstWeight, secondWeight);
        assertEquals(expectedWinner, actualWinner,
                "Expected winner based on existing logic for high weights.");
    }

    // --- Test for Edge Case Behavior (Invalid vs Invalid) ---

    /**
     * Test cases where both inputs are invalid.
     */
    @ParameterizedTest(name = "P1 Weight {0} vs P2 Weight {1} should result in Winner {2}")
    @CsvSource({
            // Both invalid, but equal (should result in Draw)
            "0, 0, 0",
            "4, 4, 0",
            "-5, -5, 0",

            // Both invalid, but unequal (P2 wins since P2's weight is lower)
            "10, 5, 2", // 5 < 10 is true -> P2 wins
            "5, 10, 1"  // 10 < 5 is false -> P1 wins
    })
    void getWinner_ShouldHandleBothInvalidWeights(int firstWeight, int secondWeight, int expectedWinner) {
        int actualWinner = GameShapes.getWinner(firstWeight, secondWeight);
        assertEquals(expectedWinner, actualWinner,
                "Expected winner based on existing logic for two invalid weights.");
    }
}
