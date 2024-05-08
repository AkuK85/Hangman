import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        WordList wordlist = new WordList("words.txt");
        int gameMode = 0;
        int invalidInputCount = 0; // Add a counter for invalid inputs

        // Loop until the user selects a valid game mode or enters too many invalid inputs
        while ((gameMode < 1 || gameMode > 4) && invalidInputCount < 10) { // Add a limit to the number of invalid inputs
            // Display the game mode options
            System.out.println("Choose a game mode you want to play" + "\n");
            System.out.println("1. Guess a random word");
            System.out.println("2. Guess a word of a certain length");
            System.out.println("3. Guess a word with specific characters");
            System.out.println("4. Exit" + "\n");
            System.out.print("Enter a number: ");

            // Check if the next input is an integer
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                if (scanner.hasNext()) {
                    scanner.next(); // discard the invalid input
                    invalidInputCount++; // increment the counter
                } else {
                    return; // exit the program if there is no more input
                }
            }
            gameMode = scanner.nextInt();

            // Handle the game mode selected by the user
            if (gameMode == 2) {
                int[] minMax = wordlist.getMinMaxWordLengths();
                System.out.println("Enter the length of the word (between " + minMax[0] + " and " + minMax[1] + "): ");
                int length = scanner.nextInt();
                while (length < minMax[0] || length > minMax[1]) {
                    System.out.println("Invalid length. Please enter a number between " + minMax[0] + " and " + minMax[1] + ".");
                    length = scanner.nextInt();
                }
                wordlist = wordlist.theWordsOfLength(length);
            } else if (gameMode == 3) {
                System.out.print("Enter the word pattern with underscores for characters you want to be masked: ");
                String pattern = scanner.next();
                while (!wordlist.hasMatchingPattern(pattern)) {
                    System.out.println("No word matches this pattern. Please enter a new pattern.");
                    pattern = scanner.next();
                }
                wordlist = wordlist.theWordsWithCharacters(pattern);
            } else if (gameMode == 4) {
                return;
            }
            else if (gameMode < 1 || gameMode > 4) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        }

        // Check if the user entered too many invalid inputs
        if (invalidInputCount >= 10) {
            System.out.println("Too many invalid inputs. Exiting the program.");
            return;
        }


        // Create a new Hangman game with the selected wordlist and 5 guesses
        Hangman hangman = new Hangman(wordlist, 10);

        // Loop until the game is over
        while (!hangman.theEnd()) {
            System.out.println("The hidden word");
            StringBuilder maskedWord = new StringBuilder();
            for (char c : hangman.getCurrentGuess().toCharArray()) {
                if (hangman.guesses().contains(c)) {
                    maskedWord.append(c).append(" ");
                } else {
                    maskedWord.append("* ");
                }
            }
            System.out.println(maskedWord);
            System.out.println("Guesses left: " + hangman.guessesLeft());
            System.out.println("Guessed letters: " + hangman.guesses());

            System.out.print("Guess a letter: ");
            char guess = scanner.next().toLowerCase().charAt(0);

            if (hangman.guesses().contains(guess)) {
                System.out.println("You have already guessed this letter. Try another one.");
                continue;
            }

            if (!hangman.guess(guess)) {
                System.out.println("Wrong guess!");
            }
        }

        // Check if the game was won or lost and display the correct word
        if (hangman.getCurrentGuess().equals(hangman.word())) {
            System.out.println("Congratulations! You won!!!");
            System.out.println("The hidden word was: " + hangman.word());
        } else {
            System.out.println("Sorry you lost!");
            System.out.println("The hidden word was: " + hangman.word());
        }
    }
}
