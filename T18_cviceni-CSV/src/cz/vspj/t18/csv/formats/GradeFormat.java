package cz.vspj.t18.csv.formats;
import cz.vspj.t18.csv.model.GradeBook;
import cz.vspj.t18.csv.logic.DataFormatException;
public interface GradeFormat { GradeBook parse(String[] lines) throws DataFormatException; }
