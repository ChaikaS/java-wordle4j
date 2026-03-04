package ru.yandex.practicum.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilteredDictionaryTest {

    private WordleDictionary commonDictionary;
    private FilteredDictionary filteredDictionary;

    @BeforeEach
    void setUp() {
        commonDictionary = new WordleDictionary();
        commonDictionary.addAll(List.of(
                "слово",
                "гонец",
                "шляпа",
                "ёж",
                "абвгдейка"
        ));
        filteredDictionary = new FilteredDictionary(commonDictionary);
    }

    @Test
    void addWordsToFilteredDictionary() {
        // addWordsToFilteredDictionary вызывается в конструкторе — проверяем результат
        List<String> words = filteredDictionary.getWords();
        assertEquals(3, words.size(), "В отфильтрованном словаре только 5-буквенные слова");
        assertTrue(words.contains("слово"));
        assertTrue(words.contains("гонец"));
        assertTrue(words.contains("шляпа"));
        assertFalse(words.contains("еж"), "4-буквенное слово не должно попасть");
        assertFalse(words.contains("абвгдейка"), "8-буквенное слово не должно попасть");
    }

    @Test
    void isContainsWord() {
        assertTrue(filteredDictionary.isContainsWord("слово"));
        assertTrue(filteredDictionary.isContainsWord("гонец"));
        assertTrue(filteredDictionary.isContainsWord("шляпа"));
        assertFalse(filteredDictionary.isContainsWord("неттакого"));
        assertFalse(filteredDictionary.isContainsWord("еж"), "2-буквенное не в словаре");
    }

    @Test
    void getRandomWord() {
        String word = filteredDictionary.getRandomWord();
        assertNotNull(word);
        assertEquals(5, word.length(), "Случайное слово должно быть длиной 5");
        assertTrue(filteredDictionary.isContainsWord(word), "Слово должно быть из отфильтрованного словаря");
    }

    @Test
    void getWords() {
        List<String> words = filteredDictionary.getWords();
        assertEquals(3, words.size());
        assertTrue(words.contains("слово"));
        assertTrue(words.contains("гонец"));
        assertTrue(words.contains("шляпа"));
    }
}