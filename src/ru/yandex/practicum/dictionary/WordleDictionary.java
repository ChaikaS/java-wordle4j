package ru.yandex.practicum.dictionary;

import java.util.*;

public class WordleDictionary {

    private final List<String> words = new ArrayList<>();

    public void addAll(Collection<String> addedWords) {
        this.words.addAll(addedWords);
    }

    public List<String> getWords() {
        return this.words;
    }
}
