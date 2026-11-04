package cz.vspj.t18.csv.logic;
import cz.vspj.t18.csv.formats.*;
import cz.vspj.t18.csv.io.*;
import cz.vspj.t18.csv.model.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
/** Aplikační služba propojí provider, factory a validaci vztahů mezi soubory. */
public final class SchoolService {
    private final LineProvider provider; private final DataFormatFactory factory;
    public SchoolService(LineProvider provider, DataFormatFactory factory) { this.provider=provider;this.factory=factory; }
    public record Loaded(SchoolClass[] classes, GradeBook grades, StudentFormat sourceFormat) {
        public Loaded { classes=classes.clone(); }
        @Override public SchoolClass[] classes() { return classes.clone(); }
    }
    public Loaded load(Path roster, String rosterFormat, Path grades, String gradeFormat) throws IOException,DataFormatException {
        StudentFormat format=factory.students(rosterFormat); GradeFormat gradeParser=factory.grades(gradeFormat);
        SchoolClass[] classes=format.parse(provider.read(roster)); GradeBook book=gradeParser.parse(provider.read(grades));
        Set<String> expected=new HashSet<>();
        for(SchoolClass schoolClass:classes) {
            expected.add(schoolClass.code()); var classGrades=book.classes().get(schoolClass.code());
            if(classGrades==null) throw new DataFormatException("Chybí známky třídy " + schoolClass.code());
            if(classGrades.size()!=schoolClass.students().length)
                throw new DataFormatException("Třída " + schoolClass.code()+": počet studentů a sad známek nesouhlasí.");
        }
        if(!book.classes().keySet().equals(expected)) throw new DataFormatException("Soubor známek obsahuje neznámou třídu.");
        return new Loaded(classes,book,format);
    }
}
