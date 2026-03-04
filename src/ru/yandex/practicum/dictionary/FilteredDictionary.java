package ru.yandex.practicum.dictionary;

import ru.yandex.practicum.exception.DictionaryNotReadyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.yandex.practicum.Wordle.WORD_LENGTH;

public class FilteredDictionary {
    public WordleDictionary commonDictionary;
    private final List<String> filteredDictionary = new ArrayList<>();
    private final Random random;

    public FilteredDictionary(WordleDictionary commonDictionary) {
        this.commonDictionary = commonDictionary;
        this.random = new Random();
        addWordsToFilteredDictionary();
    }

    public void addWordsToFilteredDictionary() {
        for (String word : this.commonDictionary.getWords()) {
            String normalized = WordleDictionaryHelper.normalize(word);
            if (normalized.length() == WORD_LENGTH) {
                filteredDictionary.add(normalized);
            }
        }
    }

    public boolean isContainsWord(String word) {
        return filteredDictionary.contains(word);
    }

    public String getRandomWord() {
        if (filteredDictionary.isEmpty()) {
            throw new DictionaryNotReadyException("Словарь пуст, невозможно выбрать слово");
        }
        return filteredDictionary.get(random.nextInt(filteredDictionary.size()));
    }

    public List<String> getWords() {
        return new ArrayList<>(filteredDictionary);
    }
}
