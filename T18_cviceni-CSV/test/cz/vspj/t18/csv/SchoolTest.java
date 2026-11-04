package cz.vspj.t18.csv;
import cz.vspj.t18.csv.formats.*;
import cz.vspj.t18.csv.io.*;
import cz.vspj.t18.csv.logic.*;
import cz.vspj.t18.csv.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {
    @TempDir Path temp;
    private final DataFormatFactory factory=new DataFormatFactory();
    private SchoolService service() { return new SchoolService(new Utf8FileProvider(),factory); }
    private SchoolService.Loaded load(String students,String grades) throws Exception {
        return service().load(Path.of("data/studenti-"+students+".csv"),students,Path.of("data/znamky-"+grades+".csv"),grades);
    }
    static Stream<Arguments> combinations() {
        return Stream.of("i","ii","iii").flatMap(s -> Stream.of("i","ii","iii","i-named","ii-named","iii-named").map(g->Arguments.of(s,g)));
    }
    @ParameterizedTest @MethodSource("combinations")
    void allFormatsAndMixedLayouts(String students,String grades) throws Exception {
        var data=load(students,grades);
        assertEquals(2,data.classes().length);
        assertEquals("7A",data.classes()[0].code());
        assertEquals("Anna Marie",data.classes()[0].students()[0].firstName());
        assertEquals("Van der Berg",data.classes()[0].students()[1].lastName());
        assertEquals(2,data.classes()[0].students()[1].number());
        assertEquals(1,data.classes()[1].students()[0].number());
        var first=data.grades().classes().get("7A").get(1);
        assertEquals(Map.of(Subject.MAT,1,Subject.FYZ,2,Subject.IT,1,Subject.TV,3,Subject.CJ,1),first);
        assertEquals(1.6,Statistics.studentAverage(first),1e-10);
        assertEquals("PROSPĚL",Statistics.result(first));
        assertEquals("NEPROSPĚL",Statistics.result(data.grades().classes().get("7A").get(2)));
        assertEquals(3.0,Statistics.subjectAverage(data.classes()[0],data.grades(),Subject.MAT),1e-10);
    }
    @ParameterizedTest @ValueSource(strings={"i","ii","iii"})
    void exportPreservesSourceFormatAndReports(String format) throws Exception {
        var data=load(format,"i"); Path output=temp.resolve(format);
        new SchoolExporter(new ReportFormatter()).export(data,output);
        String roster=Files.readString(output.resolve("7A.csv"));
        assertEquals(factory.students(format).serialize(data.classes()[0]),roster);
        var parsed=factory.students(format).parse(roster.lines().toArray(String[]::new));
        assertEquals(1,parsed.length); assertArrayEquals(data.classes()[0].students(),parsed[0].students());
        assertTrue(Files.exists(output.resolve("7B.csv")));
        String students=Files.readString(output.resolve("prehledy/studenti.csv"));
        assertTrue(students.contains("7A;1;Anna Marie;Nováková;1;2;1;3;1;1.60;PROSPĚL"));
        assertTrue(students.contains("7A;2;Jan;Van der Berg;5;3;2;1;2;2.60;NEPROSPĚL"));
        assertTrue(Files.readString(output.resolve("prehledy/tridy.csv")).contains("7A;2;3.00;2.50;1.50;2.00;1.50"));
    }
    static Stream<Arguments> badRosters() {
        return Stream.of(
          Arguments.of("i",""),Arguments.of("ii",""),Arguments.of("iii",""),
          Arguments.of("i","7A\nJan;\n"),Arguments.of("i","7A\nJan;Novák;Navíc\n"),
          Arguments.of("i","7A\nJan;Novák\n\n7a\nPetr;Nový\n"),
          Arguments.of("ii","7A\n2\nJan;Novák\n"),Arguments.of("ii","7A\n-1\n"),
          Arguments.of("ii","7A\nx\n"),Arguments.of("iii","7A\n1\nJan\n"),
          Arguments.of("iii","7A\n1\nJan\n\n"),Arguments.of("i","../escape\nJan;Novák\n"),
          Arguments.of("i","CON\nJan;Novák\n"),Arguments.of("ii","7A\n2147483648\n"));
    }
    @ParameterizedTest @MethodSource("badRosters")
    void rejectsMalformedRoster(String id,String source) {
        assertThrows(DataFormatException.class,()->factory.students(id).parse(source.lines().toArray(String[]::new)));
    }
    static Stream<Arguments> badGrades() {
        return Stream.of(
          Arguments.of("i","7A\n1;2;3;4\n"),Arguments.of("i","7A\n1;2;3;4;5;1\n"),
          Arguments.of("i","7A\n0;2;3;4;5\n"),Arguments.of("i","7A\n6;2;3;4;5\n"),
          Arguments.of("i","7A\npět;2;3;4;5\n"),Arguments.of("i","7A\n1;;3;4;5\n"),
          Arguments.of("ii","7A\n2\n1;1;1;1;1\n"),Arguments.of("iii","7A\n1\n1\n2\n3\n4\n"),
          Arguments.of("i-named","7A\nMAT:1;MAT:2;IT:1;TV:1;CJ:1\n"),
          Arguments.of("i-named","7A\nMAT:1;XYZ:2;IT:1;TV:1;CJ:1\n"),
          Arguments.of("i-named","7A\nMAT:1;FYZ:2;IT:1;CJ:1\n"),
          Arguments.of("i-named","7A\nMAT:1:2;FYZ:2;IT:1;TV:1;CJ:1\n"),
          Arguments.of("i","7A\n1;1;1;1;1\n\n7A\n1;1;1;1;1\n"),Arguments.of("i",""));
    }
    @ParameterizedTest @MethodSource("badGrades")
    void rejectsMalformedGrades(String id,String source) {
        assertThrows(DataFormatException.class,()->factory.grades(id).parse(source.lines().toArray(String[]::new)));
    }
    @ParameterizedTest @ValueSource(strings={"7A\n1;1;1;1;1\n", "7A\n1;1;1;1;1\n1;1;1;1;1\n\n7X\n1;1;1;1;1\n", "7B\n1;1;1;1;1\n"})
    void rejectsMismatchedFiles(String source) throws Exception {
        Path file=temp.resolve("grades.csv");Files.writeString(file,source);
        assertThrows(DataFormatException.class,()->service().load(Path.of("data/studenti-i.csv"),"i",file,"i"));
    }
    @Test void missingFile() {
        assertThrows(NoSuchFileException.class,()->service().load(temp.resolve("missing.csv"),"i",Path.of("data/znamky-i.csv"),"i"));
    }
    @Test void permissionFailureIsPropagated() {
        // Deterministicky i při běhu jako administrátor; nespoléhá na chování chmod.
        LineProvider denied=path->{throw new AccessDeniedException(path.toString());};
        assertThrows(AccessDeniedException.class,()->new SchoolService(denied,factory).load(Path.of("private.csv"),"i",Path.of("grades.csv"),"i"));
    }
    @Test void invalidUtf8() throws Exception {
        Path file=temp.resolve("invalid.csv");Files.write(file,new byte[]{(byte)0xC3,0x28});
        assertThrows(java.nio.charset.CharacterCodingException.class,()->new Utf8FileProvider().read(file));
    }
    @Test void bomWhitespaceAndMultiwordNames() throws Exception {
        var classes=factory.students("i").parse(new String[]{"\uFEFF7A"," Anna Marie ; Van der Berg "});
        assertEquals("Anna Marie",classes[0].students()[0].firstName());
        assertEquals("Van der Berg",classes[0].students()[0].lastName());
    }
    @Test void emptyClassHasNoNaNAverageInReport() throws Exception {
        LineProvider provider=path->new String[]{"7A","0"};
        var data=new SchoolService(provider,factory).load(Path.of("s"),"ii",Path.of("g"),"ii");
        assertEquals("Třída;Počet studentů;MAT;FYZ;IT;TV;CJ\n7A;0;—;—;—;—;—\n",new ReportFormatter().classes(data));
    }
    @Test void arbitraryClassAndStudentCountUsesGrowingArrays() throws Exception {
        SchoolBuilder school=new SchoolBuilder();
        for(int i=0;i<40;i++) {
            ClassBuilder builder=new ClassBuilder("T"+i);
            for(int j=0;j<70;j++) builder.add("Jméno "+j,"Příjmení");
            school.add(builder.build());
        }
        assertEquals(40,school.build().length);assertEquals(70,school.build()[39].students().length);
        assertEquals(70,school.build()[39].students()[69].number());
    }
    @Test void arraysAndMapsAreProtected() throws Exception {
        var data=load("i","i");SchoolClass[] classes=data.classes();classes[0]=null;
        assertNotNull(data.classes()[0]);Student[] students=data.classes()[0].students();students[0]=null;
        assertNotNull(data.classes()[0].students()[0]);
        assertThrows(UnsupportedOperationException.class,()->data.grades().classes().clear());
        assertThrows(UnsupportedOperationException.class,()->data.grades().classes().get("7A").get(1).clear());
    }
    @Test void factoryCanRegisterAnotherFormatWithoutServiceChange() throws Exception {
        var original=load("i","i");
        factory.registerStudents("custom",new StudentFormat() {
            public SchoolClass[] parse(String[] lines) { return original.classes(); }
            public String serialize(SchoolClass schoolClass) { return "<trida/>"; }
            public String extension() { return "xml"; }
        });
        factory.registerGrades("custom",lines->original.grades());
        var loaded=new SchoolService(path->new String[0],factory).load(Path.of("a"),"custom",Path.of("b"),"custom");
        assertEquals("7A",loaded.classes()[0].code());
        Path output=temp.resolve("new-format");
        new SchoolExporter(new ReportFormatter()).export(loaded,output);
        assertEquals("<trida/>",Files.readString(output.resolve("7A.xml")));
    }
    @Test void unknownFormatsAreRejected() {
        assertThrows(IllegalArgumentException.class,()->factory.students("unknown"));
        assertThrows(IllegalArgumentException.class,()->factory.grades("unknown"));
    }
    @Test void existingOutputIsNeverOverwritten() throws Exception {
        Path output=temp.resolve("existing");Files.createDirectory(output);Files.writeString(output.resolve("keep.txt"),"původní");
        assertThrows(FileAlreadyExistsException.class,()->new SchoolExporter(new ReportFormatter()).export(load("i","i"),output));
        assertEquals("původní",Files.readString(output.resolve("keep.txt")));
    }
    @Test void exportFailureLeavesNoPartialResult() throws Exception {
        var original=load("i","i");
        var broken=new SchoolService.Loaded(new SchoolClass[]{original.classes()[0],new SchoolClass("X".repeat(300),new Student[0])},original.grades(),original.sourceFormat());
        Path output=temp.resolve("partial");
        assertThrows(IOException.class,()->new SchoolExporter(new ReportFormatter()).export(broken,output));
        assertFalse(Files.exists(output));
        try(var paths=Files.list(temp)) { assertEquals(0,paths.count()); }
    }
    @Test void cliSuccessAndErrorExitCodes() throws Exception {
        ByteArrayOutputStream out=new ByteArrayOutputStream(),err=new ByteArrayOutputStream();
        var stdout=new PrintStream(out,true,StandardCharsets.UTF_8);var stderr=new PrintStream(err,true,StandardCharsets.UTF_8);
        assertEquals(0,Main.run(new String[]{"data/studenti-i.csv","i","data/znamky-iii-named.csv","iii-named",temp.resolve("cli").toString()},stdout,stderr));
        assertTrue(out.toString(StandardCharsets.UTF_8).contains("Export dokončen"));assertEquals(0,err.size());
        assertEquals(2,Main.run(new String[]{"--bad"},stdout,stderr));
        assertEquals(1,Main.run(new String[]{temp.resolve("missing").toString(),"i","data/znamky-i.csv","i",temp.resolve("error").toString()},stdout,stderr));
        assertFalse(Files.exists(temp.resolve("error")));
        assertTrue(err.toString(StandardCharsets.UTF_8).contains("Soubor neexistuje"));
    }
}
