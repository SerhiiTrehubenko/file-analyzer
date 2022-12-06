package com.tsa.analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAnalyzer {

    private final static Pattern SENTENCE_SPLITERATOR = Pattern.compile("[, ]");
    private final static Pattern CONTENT_SPLITERATOR = Pattern.compile("[.?!]");
    private final static int BUFFER_SIZE = 8 * 1024;

    public FileStatistic processFile(String path, String wordToFind) {
        String content = readContent(path);
        List<String> sentences = getListOfSentences(content);
        List<String> filteredSentences = getListOfFilteredSentences(sentences, wordToFind);

        int numberOfCoincidence = countWord(filteredSentences, wordToFind);

        return new FileStatistic(filteredSentences, wordToFind, numberOfCoincidence);
    }

    String readContent(String path) {
        StringBuilder content = new StringBuilder();
        try (var input = new FileInputStream(path)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int readBytes;
            while ((readBytes = input.read(buffer)) != -1) {
                content.append(new String(buffer, 0, readBytes));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    List<String> getListOfSentences(String content) {
        return Stream.of(CONTENT_SPLITERATOR.split(content))
                .map(this::getSentenceWithoutCrLf)
                .collect(Collectors.toList());
    }

    List<String> getListOfFilteredSentences(List<String> sentences, String wordToFind) {
        return sentences.stream().map(sentence -> {
                    long coincides = Stream.of(SENTENCE_SPLITERATOR.split(sentence))
                            .filter(word -> Objects.equals(wordToFind, word)).count();
                    return coincides == 0 ? null : sentence.trim();
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    String getSentenceWithoutCrLf(String sentence) {

        StringBuilder sentenceWithoutCrLf = new StringBuilder();
        char[] chars = new char[sentence.length()];
        sentence.getChars(0, sentence.length(), chars, 0);
        for (char aChar : chars) {
            if (isLf(aChar)) {
                sentenceWithoutCrLf.append(' ');
            } else if (!isCr(aChar)) {
                sentenceWithoutCrLf.append(aChar);
            }
        }
        return sentenceWithoutCrLf.toString();
    }

    int countWord(List<String> sentences, String wordToFind) {
        return (int) sentences.stream()
                .flatMap(sentence -> Stream.of(SENTENCE_SPLITERATOR.split(sentence)))
                .filter(word -> Objects.equals(word, wordToFind)).count();

    }

    boolean isCr(char readByte) {
        return readByte == '\r';
    }

    boolean isLf(char readByte) {
        return readByte == '\n';
    }
}
