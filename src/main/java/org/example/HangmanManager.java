package org.example;

import java.util.*;

public class HangmanManager {

    private Set<String> words = new HashSet<>();
    private final Set<Character> guesses = new HashSet<>();
    private Map<String, Set<String>> possiblePatterns;
    private int guessesLeft;
    private char currentGuess;
    private String currentPattern;

    public HangmanManager (Collection<String> dictionary, int length, int max) {
        if (dictionary.size() < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        guessesLeft = max;

        for (String word : dictionary) {
            if (word.length() == length) {
                words.add(word);
            }
        }
        currentPattern = "- ".repeat(Math.max(0, length)).trim();
    }
    public Set<String> words() {
        return words;
    }
    public int guessesLeft() {
        return guessesLeft;
    }
    public Set<Character> guesses() {
        return guesses;
    }

    public String pattern() {
        if (words.size() == 0) {
            throw new IllegalStateException();
        }
        return currentPattern;
    }

    public int record(char guess) {
        currentGuess = guess;
        guesses.add(guess);
        generatePatterns();

        if (!currentPattern.contains(Character.toString(guess))) {
            guessesLeft--;
        }
        return countLetters(currentPattern);
    }

    private String findLargestSet() {
        String largestPattern = "";
        int size = 0;
        for (String key : possiblePatterns.keySet()) {
            Set<String> value = new HashSet<>(possiblePatterns.get(key));
            if (value.size() > size) {
                largestPattern = key;
                size = value.size();
            }
        }
        return largestPattern;
    }

    private void generatePatterns() {
        possiblePatterns = new HashMap<>();
        for (String word : words) {
            char[] letters = word.toCharArray();
            StringBuilder currentPattern = new StringBuilder();
            for (char letter : letters) {
                if (guesses.contains(letter)) {
                    currentPattern.append(letter);
                } else {
                    currentPattern.append("-");
                }
                currentPattern.append(" ");
            }
            String key = currentPattern.toString().trim();
            if (possiblePatterns.containsKey(key)) {
                possiblePatterns.get(key).add(word);
            } else {
                possiblePatterns.put(key, new HashSet<>());
                possiblePatterns.get(key).add(word);
            }
        }

        String largestPattern = findLargestSet();

        words = new HashSet<>(possiblePatterns.get(largestPattern));

        currentPattern = largestPattern;
    }

    private int countLetters(String pattern) {
        int result = 0;
        for (char letter : pattern.toCharArray()) {
            if (letter == currentGuess) {
                result++;
            }
        }
        return result;
    }
}
