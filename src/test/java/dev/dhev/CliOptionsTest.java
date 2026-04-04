package dev.dhev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CliOptionsTest {
    @Test
    void defaultsToLinesWordsAndBytes() {
        CliOptions options = CliOptions.parse(new String[0]);

        assertTrue(options.showLines());
        assertTrue(options.showWords());
        assertTrue(options.showBytes());
        assertFalse(options.showChars());
        assertTrue(options.paths().isEmpty());
    }

    @Test
    void parsesCombinedFlagsAndPaths() {
        CliOptions options = CliOptions.parse(new String[] {"-lwm", "notes.txt", "draft.txt"});

        assertTrue(options.showLines());
        assertTrue(options.showWords());
        assertFalse(options.showBytes());
        assertTrue(options.showChars());
        assertEquals(2, options.paths().size());
        assertEquals("notes.txt", options.paths().getFirst());
        assertEquals("draft.txt", options.paths().get(1));
    }

    @Test
    void treatsSingleDashAsInputPath() {
        CliOptions options = CliOptions.parse(new String[] {"-"});

        assertEquals(1, options.paths().size());
        assertEquals("-", options.paths().getFirst());
    }

    @Test
    void rejectsUnknownFlags() {
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> CliOptions.parse(new String[] {"-x"}));

        assertEquals("Unknown option: -x", error.getMessage());
    }
}
