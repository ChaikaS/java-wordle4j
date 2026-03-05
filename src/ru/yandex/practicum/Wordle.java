package ru.yandex.practicum;

import ru.yandex.practicum.dictionary.FilteredDictionary;
import ru.yandex.practicum.dictionary.WordleDictionary;
import ru.yandex.practicum.dictionary.WordleDictionaryLoader;
import ru.yandex.practicum.worldlegame.WordleGame;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Wordle {

    public static final String WORLD_FILE_NAME = "src/resources/words_ru.txt";
    public static final String LOG_FILE_NAME = "src/resources/log.txt";
    public static final int MAX_STEPS_COUNT = 6;
    public static final int WORD_LENGTH = 5;

    public static void main(String[] args) {
        try (FileOutputStream fos = new FileOutputStream(LOG_FILE_NAME);
             Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

            PrintWriter logger = new PrintWriter(writer, true);

            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary commonDictionary = loader.loadFromFile(WORLD_FILE_NAME);
            FilteredDictionary filteredDictionary = new FilteredDictionary(commonDictionary);

            WordleGame game = new WordleGame(logger, filteredDictionary);

            System.out.println("Вы зашли в игру Wordle на языке Java");
            System.out.println("Программа выбрала слово — существительное в единственном числе в именительном падеже. В нем 5 букв. У вас 6 попыток, чтобы отгадать слово");
            System.out.println();
            System.out.println(" - им отмечается буква, которой НЕТ в загаданном слове");
            System.out.println(" + этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции");
            System.out.println(" ^ так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте");
            System.out.println();
            System.out.println("Игра начинается! Слово загадано!");

            game.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
