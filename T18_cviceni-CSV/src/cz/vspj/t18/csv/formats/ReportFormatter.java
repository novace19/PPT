package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.logic.*;
import cz.vspj.t18.csv.model.*;
import java.util.*;
/** Formát výsledných CSV přehledů; nepřistupuje k souborům. */
public final class ReportFormatter {
    public String students(SchoolService.Loaded data) {
        StringBuilder text=new StringBuilder("Třída;Číslo;Jméno;Příjmení;MAT;FYZ;IT;TV;CJ;Průměr;Prospěch\n");
        for(SchoolClass schoolClass:data.classes()) for(Student student:schoolClass.students()) {
            var grades=data.grades().classes().get(schoolClass.code()).get(student.number());
            text.append(schoolClass.code()).append(';').append(student.number()).append(';').append(student.firstName()).append(';').append(student.lastName());
            for(Subject subject:Subject.values()) text.append(';').append(grades.get(subject));
            text.append(';').append(decimal(Statistics.studentAverage(grades))).append(';').append(Statistics.result(grades)).append('\n');
        }
        return text.toString();
    }
    public String classes(SchoolService.Loaded data) {
        StringBuilder text=new StringBuilder("Třída;Počet studentů;MAT;FYZ;IT;TV;CJ\n");
        for(SchoolClass schoolClass:data.classes()) {
            text.append(schoolClass.code()).append(';').append(schoolClass.students().length);
            for(Subject subject:Subject.values()) text.append(';').append(decimal(Statistics.subjectAverage(schoolClass,data.grades(),subject)));
            text.append('\n');
        }
        return text.toString();
    }
    private String decimal(double value) { return Double.isNaN(value)?"—":String.format(Locale.ROOT,"%.2f",value); }
}
