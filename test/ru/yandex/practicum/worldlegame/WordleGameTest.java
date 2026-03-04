package ru.yandex.practicum.worldlegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.dictionary.FilteredDictionary;
import ru.yandex.practicum.dictionary.WordleDictionary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    private FilteredDictionary filteredDictionary;
    private PrintWriter logger;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        WordleDictionary common = new WordleDictionary();
        common.addAll(List.of("гонец", "шляпа", "слово"));
        filteredDictionary = new FilteredDictionary(common);
        logger = new PrintWriter(new StringWriter());
        game = new WordleGame(logger, filteredDictionary);
    }

    @Test
    void isValidAnswer_trueWhenFiveRussianLetters() {
        assertTrue(WordleGame.isValidAnswer("гонец"));
        assertTrue(WordleGame.isValidAnswer("Шляпа"));
    }

    @Test
    void isValidAnswer_falseWhenWrongLength() {
        assertFalse(WordleGame.isValidAnswer("гон"));
        assertFalse(WordleGame.isValidAnswer("гонецг"));
    }

    @Test
    void isContainsWordInWordleDictionary_falseWhenWordInDictionary() {
        assertFalse(game.isContainsWordInWordleDictionary("гонец"));
        assertFalse(game.isContainsWordInWordleDictionary("шляпа"));
        assertFalse(game.isContainsWordInWordleDictionary("Слово"));
    }

    @Test
    void isContainsWordInWordleDictionary_trueWhenWordNotInDictionary() {
        assertTrue(game.isContainsWordInWordleDictionary("нетта"));
        assertTrue(game.isContainsWordInWordleDictionary("абвгд"));
    }
}