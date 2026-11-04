package cz.vspj.t18.csv.model;
import java.util.*;
/** Třída -> číslo studenta -> mapa předmětů a známek. */
public record GradeBook(Map<String, Map<Integer, Map<Subject,Integer>>> classes) {
    public GradeBook {
        Map<String, Map<Integer, Map<Subject,Integer>>> copy = new LinkedHashMap<>();
        classes.forEach((code, students) -> {
            Map<Integer, Map<Subject,Integer>> inner = new LinkedHashMap<>();
            students.forEach((number, grades) -> inner.put(number, Map.copyOf(grades)));
            copy.put(code, Collections.unmodifiableMap(inner));
        });
        classes = Collections.unmodifiableMap(copy);
    }
}
