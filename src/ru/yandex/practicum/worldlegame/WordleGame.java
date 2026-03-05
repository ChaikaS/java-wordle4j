package ru.yandex.practicum.worldlegame;

import ru.yandex.practicum.dictionary.FilteredDictionary;
import ru.yandex.practicum.dictionary.WordleDictionaryHelper;
import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.Scanner;

import static ru.yandex.practicum.Wordle.MAX_STEPS_COUNT;
import static ru.yandex.practicum.Wordle.WORD_LENGTH;


public class WordleGame {
    public static final Scanner scanner = new Scanner(System.in);
    private final String programAnswer;
    private int steps = 1;

    private FilteredDictionary filteredDictionary;
    private WordleGameAutoCompleteWord autoComplete;

    public WordleGame(PrintWriter logger, FilteredDictionary filteredDictionary) {
        this.filteredDictionary = filteredDictionary;
        this.programAnswer = filteredDictionary.getRandomWord();
        this.autoComplete = new WordleGameAutoCompleteWord(filteredDictionary, logger);
    }

    public void start() {
        while (this.steps <= MAX_STEPS_COUNT) {
            System.out.println(String.format("Попытка №%s. Загадайте ваше слово. Помните, максимальная длина слова 5 букв", this.steps));

            String answer = scanner.nextLine();

            if (answer.isBlank()) {
                String autoCompleteAnswer = autoComplete.getHintWord();

                System.out.println("Подсказка: " + autoCompleteAnswer);
            }

            if (WordleGame.isValidAnswerLength(answer)) {
                System.out.println("Нужно ввести ровно 5 букв");
                continue;
            }
            if (!WordleGame.isValidAnswer(answer)) {
                System.out.println("Разрешены только русские буквы");
                continue;
            }
            if (isContainsWordInWordleDictionary(answer)) {
                System.out.println(new WordNotFoundInDictionaryException(WordleDictionaryHelper.normalize(answer)).getMessage());
                continue;
            }

            if (WordleDictionaryHelper.hasAllLetters(this.programAnswer, answer)) {
                System.out.println("Поздравляем, вы угадали слово!");
                break;
            }

            String hint = WordleDictionaryHelper.buildHintString(this.programAnswer, answer);
            autoComplete.addAttempt(answer, hint);

            this.steps += 1;

            if (steps > MAX_STEPS_COUNT) {
                System.out.println("Попытки закончились. Попробуйте еще раз");
                break;
            }
        }
    }

    public static boolean isValidAnswer(String word) {
        return word != null && word.matches("[а-яА-ЯёЁ]{" + WORD_LENGTH + "}");
    }

    public static boolean isValidAnswerLength(String word) {
        return word.length() != WORD_LENGTH;
    }

    public boolean isContainsWordInWordleDictionary(String word) {
        return !this.filteredDictionary.isContainsWord(WordleDictionaryHelper.normalize(word));
    }
}
