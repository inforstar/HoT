package helpers;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static helpers.GameShapes.*;

public class OutputScreenText {

    private static final Random RANDOM = new Random();
    public static void MenuGameType() {
        List<String> shapeNames = Stream.of(values())
                .map(Enum::name)
                .collect(Collectors.toList());
        System.out.println("-------------------------------------------");
        System.out.println(" GAME OF " + String.join(" -- " , shapeNames));
        System.out.println("");
        System.out.println(" 1 : Player vs. Computer");
        System.out.println(" 2 : Computer 1 vs. Computer 2");
        System.out.println("");
        System.out.println(" 9 : >> End game & Exit ");
        System.out.println("-------------------------------------------");
    }

    public static void MakeYourChoice() {
        System.out.print("Make your choice: ");
    }

    public static void WelcomeScreen() {
        System.out.println("************");
        System.out.println("Welcome to the game!!!");
        System.out.println("************");
    }

    public static void GoodbyeScreen() {
        System.out.println("*********************");
        System.out.println("Goodbye & see you soon");
        System.out.println("********************");
    }

    public static void MakeYourMove(int round, int move) {
        System.out.print("(round " + round + ".)(move " + move + ".) >>> ");
        for (int i = 0; i < values().length; i++) {
            System.out.print("[" + (i + 1) + ":" + values()[i].name() + "] ");
        }
        System.out.print("[9:Exit Game]:");
    }

    public static void ComputerMove(int round, int move) {
        System.out.print("(round " + round + ".)(move " + move + ".) >>> ");
        System.out.print("[ENTER:Next Move] [9:Exit Game]:");
    }

    public static void ShowMyShape(String playerName, int shape) {
        System.out.println(playerName + " shape: " +
                (shape > 0 && shape <= values().length ? values()[shape-1].name() : "???"));
    }

    public static void PlayingPlayerVSComputer() {
        System.out.println("*********************");
        System.out.println("YOU (Player) are playing versus COMPUTER");
        System.out.println("There will be 3 rounds, every round has " + values().length + " moves.");
        System.out.println("In each move you can select one shape. Computer will also select a shape.");
        System.out.println("");
        System.out.println("Ready?  GO!!");
        System.out.println("********************");
    }

    public static void PlayingComputerVSComputer() {
        System.out.println("*********************");
        System.out.println("COMPUTER 1 is playing versus COMPUTER 2");
        System.out.println("There will be 3 rounds, every round has " + values().length + " moves.");
        System.out.println("In each move computers will select one shape.");
        System.out.println("");
        System.out.println("Ready?  GO!!");
        System.out.println("********************");
    }

    public static void GameTerminated() {
        System.out.println("*********************");
        System.out.println("YOU (Player) terminated the game.");
        String message = GAME_TERMINATION_QUOTES.get(RANDOM.nextInt(GAME_TERMINATION_QUOTES.size()));
        System.out.println(message);
        System.out.println();
    }


    public static void ShowRoundWinner(int round, List<String> players, int winner, List<Integer> scores, String pickedShapes) {
        System.out.println("* * * ROUND " + round + ". finished * * * * * * * * * * * * * *");
        System.out.println("WINNER: " + players.get(winner) + " | " + pickedShapes);
        ShowCurrentScore(players, scores);
        System.out.println("-----------------------");
        System.out.println();
    }

    private static void ShowCurrentScore(List<String> players, List<Integer> scores) {
        System.out.print(">> >> SCORE: ");
        for (int i = 0; i <= 2; i++) {
            System.out.print(
                    players.get(i) + ":" +
                    scores.get(i) + " ");
        }
        System.out.println();
    }

    public static void ShowGameWinner(List<String> players, List<Integer> scores) {
        System.out.println();
        System.out.println("* * * GAME COMPLETED  * * * * * * * * * * * * * *");
        ShowCurrentScore(players, scores);

        System.out.println("==== WE HAVE A " +
                (scores.get(1) > scores.get(2) ? "WINNER: " + players.get(1) :
                        scores.get(2) > scores.get(1) ? "WINNER: " + players.get(2) :
                                "DRAW!!") +
                " ====");

        System.out.println("Congratulations & Thank you for good game!");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println();
        System.out.println();
    }

    public static void OutputErrorScreen() {
        // List of 10 funny output messages

        String message = MESSAGES.get(RANDOM.nextInt(MESSAGES.size()));
        System.out.println("\n--- APPLICATION CRITICAL FAILURE ---");
        System.out.println(message);
        System.out.println("------------------------------------\n");
    }

    public static void WrongOptionSelected() {
        System.out.println("");
        System.out.println("WRONG option selected!");
        String message = INVALID_INPUT_QUOTES.get(RANDOM.nextInt(INVALID_INPUT_QUOTES.size()));
        System.out.println(message);
    }


    private static final List<String> MESSAGES = List.of(
            "Oopsie. My brain just took a coffee break. Buggar.",
            "A wizard spilled coffee on my circuit board. Buggar.",
            "I've made a terrible mistake. Don't look at my code. Buggar.",
            "System: Panic. User: Calm. Buggar.",
            "My internal unicorn tripped over a wire. Buggar.",
            "Well, this is embarrassing. I seem to have forgotten how to compute. Buggar.",
            "Error 404: Logic not found. Buggar.",
            "The server is a lie. Also, I crashed. Buggar.",
            "I tried to divide by zero just for fun. It wasn't fun. Buggar.",
            "Please insert coin to continue. Kidding. I just broke. Buggar."
    );

    // List of 15 funny quotes for INVALID USER INPUT (Data Validation Errors)
    private static final List<String> INVALID_INPUT_QUOTES = List.of(
            "That's not even a number, bless your heart.",
            "Input fail! Try using one of your actual fingers this time.",
            "Error: You've chosen violence (or at least, an invalid option).",
            "Sir, this is a menu, not a jazz solo. Stick to the notes.",
            "I appreciate the enthusiasm, but that key does nothing here.",
            "Did you perhaps mistake me for a conversational AI? Stick to the digits.",
            "We're looking for numbers here, not abstract concepts.",
            "My tiny brain can't process that. Please be kind.",
            "You entered the quantum realm of invalid data.",
            "A valiant effort, but totally wrong. Try again.",
            "I'm sorry, I only speak 0 through 9 right now.",
            "That input is currently on vacation. Please choose another.",
            "If 'X' was a valid choice, you'd be winning. But it's not.",
            "Close, but no cigar. And definitely no valid option.",
            "Consult your manual, specifically the part about 'following instructions'."
    );

    // List of 10 funny quotes for GAME TERMINATION (Player Exit)
    private static final List<String> GAME_TERMINATION_QUOTES = List.of(
            "Adios, amigo! The CPU is taking a nap now.",
            "Quitting? We were just getting warmed up! Bye.",
            "My digital feelings are hurt, but farewell!",
            "Game over, man. Game over. See you next time!",
            "System shutting down. Remember me in your dreams.",
            "I’ll be here, brooding and waiting. Until then, so long!",
            "Did you hear that? That was the sound of my process ending. Goodbye.",
            "Don't worry, your high score is safe... in my imagination.",
            "The game session has been terminated. Please tip your developer.",
            "Exit successful. Go enjoy the analog world for a while."
    );
}
