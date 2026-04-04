package dev.dhev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class WcCounterTest {
    private final WcCounter counter = new WcCounter();

    @Test
    void countsLinesWordsBytesAndChars() throws IOException {
        CountResult result = count("one two\nthree\n", "sample.txt");

        assertEquals(2, result.lines());
        assertEquals(3, result.words());
        assertEquals(14, result.bytes());
        assertEquals(14, result.chars());
        assertEquals("sample.txt", result.label());
    }

    @Test
    void countsUnicodeCharactersSeparatelyFromBytes() throws IOException {
        CountResult result = count("A🙂\n", "unicode.txt");

        assertEquals(1, result.lines());
        assertEquals(1, result.words());
        assertEquals(6, result.bytes());
        assertEquals(3, result.chars());
        assertEquals("unicode.txt", result.label());
    }

    private CountResult count(String text, String label) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return counter.count(new ByteArrayInputStream(bytes), label);
    }
}
