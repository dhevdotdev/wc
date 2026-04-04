package dev.dhev;

import java.util.List;

record CountResult(long lines, long words, long bytes, long chars, String label) {
    static CountResult total(List<CountResult> results) {
        long lines = 0;
        long words = 0;
        long bytes = 0;
        long chars = 0;

        for (CountResult result : results) {
            lines += result.lines();
            words += result.words();
            bytes += result.bytes();
            chars += result.chars();
        }

        return new CountResult(lines, words, bytes, chars, "total");
    }
}
