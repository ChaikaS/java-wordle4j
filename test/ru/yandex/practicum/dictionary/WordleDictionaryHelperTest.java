package ru.yandex.practicum.dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class WordleDictionaryHelperTest {
    @Test
    void normalize_lowercase() {
        Assertions.assertEquals("слово", WordleDictionaryHelper.normalize("СлОвО"));
    }

    @Test
    void normalize_replacesYoWithE() {
        Assertions.assertEquals("мед", WordleDictionaryHelper.normalize("мёд"));
    }

    @Test
    void normalize_alreadyLowerAndNoYo() {
        Assertions.assertEquals("гонец", WordleDictionaryHelper.normalize("гонец"));
    }

    @Test
    void wordToCharacterArr_returnsListOfChars() {
        List<Character> expected = List.of('г', 'о', 'н', 'е', 'ц');
        Assertions.assertEquals(expected, WordleDictionaryHelper.wordToCharacterArr("гонец"));
    }

    @Test
    void wordToCharacterArr_normalizesFirst() {
        List<Character> expected = List.of('е', 'ж', 'и', 'к');
        Assertions.assertEquals(expected, WordleDictionaryHelper.wordToCharacterArr("ёжик"));
    }

    @Test
    void hasAnyLetters() {
        String generatedWord = "гонец";

        Assertions.assertTrue(WordleDictionaryHelper.hasAnyLetters(generatedWord, "абвг"));

        Assertions.assertFalse(WordleDictionaryHelper.hasAnyLetters(generatedWord, "абв"));
    }

    @Test
    void hasAllLetters() {
        String generatedWord = "гоноц";
        Assertions.assertTrue(WordleDictionaryHelper.hasAllLetters(generatedWord, "гоноц"));

        Assertions.assertFalse(WordleDictionaryHelper.hasAllLetters(generatedWord, "гонег"));
    }

    @Test
    void buildHintString_fullMatch() {
        String answer = "гонец";
        Assertions.assertEquals("+++++", WordleDictionaryHelper.buildHintString(answer, "гонец"));
    }

    @Test
    void buildHintString_noMatchingLetters() {
        String answer = "гонец";
        Assertions.assertEquals("-----", WordleDictionaryHelper.buildHintString(answer, "шляпа"));
    }

    @Test
    void buildHintString_letterInCorrectPosition() {
        String answer = "гонец";
        Assertions.assertEquals("+----", WordleDictionaryHelper.buildHintString(answer, "гxxxx"));
        Assertions.assertEquals("--+--", WordleDictionaryHelper.buildHintString(answer, "ххнхх"));
        Assertions.assertEquals("----+", WordleDictionaryHelper.buildHintString(answer, "ххххц"));
    }

}