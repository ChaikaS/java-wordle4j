package ru.yandex.practicum.exception;

public class DictionaryNotReadyException extends RuntimeException {

    public DictionaryNotReadyException(String message) {
        super(message);
    }
}
