package com.tsa.analyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.tsa.analyzer.Service.*;

public class FileAnalyzerTest {

    FileAnalyzer fileAnalyzer = new FileAnalyzer();

    @DisplayName("Test checkParameters(), IllegalArgumentException is thrown when" +
            " a quantity of parameters is less than two")
    @Test
    void testCheckParametersQuantityLessThenTwo() {
        String[] parameters = {""};

        assertThrows(IllegalArgumentException.class, () -> checkParameters(parameters));
    }

    @DisplayName("Test checkParameters(), IllegalArgumentException is thrown when" +
            " a quantity of parameters is bigger than two")
    @Test
    void testCheckParametersQuantityBiggerThenTwo() {
        String[] parameters = {"", "", ""};

        assertThrows(IllegalArgumentException.class, () -> checkParameters(parameters));
    }

    @DisplayName("Test checkParameters(), RuntimeException is thrown when" +
            " a path to file is not valid")
    @Test
    void testCheckParametersFilePathIsNotValid() {
        String[] parameters = {"", ""};

        assertThrows(RuntimeException.class, () -> checkParameters(parameters));
    }

    @DisplayName("Test checkParameters(), with eligible parameters")
    @Test
    void testCheckParametersWithEligibleParameters() {
        String path = "F:\\0_CODING\\11_Anatol_java\\file-analyzer\\text.txt";
        String word = "language";
        String[] parameters = {path, word};

        Object[] result = checkParameters(parameters);

        assertEquals(path, ((File) result[0]).getAbsolutePath());
        assertEquals(word, result[1]);
    }

    @DisplayName("Test isSentenceSeparator(), returns TRUE when char is '.', '!', '?'")
    @Test
    void testIsSentenceSeparatorAccordingRequirements() {
        char[] chars = {'.', '!', '?'};

        assertTrue(fileAnalyzer.isSentenceSeparator(chars[0]));
        assertTrue(fileAnalyzer.isSentenceSeparator(chars[1]));
        assertTrue(fileAnalyzer.isSentenceSeparator(chars[2]));
    }

    @DisplayName("Test isSentenceSeparator(), returns FALSE when char is NOT '.', '!', '?'")
    @Test
    void testIsSentenceSeparatorRequirementsAreNotValid() {
        char[] chars = {'d', ' ', '\n'};

        assertFalse(fileAnalyzer.isSentenceSeparator(chars[0]));
        assertFalse(fileAnalyzer.isSentenceSeparator(chars[1]));
        assertFalse(fileAnalyzer.isSentenceSeparator(chars[2]));
    }

    @DisplayName("Test isCrlf(), returns TRUE when char is '\n', '\r'")
    @Test
    void testisCrlfReturnsTrueWhenCRLF() {
        char[] chars = {'\r', '\n'};

        assertTrue(fileAnalyzer.isCrlf(chars[0]));
        assertTrue(fileAnalyzer.isCrlf(chars[1]));
    }

    @DisplayName("Test isCrlf(), returns FALSE when char is not '\n', '\r'")
    @Test
    void testisCrlfReturnsFalseWhenNotCRLF() {
        char[] chars = {'a', ' '};

        assertFalse(fileAnalyzer.isCrlf(chars[0]));
        assertFalse(fileAnalyzer.isCrlf(chars[1]));
    }

    @DisplayName("Test getSentenceContainsWord(), <Word is present> fill List with a sentence when the sentence contains needed word")
    @Test
    void testGetSentenceContainsWordWordIsPresent() {
        String word = "language";
        String text = "This is true of both spoken/written languages and programming languages.";
        List<String> coincides = new ArrayList<>();
        var stringBuilder = new StringBuilder();
        stringBuilder.append(text);

        fileAnalyzer.getSentenceContainsWord(coincides, stringBuilder, word);

        assertEquals(1, coincides.size());
        assertEquals(text, coincides.get(0));
    }

    @DisplayName("Test getSentenceContainsWord(), <Word is not present> fill List with a sentence when the sentence contains needed word")
    @Test
    void testGetSentenceContainsWordWordIsNotPresent() {
        String word = "language";
        String text = "Requiring that everything be\n" +
                "an object (especially all the way down to the lowest level) is a design mistake, but\n" +
                "banning objects altogether seems equally draconian.";

        List<String> coincides = new ArrayList<>();
        var stringBuilder = new StringBuilder();
        stringBuilder.append(text);

        fileAnalyzer.getSentenceContainsWord(coincides, stringBuilder, word);

        assertEquals(0, coincides.size());
    }

    @Test
    void testPrintResult() {
        List<String> list = new ArrayList<>();
        list.add("Requiring that everything be\n" +
                "an object (especially all the way down to the lowest level) is a design mistake, but\n" +
                "banning objects altogether seems equally draconian.");

        printResult(list);
    }

    @Test
    void testProcessFile() {

        var list = fileAnalyzer.processFile(new File("text.txt"), "language");

        assertEquals(15, list.size());
    }

    @Test
    void testFileStatistic() {
        var list = fileAnalyzer.processFile(new File("text.txt"), "language");
        var fileStatistic = new FileStatistic(list);

        System.out.println(fileStatistic);
    }
}
