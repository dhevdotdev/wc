package dev.dhev;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class WcApplication {
    private final WcCounter counter;
    private final OutputFormatter formatter;

    WcApplication(WcCounter counter, OutputFormatter formatter) {
        this.counter = counter;
        this.formatter = formatter;
    }

    void run(CliOptions options) throws IOException {
        List<CountResult> results = countInputs(options);

        for (CountResult result : results) {
            System.out.println(formatter.format(result, options));
        }

        if (results.size() > 1) {
            CountResult total = CountResult.total(results);
            System.out.println(formatter.format(total, options));
        }
    }

    private List<CountResult> countInputs(CliOptions options) throws IOException {
        List<CountResult> results = new ArrayList<>();

        if (options.paths().isEmpty()) {
            results.add(counter.count(System.in, null));
            return results;
        }

        for (String rawPath : options.paths()) {
            results.add(countInput(rawPath));
        }

        return results;
    }

    private CountResult countInput(String rawPath) throws IOException {
        if ("-".equals(rawPath)) {
            return counter.count(System.in, rawPath);
        }

        Path path = Path.of(rawPath);
        try (InputStream input = Files.newInputStream(path)) {
            return counter.count(input, rawPath);
        }
    }
}
