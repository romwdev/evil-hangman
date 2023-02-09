package org.example;

import java.util.*;

public class HangmanManager {
    private int length;
    private Set<String> words = new HashSet<>();
    private Set<Character> guesses = new HashSet<>();
    private char currentGuess;
    private Map<String, Set<String>> possiblePatterns;
    private int guessesLeft;

    public HangmanManager (Collection<String> dictionary, int length, int max) {
        if (dictionary.size() < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        guessesLeft = max;
        this.length = length;

        for (String word : dictionary) {
            if (word.length() == length) {
                words.add(word);
            }
        }
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
        possiblePatterns = new HashMap<>();

        if (words.size() == 0) {
            throw new IllegalStateException();
        }
        for (String word : words) {
            char[] letters = word.toCharArray();
            StringBuilder currentPattern = new StringBuilder();
            for (char letter : letters) {
                if (letter == currentGuess) {
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

        String largestPattern = "";

        int size = 0;
        for (String key : possiblePatterns.keySet()) {
            Set<String> value = new HashSet<>(possiblePatterns.get(key));
            if (value.size() > size) {
                largestPattern = key;
                size = value.size();
            }
        }

        words = new HashSet<>(possiblePatterns.get(largestPattern));

        return largestPattern;
    }

    public int record(char guess) {
        currentGuess = guess;

        return 0;
    }
}
