package ru.yandex.practicum.worldlegame;

import ru.yandex.practicum.dictionary.FilteredDictionary;
import ru.yandex.practicum.dictionary.WordleDictionaryHelper;

import java.io.PrintWriter;
import java.util.*;

public class WordleGameAutoCompleteWord {
    private static final int WORD_LENGTH = 5;

    private final FilteredDictionary filteredDictionary;
    private final PrintWriter logger;
    private final Random random = new Random();

    private final List<String> attemptedWords = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();
    private final List<String> candidateWords;

    public WordleGameAutoCompleteWord(FilteredDictionary filteredDictionary, PrintWriter logger) {
        this.filteredDictionary = filteredDictionary;
        this.logger = logger;
        this.candidateWords = new ArrayList<>(filteredDictionary.getWords());
    }

    private void updateCandidates() {
        logger.println("Начато обновление подходящих слов словаря");

        Set<Character> excludedLetters = new HashSet<>();
        Map<Character, Integer> minLetterCount = new HashMap<>();
        Map<Integer, Set<Character>> wrongPosition = new HashMap<>();
        char[] exactPosition = new char[WORD_LENGTH];

        for (int i = 0; i < WORD_LENGTH; i++) {
            wrongPosition.put(i, new HashSet<>());
        }

        for (int a = 0; a < attemptedWords.size(); a++) {
            String attemptedWord = attemptedWords.get(a);
            String hint = hints.get(a);
            Map<Character, Integer> attemptCount = new HashMap<>();

            for (int i = 0; i < WORD_LENGTH; i++) {
                char symbolAttemptedWord = attemptedWord.charAt(i);
                char symbolHint = hint.charAt(i);
                if (symbolHint == '-') {
                    excludedLetters.add(symbolAttemptedWord);
                } else if (symbolHint == '+') {
                    exactPosition[i] = symbolAttemptedWord;
                    int count = attemptCount.getOrDefault(symbolAttemptedWord, 0) + 1;
                    attemptCount.put(symbolAttemptedWord, count);
                } else if (symbolHint == '^') {
                    wrongPosition.get(i).add(symbolAttemptedWord);
                    int count = attemptCount.getOrDefault(symbolAttemptedWord, 0) + 1;
                    attemptCount.put(symbolAttemptedWord, count);
                }
            }

            for (Map.Entry<Character, Integer> e : attemptCount.entrySet()) {
                char letter = e.getKey();
                int count = e.getValue();
                int current = minLetterCount.getOrDefault(letter, 0);
                if (count > current) {
                    minLetterCount.put(letter, count);
                }
            }
        }

        candidateWords.removeIf(w ->
                containsAnyExcluded(w, excludedLetters)
                || !hasMinimumLetters(w, minLetterCount));

        logger.println("Закончено обновление подходящих слов словаря");
    }

    public void addAttempt(String word, String hint) {
        logger.println("Добавлена попытка");

        String normalizedWord = WordleDictionaryHelper.normalize(word);

        attemptedWords.add(normalizedWord);
        hints.add(hint);

        updateCandidates();
    }

    public String getHintWord() {
        logger.println("Получение подсказки");

        if (attemptedWords.isEmpty()) {
            return filteredDictionary.getRandomWord();
        }

        if (candidateWords.isEmpty()) {
            return filteredDictionary.getRandomWord();
        }

        return  candidateWords.get(random.nextInt(candidateWords.size()));
    }

    private boolean containsAnyExcluded(String word, Set<Character> excluded) {
        for (int i = 0; i < word.length(); i++) {
            if (excluded.contains(word.charAt(i))) return true;
        }
        return false;
    }

    private boolean hasMinimumLetters(String word, Map<Character, Integer> minCount) {
        for (Map.Entry<Character, Integer> e : minCount.entrySet()) {
            char letter = e.getKey();
            int need = e.getValue();
            int have = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) have++;
            }
            if (have < need) return false;
        }
        return true;
    }
}
