package ru.yandex.practicum.dictionary;

import ru.yandex.practicum.exception.DictionaryLoadException;
import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    PrintWriter logger;

    public WordleDictionaryLoader(PrintWriter logger) {
        this.logger = logger;
    }

    public WordleDictionary loadFromFile(String fileName) throws IOException, EmptyDictionaryException, DictionaryLoadException {
        logger.println(String.format("Начато чтение файла %s для загрузки словаря", fileName));
        WordleDictionary dictionary = new WordleDictionary();

        try {
            File dictionaryFile = getDictionaryFile(fileName);
            List<String> dictionaryWords = readFile(dictionaryFile);
            dictionary.addAll(dictionaryWords);
        } catch (FileNotFoundException e) {
            throw new DictionaryLoadException("Файл словаря не найден: " + fileName);
        }

        if (dictionary.getWords().isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст после загрузки из " + fileName);
        }

        logger.println(String.format("Словарь из файла %s загружен", fileName));
        return dictionary;
    }

    private File getDictionaryFile(String fileName) throws FileNotFoundException {
        logger.println(String.format("Получаем файл по имени %s", fileName));
        Path filePath = Paths.get(fileName);
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new FileNotFoundException(String.format("Файла с именем %s не существует", fileName));
        }

        logger.println(String.format("Файл по имени %s получен", fileName));
        return file;
    }

    private List<String> readFile(File dictionaryFile) throws IOException {
        logger.println(String.format("Начато чтение файла по имени %s", dictionaryFile));

        List<String> result = new ArrayList<>();

        try (FileReader fileReader = new FileReader(dictionaryFile, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while (reader.ready()) {
                String readWord = reader.readLine();
                result.add(readWord);
            }
            logger.println(String.format("Из файла по имени %s прочитано %d слов", dictionaryFile, result.size()));
        } catch (IOException exception) {
            logger.println(String.format("Произошла ошибка при чтении файла %s", dictionaryFile));
            throw exception;
        }

        logger.println(String.format("Закончено чтение файла %s", dictionaryFile));

        return result;
    }
}
