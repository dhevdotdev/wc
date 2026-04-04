package dev.dhev;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

final class WcCounter {
    CountResult count(InputStream input, String label) throws IOException {
        byte[] bytes = input.readAllBytes();
        String text = new String(bytes, StandardCharsets.UTF_8);

        long lineCount = 0;
        long wordCount = 0;
        long charCount = 0;
        boolean inWord = false;

        for (int index = 0; index < text.length(); ) {
            int codePoint = text.codePointAt(index);
            index += Character.charCount(codePoint);
            charCount++;

            if (codePoint == '\n') {
                lineCount++;
            }

            if (Character.isWhitespace(codePoint)) {
                inWord = false;
                continue;
            }

            if (!inWord) {
                wordCount++;
                inWord = true;
            }
        }

        return new CountResult(lineCount, wordCount, bytes.length, charCount, label);
    }
}
