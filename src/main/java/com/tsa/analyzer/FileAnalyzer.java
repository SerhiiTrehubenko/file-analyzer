package com.tsa.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FileAnalyzer {

    private final static Pattern pattern = Pattern.compile("[, .?!]");

    public FileStatistic processFile(File file, String wordToFind) {
        int numberOfCoincidence = 0;
        List<String> coincides = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        try (var inputFile = new FileInputStream(file)) {
            int readByte;

            while ((readByte = inputFile.read()) != -1) {

                if (!isCr(readByte) & !isLf(readByte)) {
                    stringBuilder.append((char) readByte);
                } else if (isLf(readByte)) {
                    stringBuilder.append(' ');
                }

                if (isSentenceSeparator(readByte)) {
                    numberOfCoincidence += getSentenceContainsWord(coincides, stringBuilder, wordToFind);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FileStatistic(coincides, wordToFind, numberOfCoincidence);
    }

    int getSentenceContainsWord(List<String> coincides,
                                StringBuilder stringBuilder,
                                String wordToFind) {

        String sentenceToString = stringBuilder.toString().trim();
        int numberOfCoincidence = checkWordOnCoincidence(sentenceToString, wordToFind);
        if (numberOfCoincidence > 0) {
            coincides.add(sentenceToString);
        }
        stringBuilder.delete(0, stringBuilder.length());
        return numberOfCoincidence;
    }

    int checkWordOnCoincidence(String sentence, String wordToFind) {
        int count = 0;
        String[] splitByWords = pattern.split(sentence);
        for (String splitByWord : splitByWords) {
            if (Objects.equals(splitByWord, wordToFind)) {
                count++;
            }
        }
        return count;
    }

    boolean isCr(int readByte) {
        return readByte == 13; // '\r'
    }

    boolean isLf(int readByte) {
        return readByte == 10; // '\n'
    }

    boolean isSentenceSeparator(int castedChar) {
        return castedChar == 46 | // '.'
                castedChar == 33 | // '!'
                castedChar == 63; // '?'
    }
}
