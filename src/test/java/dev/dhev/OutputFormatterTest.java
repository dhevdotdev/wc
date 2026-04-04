package dev.dhev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OutputFormatterTest {
    private final OutputFormatter formatter = new OutputFormatter();

    @Test
    void formatsDefaultColumnsWithLabel() {
        CliOptions options = CliOptions.parse(new String[] {"report.txt"});
        CountResult result = new CountResult(12, 34, 56, 78, "report.txt");

        assertEquals("      12       34       56 report.txt", formatter.format(result, options));
    }

    @Test
    void formatsSelectedColumnsOnly() {
        CliOptions options = CliOptions.parse(new String[] {"-m", "-l"});
        CountResult result = new CountResult(2, 3, 6, 5, null);

        assertEquals("       2        5", formatter.format(result, options));
    }
}
