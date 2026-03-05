package ru.yandex.practicum.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new WordleDictionary();
    }

    @Test
    public void addAll() {
        dictionary.addAll(List.of("слово", "гонец", "шляпа"));
        List<String> words = dictionary.getWords();
        assertEquals(3, words.size());
        assertTrue(words.contains("слово"));
        assertTrue(words.contains("гонец"));
        assertTrue(words.contains("шляпа"));
    }

    @Test
    public void addAll_multipleCalls() {
        dictionary.addAll(List.of("один", "два"));
        dictionary.addAll(List.of("три"));
        List<String> words = dictionary.getWords();
        assertEquals(3, words.size());
        assertTrue(words.contains("один"));
        assertTrue(words.contains("два"));
        assertTrue(words.contains("три"));
    }

    @Test
    public void getWords() {
        dictionary.addAll(List.of("гонец"));
        List<String> words = dictionary.getWords();
        assertNotNull(words);
        assertEquals(1, words.size());
        assertEquals("гонец", words.get(0));
    }

    @Test
    public void getWords_returnsSameListReference() {
        dictionary.addAll(List.of("слово"));
        List<String> first = dictionary.getWords();
        List<String> second = dictionary.getWords();
        assertSame(first, second, "getWords() возвращает один и тот же список");
    }
}