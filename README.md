# wc

A small Java implementation of the Unix `wc` command built for practice.

## Supported flags

- `-l` for line count
- `-w` for word count
- `-c` for byte count
- `-m` for character count

With no flags, the program prints lines, words, and bytes.

## Run

```bash
mvn test
./wc path/to/file
printf 'hello world\n' | ./wc
```

## Notes

- This is a learning project, not a byte-for-byte clone of GNU/BSD `wc`.
- The implementation currently reads each input fully into memory instead of streaming it.
- Character counting assumes UTF-8 input.
