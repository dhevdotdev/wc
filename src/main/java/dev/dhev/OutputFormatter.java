package dev.dhev;

import java.util.ArrayList;
import java.util.List;

final class OutputFormatter {
    String format(CountResult result, CliOptions options) {
        List<String> columns = new ArrayList<>();

        if (options.showLines()) {
            columns.add(String.format("%8d", result.lines()));
        }
        if (options.showWords()) {
            columns.add(String.format("%8d", result.words()));
        }
        if (options.showBytes()) {
            columns.add(String.format("%8d", result.bytes()));
        }
        if (options.showChars()) {
            columns.add(String.format("%8d", result.chars()));
        }
        if (result.label() != null) {
            columns.add(result.label());
        }

        return String.join(" ", columns);
    }
}
