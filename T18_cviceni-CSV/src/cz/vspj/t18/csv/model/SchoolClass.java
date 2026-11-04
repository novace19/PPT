package cz.vspj.t18.csv.model;
/** Datová vrstva: seznam studentů je klasické pole s obrannými kopiemi. */
public record SchoolClass(String code, Student[] students) {
    public SchoolClass { students = students.clone(); }
    @Override public Student[] students() { return students.clone(); }
}
