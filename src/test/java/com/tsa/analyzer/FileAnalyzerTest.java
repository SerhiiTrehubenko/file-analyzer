package com.tsa.analyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.tsa.analyzer.PrintService.*;

public class FileAnalyzerTest {

    private static final String SENTENCES = "This is true of both spoken/written languages and programming languages." +
            " It’s often\r\nsubtle: A language gently guides you into certain modes of thought and away from\r\nothers." +
            " Java is particularly opinionated.";

    FileAnalyzer fileAnalyzer = new FileAnalyzer();

    @DisplayName("Test checkParameters(), IllegalArgumentException is thrown when" +
            " a quantity of parameters is less than two")
    @Test
    void testCheckParametersQuantityLessThenTwo() {
        String[] parameters = {""};

        assertThrows(IllegalArgumentException.class, () -> new ParametersAnalyzer(parameters));
    }

    @DisplayName("Test checkParameters(), IllegalArgumentException is thrown when" +
            " a quantity of parameters is bigger than two")
    @Test
    void testCheckParametersQuantityBiggerThenTwo() {
        String[] parameters = {"", "", ""};

        assertThrows(IllegalArgumentException.class, () -> new ParametersAnalyzer(parameters));
    }

    @DisplayName("Test checkParameters(), RuntimeException is thrown when" +
            " a path to file is not valid")
    @Test
    void testCheckParametersFilePathIsNotValid() {
        String[] parameters = {"", ""};

        assertThrows(RuntimeException.class, () -> new ParametersAnalyzer(parameters));
    }

    @DisplayName("Test checkParameters(), with eligible parameters")
    @Test
    void testCheckParametersWithEligibleParameters() {
        String path = Paths.get(Path.of("").toAbsolutePath().toString(), "src", "main", "resources", "text.txt").toString();
        String word = "language";
        String[] parameters = {path, word};

        var resultParameters = new ParametersAnalyzer(parameters);

        assertEquals(path, resultParameters.getPath());
        assertEquals(word, resultParameters.getWordToFind());
    }

    @DisplayName("Test isCrlf(), returns TRUE when char is '\r'")
    @Test
    void testisCrlfReturnsTrueWhenCr() {
        char chars = '\r';

        assertTrue(fileAnalyzer.isCr(chars));
    }

    @DisplayName("Test isLf(), returns TRUE when char is '\n'")
    @Test
    void testisCrlfReturnsTrueWhenLf() {
        char chars = '\n';

        assertTrue(fileAnalyzer.isLf(chars));
    }

    @DisplayName("Test isCrlf(), returns FALSE when char is not '\n', '\r'")
    @Test
    void testisCrlfReturnsFalseWhenNotCRLF() {
        char[] chars = {'a', ' '};

        assertFalse(fileAnalyzer.isCr(chars[0]));
        assertFalse(fileAnalyzer.isCr(chars[1]));
    }

    @DisplayName("Test getSentenceWithoutCrLf(), removes '\n' and '\r' from a sentence")
    @Test
    void testGetSentenceWithoutCrLf() {
        String sentenceWithCrLf = "This is true\nof both\nspoken/written\r languages and\nprogramming\r languages.";
        String expected = "This is true of both spoken/written languages and programming languages.";

        String sentenceWithoutCrLf = fileAnalyzer.getSentenceWithoutCrLf(sentenceWithCrLf);

        assertEquals(expected, sentenceWithoutCrLf);
    }

    @DisplayName("Test readContent(), reads content from file to String")
    @Test
    void testReadContent() {
        String path = Paths.get(Path.of("").toAbsolutePath().toString(), "src", "main", "resources", "textForContent.txt").toString();

        String content = fileAnalyzer.readContent(path);

        assertEquals(SENTENCES, content);
    }

    @DisplayName("Test getListOfSentences(), splits String to a List of Strings")
    @Test
    void testGetListOfSentences() {
        List<String> listOfSentence = fileAnalyzer.getListOfSentences(SENTENCES);

        assertEquals(3, listOfSentence.size());
    }

    @DisplayName("Test getListOfFilteredSentences(), filters List of Strings to a List of Strings that contain searched word")
    @Test
    void testGetListOfFilteredSentences() {
        String wordToFind = "thought";
        String expected = "It’s often subtle: A language gently guides you into certain modes of thought and away from" +
                " others";
        List<String> listOfSentence = fileAnalyzer.getListOfSentences(SENTENCES);

        List<String> filteredSentences = fileAnalyzer.getListOfFilteredSentences(listOfSentence, wordToFind);

        assertEquals(3, listOfSentence.size());
        assertEquals(expected, filteredSentences.get(0));
    }

    @DisplayName("Test countWord(List<String>, wordToFind), returns number of wordToFind coincides")
    @Test
    void testCountWord() {
        String wordToFind = "languages";
        List<String> listOfSentence = fileAnalyzer.getListOfSentences(SENTENCES);
        List<String> filteredSentences = fileAnalyzer.getListOfFilteredSentences(listOfSentence, wordToFind);

        int numberOfCoincides = fileAnalyzer.countWord(filteredSentences, wordToFind);

        assertEquals(2, numberOfCoincides);
    }

    @Test
    void integrationTest() {
        String expected = "Word to find: languages\n" +
                "Number of word coincides: 4\n" +
                "\n" +
                "Number of sentences: 3\n" +
                "1) This is true of both spoken/written languages and programming languages\n" +
                "2) Java is also responsible for pushing the industry forward in other ways; for example, most languages are now expected to include documentation markup syntax and a tool to produce HTML documentation\n" +
                "3) On the other hand, C++, VB, Perl, and other languages such as SmallTalk had some of their design efforts focused on the issue of complexity and as a result are remarkably successful in solving certain types of problems";
        String wordToFind = "languages";
        String path = Paths.get(Path.of("").toAbsolutePath().toString(), "src", "main", "resources", "text.txt").toString();
        var fileStatistic = fileAnalyzer.processFile(path, wordToFind);

        assertEquals(4, fileStatistic.getNumberOfCoincidence());
        assertEquals(expected, toStringFileStatistics(fileStatistic));
    }
}
