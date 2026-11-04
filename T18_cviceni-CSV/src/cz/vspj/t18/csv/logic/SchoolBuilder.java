package cz.vspj.t18.csv.logic;
import cz.vspj.t18.csv.model.*;
import java.util.*;
/** Builder sestaví libovolný počet tříd; kolize názvů kontroluje i bez rozlišení velikosti písmen. */
public final class SchoolBuilder {
    private SchoolClass[] classes = new SchoolClass[4];
    private int size;
    private final Set<String> names = new HashSet<>();
    public void add(SchoolClass schoolClass) throws DataFormatException {
        if (!names.add(schoolClass.code().toLowerCase(Locale.ROOT)))
            throw new DataFormatException("Duplicitní třída: " + schoolClass.code());
        if (size == classes.length) classes = Arrays.copyOf(classes, size * 2);
        classes[size++] = schoolClass;
    }
    public SchoolClass[] build() throws DataFormatException {
        if (size == 0) throw new DataFormatException("Soubor neobsahuje žádnou třídu.");
        return Arrays.copyOf(classes, size);
    }
}
