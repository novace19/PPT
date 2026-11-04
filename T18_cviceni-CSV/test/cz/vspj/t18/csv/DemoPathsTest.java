package cz.vspj.t18.csv;
import cz.vspj.t18.csv.io.DemoPaths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class DemoPathsTest {
    @TempDir Path temp;
    private Path project(String name) throws Exception {
        Path root=Files.createDirectories(temp.resolve(name));
        Files.createDirectories(root.resolve("data"));
        Files.writeString(root.resolve("data/studenti-i.csv"),"7A\nJan;Novák\n");
        Files.writeString(root.resolve("data/znamky-i.csv"),"7A\n1;1;1;1;1\n");
        return root;
    }
    @Test void jarWithSpacesAndDifferentWorkingDirectory() throws Exception {
        Path root=project("projekt s mezerami");
        Path jar=Files.createFile(root.resolve("T18_cviceni-CSV.jar"));
        assertEquals(root,DemoPaths.locate(jar,temp.resolve("jina slozka")));
    }
    @Test void mavenClasses() throws Exception {
        Path root=project("maven");Path classes=Files.createDirectories(root.resolve("target/classes"));
        assertEquals(root,DemoPaths.locate(classes,temp));
    }
    @Test void intellijClasses() throws Exception {
        Path root=project("idea");Path classes=Files.createDirectories(root.resolve("out/production/T18_cviceni-CSV"));
        assertEquals(root,DemoPaths.locate(classes,temp));
    }
    @Test void outerExtractionFolder() throws Exception {
        Path root=project("outer/T18_cviceni-CSV");
        assertEquals(root,DemoPaths.locate(temp.resolve("elsewhere"),root.getParent()));
    }
    @Test void applicationDataPreferredOverUnrelatedWorkingData() throws Exception {
        Path root=project("spravny-projekt"),other=project("jiny-projekt");
        Path jar=Files.createFile(root.resolve("app.jar"));
        assertEquals(root,DemoPaths.locate(jar,other));
    }
    @Test void missingDataProducesClearError() {
        assertThrows(NoSuchFileException.class,()->DemoPaths.locate(temp.resolve("missing.jar"),temp));
    }
}
