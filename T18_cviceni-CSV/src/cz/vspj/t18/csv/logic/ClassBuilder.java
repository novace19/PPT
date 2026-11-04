package cz.vspj.t18.csv.logic;
import cz.vspj.t18.csv.model.*;
import java.util.Arrays;
/** Builder přiděluje pořadová čísla a vytváří studenty výhradně v poli. */
public final class ClassBuilder {
    private final String code;
    private Student[] students = new Student[8];
    private int size;
    public ClassBuilder(String code) throws DataFormatException {
        if (!code.matches("[\\p{L}\\p{N}][\\p{L}\\p{N}_-]*") || code.matches("(?i)CON|PRN|AUX|NUL|COM[0-9]|LPT[0-9]"))
            throw new DataFormatException("Neplatná zkratka třídy: " + code);
        this.code = code;
    }
    public ClassBuilder add(String first, String last) throws DataFormatException {
        if (first.isBlank() || last.isBlank() || first.contains(";") || last.contains(";"))
            throw new DataFormatException("Jméno a příjmení musí být neprázdné a bez středníku.");
        if (size == students.length) students = Arrays.copyOf(students, size * 2);
        students[size] = new Student(size + 1, first.strip(), last.strip()); size++;
        return this;
    }
    public SchoolClass build() { return new SchoolClass(code, Arrays.copyOf(students, size)); }
}
