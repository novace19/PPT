package cz.vspj.t18.csv;
import cz.vspj.t18.csv.formats.*;
import cz.vspj.t18.csv.io.*;
import cz.vspj.t18.csv.logic.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.CharacterCodingException;
/** Konzolová prezentační vrstva. Bez parametrů spustí připravenou ukázku. */
public final class Main {
    private Main() { }
    public static void main(String[] args) { int code=run(args,System.out,System.err); if(code!=0) System.exit(code); }
    public static int run(String[] args,PrintStream out,PrintStream err) {
        if(args.length==0) args=new String[]{"data/studenti-i.csv","i","data/znamky-i.csv","i","vystup-demo-" + System.currentTimeMillis()};
        if(args.length!=5) {
            err.println("Použití: java -jar T18_cviceni-CSV.jar studenti.csv i|ii|iii znamky.csv i|ii|iii|i-named|ii-named|iii-named NOVA_VYSTUPNI_SLOZKA"); return 2;
        }
        try {
            var factory=new DataFormatFactory();
            var data=new SchoolService(new Utf8FileProvider(),factory).load(Path.of(args[0]),args[1],Path.of(args[2]),args[3]);
            var formatter=new ReportFormatter();
            new SchoolExporter(formatter).export(data,Path.of(args[4]));
            out.print(formatter.students(data)); out.println(); out.print(formatter.classes(data));
            out.println("Export dokončen: " + Path.of(args[4]).toAbsolutePath()); return 0;
        } catch(NoSuchFileException e) { err.println("Chyba: Soubor neexistuje: " + e.getFile()); }
        catch(AccessDeniedException e) { err.println("Chyba: Nedostatečné oprávnění: " + e.getFile()); }
        catch(FileAlreadyExistsException e) { err.println("Chyba: Výstupní složka již existuje. Zvolte nový název: " + e.getFile()); }
        catch(CharacterCodingException e) { err.println("Chyba: Soubor musí mít kódování UTF-8."); }
        catch(DataFormatException | IllegalArgumentException e) { err.println("Chyba dat: " + e.getMessage()); }
        catch(IOException e) { err.println("Chyba při čtení nebo zápisu souboru: " + e.getMessage()); }
        return 1;
    }
}
