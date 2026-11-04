package cz.vspj.t18.csv.formats;
import java.util.*;
/** Factory odděluje výběr formátu od orchestrace. Nové formáty lze registrovat (např. XML). */
public final class DataFormatFactory {
    private final Map<String,StudentFormat> students = new HashMap<>();
    private final Map<String,GradeFormat> grades = new HashMap<>();
    public DataFormatFactory() {
        String[] ids={"i","ii","iii"}; Layout[] layouts=Layout.values();
        for(int n=0;n<ids.length;n++) {
            registerStudents(ids[n],new CsvStudentFormat(layouts[n]));
            registerGrades(ids[n],new CsvGradeFormat(layouts[n],false));
            registerGrades(ids[n]+"-named",new CsvGradeFormat(layouts[n],true));
        }
    }
    public void registerStudents(String id, StudentFormat format) { students.put(id,Objects.requireNonNull(format)); }
    public void registerGrades(String id, GradeFormat format) { grades.put(id,Objects.requireNonNull(format)); }
    public StudentFormat students(String id) {
        StudentFormat format=students.get(id);
        if(format==null) throw new IllegalArgumentException("Neznámý formát seznamu: " + id);
        return format;
    }
    public GradeFormat grades(String id) {
        GradeFormat format=grades.get(id);
        if(format==null) throw new IllegalArgumentException("Neznámý formát známek: " + id);
        return format;
    }
}
