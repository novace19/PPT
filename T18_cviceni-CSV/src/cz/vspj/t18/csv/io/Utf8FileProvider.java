package cz.vspj.t18.csv.io;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
/** Jediná odpovědnost: čtení UTF-8. Výjimky zachovají informace o souboru i příčině. */
public final class Utf8FileProvider implements LineProvider {
    @Override public String[] read(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8).toArray(String[]::new);
    }
}
