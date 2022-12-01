package com.tsa.analyzer;

import java.util.List;

public class FileStatistic {

    private final List<String> listOfSentences;

    private final int numberOfCoincidence;

    private final String wordToFind;

    public FileStatistic(List<String> listOfSentences, String wordToFind, int numberOfCoincidence) {
        this.listOfSentences = listOfSentences;
        this.wordToFind = wordToFind;
        this.numberOfCoincidence = numberOfCoincidence;
    }

    public List<String> getListOfSentences() {
        return listOfSentences;
    }

    public int getNumberOfCoincidence() {
        return numberOfCoincidence;
    }

    public String getWordToFind() {
        return wordToFind;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of word coincides: ")
                .append(numberOfCoincidence)
                .append("\n\n")
                .append("Number of sentences: ")
                .append(listOfSentences.size())
                .append("\n");
        int index = 1;
        for (String sentence : listOfSentences) {
            stringBuilder.append(index).append(") ").append(sentence)
                    .append("\n");
            index++;
        }
        return stringBuilder.toString();
    }
}
