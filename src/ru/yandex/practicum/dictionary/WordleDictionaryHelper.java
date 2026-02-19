package ru.yandex.practicum.dictionary;

import java.util.*;

import static ru.yandex.practicum.Wordle.WORD_LENGTH;

public class WordleDictionaryHelper {
    public WordleDictionaryHelper() {
    }

    public static String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }

    public static ArrayList<Character> wordToCharacterArr(String word) {
        ArrayList<Character> characters = new ArrayList<>();

        String normalized = normalize(word);
        for (int i = 0; i < normalized.length(); i++) {
            characters.add(normalized.charAt(i));
        }
        return characters;
    }

    public static boolean hasAnyLetters(String generatedWord, String userWord) {
        boolean result = false;

        for (int i = 0; i < generatedWord.length(); i++) {
            if (wordToCharacterArr(userWord).contains(generatedWord.charAt(i))) {
                result = true;
            }
        }

        return result;
    }

    public static boolean hasAllLetters(String generatedWord, String userWord) {
        return Objects.equals(generatedWord, userWord);
    }

    public static String buildHintString(String generatedWord, String userWord) {
        StringBuilder strBuilder = new StringBuilder(WORD_LENGTH);

        String normalizeGeneratedWord = normalize(generatedWord);
        String normalizeUserWord = normalize(userWord);

        for (int i = 0; i < WORD_LENGTH; i++) {
            char userChar = normalizeUserWord.charAt(i);
            char answerChar = normalizeGeneratedWord.charAt(i);
            if (userChar == answerChar) {
                strBuilder.append('+');
            } else if (normalizeGeneratedWord.indexOf(userChar) >= 0) {
                strBuilder.append('^');
            } else {
                strBuilder.append('-');
            }
        }

        return strBuilder.toString();
    }
}
