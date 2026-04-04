package dev.dhev;

import java.util.ArrayList;
import java.util.List;

record CliOptions(boolean showLines, boolean showWords, boolean showBytes, boolean showChars, List<String> paths) {
    static CliOptions parse(String[] args) {
        boolean showLines = false;
        boolean showWords = false;
        boolean showBytes = false;
        boolean showChars = false;
        List<String> paths = new ArrayList<>();

        for (String arg : args) {
            if (!arg.startsWith("-") || "-".equals(arg)) {
                paths.add(arg);
                continue;
            }

            for (int index = 1; index < arg.length(); index++) {
                switch (arg.charAt(index)) {
                    case 'l' -> showLines = true;
                    case 'w' -> showWords = true;
                    case 'c' -> showBytes = true;
                    case 'm' -> showChars = true;
                    default -> throw new IllegalArgumentException("Unknown option: -" + arg.charAt(index));
                }
            }
        }

        if (!showLines && !showWords && !showBytes && !showChars) {
            showLines = true;
            showWords = true;
            showBytes = true;
        }

        return new CliOptions(showLines, showWords, showBytes, showChars, List.copyOf(paths));
    }
}
