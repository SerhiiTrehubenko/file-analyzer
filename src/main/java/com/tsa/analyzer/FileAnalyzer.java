package com.tsa.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileAnalyzer {

    private static List<String> coincides;

    public static void main(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException("You should provide only TWO arguments:\n" +
                    " the first is a PATH to a file, the second is a WORD to find");
        String wordToFind = args[1];
        Path path = Path.of(args[0]);
        File file = path.toFile();
        if (!file.canExecute()) throw new RuntimeException("there is a problem with the Path");

        coincides = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();

        try (var inputFile = new FileInputStream(file)) {
            int readByte;

            while ((readByte = inputFile.read()) != -1) {
                char castedChar = (char) readByte;

                if (isCrlf(castedChar)) {
                    characterList.add(castedChar);
                }

                if (isSentenceSeparator(castedChar)) {
                    StringBuilder sentence = new StringBuilder();
                    characterList.forEach(sentence::append);

                    String sentenceToString = sentence.toString().trim();

                    if (sentenceToString.contains(wordToFind)) {
                        coincides.add(sentenceToString);
                    }
                    characterList.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Number of coincides: " + coincides.size());
        System.out.println();
        int count = 1;
        for (String coincide : coincides) {
            System.out.println(count + ") " + coincide);
            count++;
        }
    }

    static List<String> getCoincides() {
        return coincides;
    }
    private static boolean isCrlf(char castedChar) {
        return castedChar != 10 & castedChar != 13;
    }

    private static boolean isSentenceSeparator(char castedChar) {
        return String.valueOf(castedChar).equals(".") |
                String.valueOf(castedChar).equals("!") |
                String.valueOf(castedChar).equals("?");
    }
}
