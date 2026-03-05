package ru.yandex.practicum.worldlegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.dictionary.FilteredDictionary;
import ru.yandex.practicum.dictionary.WordleDictionary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameAutoCompleteWordTest {

    private FilteredDictionary filteredDictionary;
    private WordleGameAutoCompleteWord autoComplete;
    private PrintWriter logger;

    @BeforeEach
    public void setUp() {
        WordleDictionary common = new WordleDictionary();
        common.addAll(List.of("гонец", "шляпа", "дубки", "тесть", "слово"));
        filteredDictionary = new FilteredDictionary(common);
        logger = new PrintWriter(new StringWriter());
        autoComplete = new WordleGameAutoCompleteWord(filteredDictionary, logger);
    }

    @Test
    public void addAttempt() {
        assertDoesNotThrow(() -> autoComplete.addAttempt("гонец", "+++++"));
        assertDoesNotThrow(() -> autoComplete.addAttempt("шляпа", "-----"));
    }

    @Test
    public void getHintWord_noAttempts_returnsWordFromDictionary() {
        String hint = autoComplete.getHintWord();
        assertNotNull(hint);
        assertEquals(5, hint.length());
        assertTrue(filteredDictionary.getWords().contains(hint));
    }

    @Test
    public void getHintWord_afterAttempt_returnsValidWord() {
        autoComplete.addAttempt("слово", "-^--^");
        String hint = autoComplete.getHintWord();
        assertNotNull(hint);
        assertEquals(5, hint.length());
        assertTrue(filteredDictionary.getWords().contains(hint));
    }
}