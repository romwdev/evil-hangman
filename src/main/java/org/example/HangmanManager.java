package org.example;

import java.util.*;

public class HangmanManager {
    private Set<String> words = new HashSet<>();
    private Set<Character> guesses;
    private int guessesLeft;
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


        return "";
    }

    public int record(char guess) {
        boolean wordsRemoved = false;
        for (String word : words) {
            if (word.indexOf(guess) > -1) {
                words.remove(word);
                wordsRemoved = true;
            }
        }
        return 0;
    }
}
