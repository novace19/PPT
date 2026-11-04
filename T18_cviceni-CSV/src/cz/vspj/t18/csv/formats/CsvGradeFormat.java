package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.logic.*;
import cz.vspj.t18.csv.model.*;
import java.util.*;
/** Známky 1–5: pevné pořadí nebo explicitní dvojice PŘEDMĚT:známka v libovolném pořadí. */
public final class CsvGradeFormat implements GradeFormat {
    private final Layout layout; private final boolean named;
    public CsvGradeFormat(Layout layout, boolean named) { this.layout=layout; this.named=named; }
    @Override public GradeBook parse(String[] lines) throws DataFormatException {
        LineCursor cursor = new LineCursor(lines);
        Map<String,Map<Integer,Map<Subject,Integer>>> classes = new LinkedHashMap<>();
        Set<String> names = new HashSet<>(); cursor.blanks();
        while(cursor.has()) {
            String code = cursor.read(); new ClassBuilder(code);
            if (!names.add(code.toLowerCase(Locale.ROOT))) throw cursor.error("Duplicitní třída: " + code);
            Map<Integer,Map<Subject,Integer>> grades = new LinkedHashMap<>();
            int count = layout == Layout.BLOCKS ? -1 : cursor.count();
            if(count == -1) {
                while(cursor.has() && !cursor.blank()) grades.put(grades.size()+1, readGrades(cursor));
            } else for(int n=1;n<=count;n++) grades.put(n,readGrades(cursor));
            classes.put(code,grades);cursor.blanks();
        }
        if(classes.isEmpty()) throw cursor.error("Soubor neobsahuje žádné hodnocení tříd.");
        return new GradeBook(classes);
    }
    private Map<Subject,Integer> readGrades(LineCursor cursor) throws DataFormatException {
        Subject[] subjects = Subject.values(); String[] fields;
        if(layout == Layout.LINES) {
            fields = new String[subjects.length];
            for(int i=0;i<fields.length;i++) fields[i]=cursor.read();
        } else fields=cursor.read().split(";",-1);
        if(fields.length!=subjects.length) throw cursor.error("Student musí mít právě pět známek.");
        Map<Subject,Integer> grades = new EnumMap<>(Subject.class);
        for(int i=0;i<fields.length;i++) {
            Subject subject = subjects[i]; String value = fields[i].strip();
            if(named) {
                String[] pair=value.split(":",-1);
                if(pair.length!=2) throw cursor.error("Očekávána dvojice PŘEDMĚT:známka.");
                try { subject=Subject.valueOf(pair[0].strip()); }
                catch(IllegalArgumentException e) { throw cursor.error("Neznámý předmět: " + pair[0]); }
                value=pair[1].strip();
            }
            int grade;
            try { grade=Integer.parseInt(value); }
            catch(NumberFormatException e) { throw cursor.error("Známka není celé číslo: " + value); }
            if(grade<1 || grade>5) throw cursor.error("Známka musí být 1 až 5.");
            if(grades.put(subject,grade)!=null) throw cursor.error("Duplicitní předmět: " + subject);
        }
        return grades;
    }
}
