package dev.dhev;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CliOptions options = CliOptions.parse(args);
            WcApplication application = new WcApplication(new WcCounter(), new OutputFormatter());
            application.run(options);
        } catch (IllegalArgumentException | IOException error) {
            System.err.println("wc: " + error.getMessage());
            System.exit(1);
        }
    }
}
