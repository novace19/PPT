package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.logic.*;
import cz.vspj.t18.csv.model.*;
/** Čistý parser a serializér všech tří formátů seznamu studentů. */
public final class CsvStudentFormat implements StudentFormat {
    private final Layout layout;
    public CsvStudentFormat(Layout layout) { this.layout = layout; }
    @Override public SchoolClass[] parse(String[] lines) throws DataFormatException {
        LineCursor cursor = new LineCursor(lines); SchoolBuilder school = new SchoolBuilder();
        cursor.blanks();
        while (cursor.has()) {
            String code = cursor.read(); ClassBuilder builder = new ClassBuilder(code);
            int count = layout == Layout.BLOCKS ? -1 : cursor.count();
            if (count == -1) {
                while (cursor.has() && !cursor.blank()) addStudent(cursor,builder);
            } else {
                for(int i=0;i<count;i++) addStudent(cursor,builder);
            }
            school.add(builder.build()); cursor.blanks();
        }
        return school.build();
    }
    private void addStudent(LineCursor cursor, ClassBuilder builder) throws DataFormatException {
        if (layout == Layout.LINES) builder.add(cursor.read(),cursor.read());
        else {
            String[] fields = cursor.read().split(";", -1);
            if (fields.length != 2) throw cursor.error("Očekáváno jméno;příjmení, tedy dvě položky.");
            try { builder.add(fields[0],fields[1]); }
            catch (DataFormatException e) { throw cursor.error(e.getMessage()); }
        }
    }
    @Override public String serialize(SchoolClass schoolClass) {
        StringBuilder text = new StringBuilder(schoolClass.code()).append('\n');
        Student[] students = schoolClass.students();
        if (layout != Layout.BLOCKS) text.append(students.length).append('\n');
        for(Student student : students) {
            text.append(student.firstName()).append(layout == Layout.LINES ? '\n' : ';');
            text.append(student.lastName()).append('\n');
        }
        return text.toString();
    }
}
