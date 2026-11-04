package cz.vspj.t18.csv.logic;
/** Chybný obsah souboru nebo nesoulad mezi soubory. */
public class DataFormatException extends Exception {
    public DataFormatException(String message) { super(message); }
}
