import java.util.List;
import java.util.ArrayList;
import java.util.Random;

// keeps track of the situation(logic) of the game.
public class Hangman {

    private String word;
    private List<Character> guesses;
    private int remainingGuesses;
    private String currentGuess;


    // Constructor for the Hangman class
    public Hangman(WordList wordlist, int guesses) {
        Random random = new Random();
        List<String> words = wordlist.giveWords();
        this.word = words.get(random.nextInt(words.size()));
        this.guesses = new ArrayList<>();
        this.remainingGuesses = guesses;
        this.currentGuess = String.valueOf('*').repeat(this.word.length());
    }

    // Method to handle the user's guess
    public boolean guess(Character c) {
        // Convert the character to lowercase to handle case insensitivity
        c = Character.toLowerCase(c);

        // Check if the character has already been guessed
        if (guesses.contains(c)) {
            return false;
        }

        // Add the character to the list of guesses
        guesses.add(c);

        // Check if the word contains the guessed character
        if (word.toLowerCase().contains(c.toString())) {
            // If the word contains the guessed character, update the current guess
            for (int i = 0; i < word.length(); i++) {
                if (Character.toLowerCase(word.charAt(i)) == c) {
                    currentGuess = currentGuess.substring(0, i) + word.charAt(i) + currentGuess.substring(i + 1);
                }
            }
            return true;
        } else {
            // If the guessed character is not in the word, reduce the number of remaining guesses
            remainingGuesses--;
            return false;
        }
    }

    // Method to return the current guess
    public String getCurrentGuess() {
        return this.currentGuess;
    }

    // Method to return the list of guesses
    public List<Character> guesses() {
        return guesses;
    }

    // Method to return the number of guesses left
    public int guessesLeft() {
        if (remainingGuesses < 0) {
            return 0;
        } else {
            return remainingGuesses;
        }
    }

    // Method to return the word
    public String word() {
        return this.word;
    }

    // Method to check if the game is over
    public boolean theEnd() {
        return currentGuess.equals(word) || remainingGuesses == 0;
    }

}
