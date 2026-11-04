package cz.vspj.t18.csv.logic;
import cz.vspj.t18.csv.model.*;
import java.util.Map;
/** Čisté výpočty bez parsování, exportu a uživatelského rozhraní. */
public final class Statistics {
    private Statistics() { }
    public static double studentAverage(Map<Subject,Integer> grades) {
        return grades.values().stream().mapToInt(Integer::intValue).average().orElseThrow();
    }
    public static String result(Map<Subject,Integer> grades) { return grades.containsValue(5)?"NEPROSPĚL":"PROSPĚL"; }
    public static double subjectAverage(SchoolClass schoolClass, GradeBook book, Subject subject) {
        return book.classes().get(schoolClass.code()).values().stream().mapToInt(g->g.get(subject)).average().orElse(Double.NaN);
    }
}
