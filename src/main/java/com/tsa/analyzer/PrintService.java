package com.tsa.analyzer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PrintService {
    public static String toStringFileStatistics(FileStatistic fileStatistic) {
        return getTopOfMessage(fileStatistic) +
                getBulletListOfSentences(fileStatistic.getListOfSentences());

    }

    private static String getTopOfMessage(FileStatistic fileStatistic) {
        return "Word to find: " +
                fileStatistic.getWordToFind() +
                "\nNumber of word coincides: " +
                fileStatistic.getNumberOfCoincidence() +
                "\n\nNumber of sentences: " +
                fileStatistic.getListOfSentences().size() +
                "\n";
    }
     private static String getBulletListOfSentences(List<String> sentences) {
         AtomicInteger index = new AtomicInteger(1);

         return sentences.stream()
                 .map(sentence -> index.getAndIncrement() + ") " + sentence)
                 .collect(Collectors.joining("\n"));
     }
}
