package com.tsa.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {

    private static List<String> coincides;

    public static void main(String[] args) {

        Object[] parameters = checkParameters(args);
        String wordToFind = (String) parameters[1];
        File file = (File) parameters[0];

        coincides = new ArrayList<>();
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
        printResult(coincides);
    }

    static void getSentenceContainsWord(List<String> coincides,
                                        StringBuilder stringBuilder,
                                        String wordToFind) {

        String sentenceToString = stringBuilder.toString().trim();

        if (sentenceToString.contains(wordToFind)) {
            coincides.add(sentenceToString);
        }
        stringBuilder.delete(0, stringBuilder.length());
    }

    static List<String> getCoincides() {
        return coincides;
    }

    static boolean isCrlf(char castedChar) {
        return castedChar == 10 | castedChar == 13;
    }

    static boolean isSentenceSeparator(char castedChar) {
        return String.valueOf(castedChar).equals(".") |
                String.valueOf(castedChar).equals("!") |
                String.valueOf(castedChar).equals("?");
    }

    static void printResult(List<String> coincides) {
        System.out.println("Number of coincides: " + coincides.size());
        System.out.println();
        int count = 1;
        for (String coincide : coincides) {
            System.out.println(count + ") " + coincide);
            count++;
        }
    }

    static Object[] checkParameters(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("You should provide only TWO arguments:\n" +
                    " the first is a PATH to a file, the second is a WORD to find");
        }
        Object[] parameters = new Object[2];
        parameters[1] = args[1];
        File file = new File(args[0]);
        parameters[0] = file;
        if (!file.canExecute()) {
            throw new RuntimeException("there is a problem with the Path: " + file.getAbsolutePath());
        }
        return parameters;
    }
}
