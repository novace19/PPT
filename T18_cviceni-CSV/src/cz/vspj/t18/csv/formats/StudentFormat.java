package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.model.SchoolClass;
import cz.vspj.t18.csv.logic.DataFormatException;
/** Rozhraní rozšiřitelného formátu seznamu; např. XML implementuje stejný kontrakt. */
public interface StudentFormat {
    SchoolClass[] parse(String[] lines) throws DataFormatException;
    String serialize(SchoolClass schoolClass);
    default String extension() { return "csv"; }
}
