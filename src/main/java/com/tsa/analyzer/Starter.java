package com.tsa.analyzer;

import java.io.File;

import static com.tsa.analyzer.Service.*;

public class Starter {


    public static void main(String[] args) {

        Object[] parameters = checkParameters(args);
        String wordToFind = (String) parameters[1];
        File file = (File) parameters[0];

        var fileAnalyzer = new FileAnalyzer();

        var result = fileAnalyzer.processFile(file, wordToFind);

        printResult(result);
    }

}
