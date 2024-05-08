import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.*;

// Wordlist class to handle the text file (words.txt) to be used in the game. File should be located in root directory
public class WordList {

    // List to store the words from the file
    private List<String> words;

    // Constructor that reads the words from the file and stores them in the list
    public WordList(String filename) {

        // Initialize the list
        words = new ArrayList<>();
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Paths.get(filename));
            // Add each line (word) to the list
            for (String line : lines) {
                words.add(line.toLowerCase());
            }
        } catch (IOException e) {
            // Handle the exception that occur while reading the file
            System.out.println("Error reading file." + e.getMessage());
        }
    }

    // Method to return the list of words
    public List<String> giveWords() {

        return words;
    }

    // Method to return the minimum and maximum word lengths in the list
    public int[] getMinMaxWordLengths() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (String word : words) {
            int length = word.length();
            min = Math.min(min, length);
            max = Math.max(max, length);
        }

        return new int[] {min, max};
    }

    // Method to return a new WordList containing only words of a certain length
    public WordList theWordsOfLength(int length) {

        // List to store the selected words
        List<String> selectedWords = new ArrayList<>();
        // Iterate through all words
        for (String word : words) {
            // If the word has the desired length, add it to the list
            if (word.length() == length) {
                selectedWords.add(word.toLowerCase());
            }
        }
        // Create a temporary file with the selected words
        Path tempFile;
        try {
            tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, selectedWords);
        } catch (IOException e) {
            // Handle any exceptions that occur while creating the temporary file
            throw new RuntimeException("Error creating temporary file: " + e.getMessage());
        }

        // Return a new WordList created from the temporary file
        return new WordList(tempFile.toString());

    }

    // Method to check if there is a word in the list that matches given pattern
    public boolean hasMatchingPattern(String pattern) {
        for (String word : words) {
            if (word.length() == pattern.length()) {
                boolean match = true;
                for (int i = 0; i < word.length(); i++) {
                    if (pattern.charAt(i) != '_' && pattern.charAt(i) != word.charAt(i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to return a new WordList containing only words that match a certain pattern
    public WordList theWordsWithCharacters(String someString) {

        // List to store the matching words
        List<String> matchingWords = new ArrayList<>();
        // Iterate through all words
        for (String word : words) {
            // Check if the word is same length as the someString
            if (word.length() == someString.length()) {
                // Count the number of matching characters
                int matchCount = 0;
                for (int i = 0; i < word.length(); i++) {
                    if (someString.charAt(i) != '_' && someString.charAt(i) == word.charAt(i)) {
                        matchCount++;
                    }
                }
                // If there are at least 2 matching characters, add the word to the list
                if (matchCount >= 2) {
                    matchingWords.add(word);
                }
            }
        }
        // Create a temporary file with the matching words
        Path tempFile;
        try {
            tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, matchingWords);
        } catch (IOException e) {
            // Handle any exceptions that occur while creating the temporary file
            throw new RuntimeException("Error creating temporary file: " + e.getMessage());
        }

        // Return a new WordList created from the temporary file
        return new WordList(tempFile.toString());

    }

}
