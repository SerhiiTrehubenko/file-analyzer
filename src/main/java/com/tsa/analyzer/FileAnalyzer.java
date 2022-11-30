package com.tsa.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {

    public List<String> processFile(File file, String wordToFind) {

        List<String> coincides = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        try (var inputFile = new FileInputStream(file)) {
            int readByte;

            while ((readByte = inputFile.read()) != -1) {
                char castedChar = (char) readByte;

                if (!isCrlf(castedChar)) {
                    stringBuilder.append(castedChar);
                }

                if (isSentenceSeparator(castedChar)) {
                    getSentenceContainsWord(coincides, stringBuilder, wordToFind);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coincides;
    }

    void getSentenceContainsWord(List<String> coincides,
                                 StringBuilder stringBuilder,
                                 String wordToFind) {

        String sentenceToString = stringBuilder.toString().trim();

        if (sentenceToString.contains(wordToFind)) {
            coincides.add(sentenceToString);
        }
        stringBuilder.delete(0, stringBuilder.length());
    }

    boolean isCrlf(char castedChar) {
        return castedChar == 10 | castedChar == 13;
    }

    boolean isSentenceSeparator(char castedChar) {
        return String.valueOf(castedChar).equals(".") |
                String.valueOf(castedChar).equals("!") |
                String.valueOf(castedChar).equals("?");
    }
}
