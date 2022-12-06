package com.tsa.analyzer;

import java.io.File;

public class ParametersAnalyzer {
    private final String path;
    private final String wordToFind;

    public ParametersAnalyzer(String[] args) throws IllegalArgumentException {
        if (args.length != 2) {
            throw new IllegalArgumentException("You should provide only TWO arguments:\n" +
                    " the first is a PATH to a file, the second is a WORD to find");
        }
        String path = args[0];
        if (!new File(path).canExecute()) {
            throw new RuntimeException("there is a problem with the Path: " + path);
        }
        this.path = path;
        wordToFind = args[1];
    }

    public String getPath() {
        return path;
    }

    public String getWordToFind() {
        return wordToFind;
    }
}
