package dev.dhev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WcApplicationTest {
    private final WcApplication application = new WcApplication(new WcCounter(), new OutputFormatter());

    @TempDir
    Path tempDir;

    @Test
    void printsResultsAndTotalForMultipleFiles() throws IOException {
        Path first = writeFile("first.txt", "a b\n");
        Path second = writeFile("second.txt", "xyz\n");
        CliOptions options = CliOptions.parse(new String[] {first.toString(), second.toString()});

        String output = captureOutput(() -> application.run(options));

        assertEquals(
                """
                               1        2        4 %s
                               1        1        4 %s
                               2        3        8 total
                        """
                        .formatted(first, second)
                        .replace("\n", System.lineSeparator()),
                output);
    }

    private Path writeFile(String name, String content) throws IOException {
        Path file = tempDir.resolve(name);
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return file;
    }

    private String captureOutput(ThrowingRunnable runnable) throws IOException {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream replacement = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(replacement);
            runnable.run();
        } finally {
            System.setOut(originalOut);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws IOException;
    }
}
