package feature.RockPaperScissors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RockPaperScissors implements Game {
    private static final String ratingPath = "src/feature/RockPaperScissors/rating.txt";

    public void startGame() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name:>");
        String name = scanner.nextLine().trim();
        int rating = getScore(name);
        System.out.println("Hello, " + name);

        List<String> options = setOptions();

        while (!exit) {
            String compChoice = options.get(new Random().nextInt(options.size()));
            System.out.print(">");
            String playerChoice = scanner.nextLine().trim().toLowerCase();

            switch (playerChoice) {
                case "!exit":
                    System.out.println("Bye!");
                    exit = true;
                    break;
                case "!rating":
                    System.out.println("Your rating: " + rating);
                    break;
                default:
                    if (!options.contains(playerChoice)) {
                        System.out.println("Invalid input: " + playerChoice);
                    } else {
                        rating = getWinner(playerChoice, compChoice, rating);
                    }
            }
        }
    }

    private int getWinner(String playerAnswer, String compAnswer, int rating) {
        List<String> options = getOptionsList();
        int playerIndex = options.indexOf(playerAnswer);
        int compIndex = options.indexOf(compAnswer);
        int half = options.size() / 2;

        if (playerIndex == compIndex) {
            System.out.println("There is a draw (" + compAnswer + ")");
            rating += 50;
        } else if (!(playerIndex < compIndex && compIndex - playerIndex <= half)) {
            System.out.println("Well done. The computer chose " + compAnswer + " and failed");
            rating += 100;
        } else {
            System.out.println("Sorry, but the computer chose " + compAnswer);
        }

        return rating;
    }

    private static int getScore(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ratingPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(" ");

                if(strings.length <= 1)
                    continue;

                if(strings[0].equalsIgnoreCase(name)) {
                    return Integer.parseInt(strings[1]);
                }
            }
        } catch (IOException e) {
            return 0;
        }
        return 0;
    }

    private List<String> getOptionsList() {
        return Arrays.asList("fire", "rock", "gun", "lightning", "devil", "dragon", "water", "air", "paper", "sponge",
                "wolf", "tree", "human", "snake", "scissors");
    }

    private List<String> setOptions() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write options >");
        String input = scanner.nextLine().trim().toLowerCase();
        List<String> options = new ArrayList<>(Arrays.asList(input.split(",\\s*|,")));

        List<String> fullList = getOptionsList();
        Iterator<String> iterator = options.iterator();
        while (iterator.hasNext()) {
            String option = iterator.next();
            if (!fullList.contains(option)) {
                System.out.printf("\nUnknown param: %s \nDefault options are set\n", option);
                iterator.remove();
            }
        }

        if (options.isEmpty()) {
            options = Arrays.asList("rock", "paper", "scissors");
        }
        return options;
    }
}
