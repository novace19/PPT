package cz.vspj.t18.csv.io;
import java.io.IOException;
import java.nio.file.Path;
/** Provider zdroje dat, nahraditelný v testech bez souborového systému. */
@FunctionalInterface
public interface LineProvider { String[] read(Path path) throws IOException; }
