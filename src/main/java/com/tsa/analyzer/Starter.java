package com.tsa.analyzer;

import static com.tsa.analyzer.PrintService.toStringFileStatistics;

public class Starter {


    public static void main(String[] args) {

        ParametersAnalyzer checkedParameters = new ParametersAnalyzer(args);


        FileStatistic result = new FileAnalyzer().processFile(checkedParameters.getPath(), checkedParameters.getWordToFind());

        System.out.println(toStringFileStatistics(result));
    }

}
