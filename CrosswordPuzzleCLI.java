import java.util.Scanner;

public class CrosswordPuzzleCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CrosswordPuzzle puzzle = new CrosswordPuzzle();

        while (true) {
            System.out.println("\nCrossword Puzzle Generator Menu:");
            System.out.println("1. Insert a new word");
            System.out.println("2. Show the grid");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = getIntInput(scanner);
            if (choice == -1) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter a word to add: ");
                    String word = scanner.nextLine().trim().toUpperCase();
                    if (word.isEmpty() || !word.matches("^[A-Z]+$")) {
                        System.out.println("Invalid word. Please enter a non-empty, alphabetic word.");
                        continue;
                    }
                    if (!puzzle.addWord(word)) {
                        System.out.println("Failed to add the word. Either no space or no intersecting letters found.");
                    }
                    break;
                case 2:
                    puzzle.printPuzzle();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 3.");
            }
        }
    }

    // Utility method to safely get an integer input
    private static int getIntInput(Scanner scanner) {
        try {
            int input = Integer.parseInt(scanner.nextLine());
            if (input < 1 || input > 3) {
                return -1; // Indicate out-of-range error
            }
            return input;
        } catch (NumberFormatException e) {
            return -1; // Indicate parsing error
        }
    }
}

// Include CrosswordPuzzle and Coordinate classes as previously defined, with no changes required.

// Note: CrosswordPuzzle class should be adjusted according to your implementation, 
// including any methods like addWord, placeWord, placeNewWord, initializeBoard, and printPuzzle.
