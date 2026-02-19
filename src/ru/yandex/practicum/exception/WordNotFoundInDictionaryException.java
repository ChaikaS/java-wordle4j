package ru.yandex.practicum.exception;

public class WordNotFoundInDictionaryException extends Exception {

    public WordNotFoundInDictionaryException(String word) {
        super("Слово не найдено в словаре: " + word);
    }
}
