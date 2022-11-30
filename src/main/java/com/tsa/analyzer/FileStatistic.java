package com.tsa.analyzer;

import java.util.List;

public class FileStatistic {

    private final List<String> listOfSentences;

    public FileStatistic(List<String> listOfSentences) {
        this.listOfSentences = listOfSentences;
    }

    public List<String> getListOfSentences() {
        return listOfSentences;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of coincides: ").append(listOfSentences.size());
        stringBuilder.append("\n\n");
        int index = 1;
        for (String sentence : listOfSentences) {
            stringBuilder.append(index).append(") ").append(sentence);
            stringBuilder.append("\n");
            index++;
        }
        return stringBuilder.toString();
    }
}
