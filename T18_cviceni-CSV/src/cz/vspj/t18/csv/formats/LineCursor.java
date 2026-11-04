package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.logic.DataFormatException;
/** Čtení textových řádků bez přístupu k souborovému systému. */
final class LineCursor {
    private final String[] lines; private int index;
    LineCursor(String[] lines) {
        this.lines = lines.clone();
        if (this.lines.length > 0) this.lines[0] = this.lines[0].replaceFirst("^\uFEFF", "");
    }
    boolean has() { return index < lines.length; }
    boolean blank() { return has() && lines[index].isBlank(); }
    void blanks() { while(blank()) index++; }
    String read() throws DataFormatException {
        if (!has()) throw error("Neočekávaný konec souboru.");
        String text = lines[index++].strip();
        if (text.isEmpty()) throw error("Prázdná položka uvnitř záznamu.");
        return text;
    }
    int count() throws DataFormatException {
        String text = read();
        try { int n = Integer.parseInt(text); if(n < 0) throw new NumberFormatException(); return n; }
        catch (NumberFormatException e) { throw error("Neplatný počet studentů: " + text); }
    }
    DataFormatException error(String text) { return new DataFormatException("Řádek " + Math.max(1,index) + ": " + text); }
}
