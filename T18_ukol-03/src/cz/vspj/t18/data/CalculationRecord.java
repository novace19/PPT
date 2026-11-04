package cz.vspj.t18.data;

/** Neměnný datový záznam jednoho úspěšného či chybového výstupu. */
public record CalculationRecord(String output, boolean successful) { }
