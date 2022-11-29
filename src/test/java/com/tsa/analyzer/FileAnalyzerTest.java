package com.tsa.analyzer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerTest {

    @Test
    void testClass() {
        FileAnalyzer.main(new String[]{"text.txt", "language"});

        assertEquals(15, FileAnalyzer.getCoincides().size());
    }

}
