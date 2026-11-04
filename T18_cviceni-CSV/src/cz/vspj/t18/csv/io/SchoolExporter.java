package cz.vspj.t18.csv.io;
import cz.vspj.t18.csv.formats.*;
import cz.vspj.t18.csv.logic.*;
import cz.vspj.t18.csv.model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;
/** Zápis až po úplné validaci, nejprve do pomocné složky. Existující cíl se nepřepisuje. */
public final class SchoolExporter {
    private final ReportFormatter formatter;
    public SchoolExporter(ReportFormatter formatter) { this.formatter=formatter; }
    public void export(SchoolService.Loaded data, Path output) throws IOException {
        Path target=output.toAbsolutePath().normalize();
        if(Files.exists(target)) throw new FileAlreadyExistsException(target.toString());
        Files.createDirectories(target.getParent());
        Path staging=Files.createTempDirectory(target.getParent(),".csv-export-");
        try {
            for(SchoolClass schoolClass:data.classes())
                write(staging.resolve(schoolClass.code()+"."+data.sourceFormat().extension()),data.sourceFormat().serialize(schoolClass));
            Path reports=Files.createDirectory(staging.resolve("prehledy"));
            write(reports.resolve("studenti.csv"),formatter.students(data));
            write(reports.resolve("tridy.csv"),formatter.classes(data));
            Files.move(staging,target);
        } finally {
            if(Files.exists(staging)) try(var paths=Files.walk(staging)) {
                for(Path file:paths.sorted(Comparator.reverseOrder()).toArray(Path[]::new)) Files.deleteIfExists(file);
            }
        }
    }
    private void write(Path file,String text) throws IOException { Files.writeString(file,text,StandardCharsets.UTF_8,StandardOpenOption.CREATE_NEW); }
}
