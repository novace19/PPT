package cz.vspj.t18.csv.io;

import java.net.URISyntaxException;
import java.nio.file.*;

/** Najde ukázková data podle umístění aplikace, nezávisle na pracovní složce IDE. */
public final class DemoPaths {
    private DemoPaths() { }
    public static Path projectDirectory() throws NoSuchFileException {
        Path working = Path.of("").toAbsolutePath();
        Path location = working;
        try {
            var source = DemoPaths.class.getProtectionDomain().getCodeSource();
            if (source != null) location = Path.of(source.getLocation().toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            // Při nestandardním classloaderu použijeme pracovní složku.
        }
        return locate(location,working);
    }
    public static Path locate(Path applicationLocation, Path workingDirectory) throws NoSuchFileException {
        Path candidate = applicationLocation.toAbsolutePath().normalize();
        if (Files.isRegularFile(candidate)) candidate = candidate.getParent();
        // JAR vedle data/, Maven target/classes nebo IntelliJ out/production/modul.
        for (int depth=0; candidate!=null && depth<5; depth++, candidate=candidate.getParent()) {
            if (hasData(candidate)) return candidate;
        }
        Path working = workingDirectory.toAbsolutePath().normalize();
        if (hasData(working)) return working;
        Path inner = working.resolve("T18_cviceni-CSV");
        if (hasData(inner)) return inner;
        throw new NoSuchFileException("Ukázková data nenalezena. Rozbalte složku data vedle T18_cviceni-CSV.jar.");
    }
    private static boolean hasData(Path directory) {
        return Files.isRegularFile(directory.resolve("data/studenti-i.csv"))
                && Files.isRegularFile(directory.resolve("data/znamky-i.csv"));
    }
}
