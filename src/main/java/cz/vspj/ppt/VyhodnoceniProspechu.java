package cz.vspj.ppt;

import java.util.Objects;

/** Slovní hodnocení známek 1 až 5 podle pravidel popsaných v README. */
public class VyhodnoceniProspechu {
    /** Vytvoří vyhodnocovač. */
    public VyhodnoceniProspechu() { }

    /**
     * Vrátí hodnocení; pětka má přednost před průměrem.
     * Vyznamenání vyžaduje průměr nejvýše 1,5 a všechny známky nejvýše 2.
     * @param znamky neprázdné pole známek od 1 do 5
     * @return „prospěl s vyznamenáním“, „prospěl“ nebo „neprospěl“
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo známka neplatná
     */
    public String prospech(int[] znamky) {
        double prumer = prumer(znamky);
        int nejhorsi = 1;
        for (int znamka : znamky) nejhorsi = Math.max(nejhorsi, znamka);
        if (nejhorsi == 5) return "neprospěl";
        if (prumer <= 1.5 && nejhorsi <= 2) return "prospěl s vyznamenáním";
        return "prospěl";
    }

    /**
     * Spočítá aritmetický průměr s desetinnou částí; pole nemění.
     * @param znamky neprázdné pole známek od 1 do 5
     * @return součet známek dělený jejich počtem jako double
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo známka neplatná
     */
    public double prumer(int[] znamky) {
        Objects.requireNonNull(znamky, "Známky nesmí být null.");
        if (znamky.length == 0) throw new IllegalArgumentException("Chybí známky.");
        long soucet = 0;
        for (int znamka : znamky) {
            if (znamka < 1 || znamka > 5) throw new IllegalArgumentException("Známka musí být 1 až 5.");
            soucet += znamka;
        }
        return (double) soucet / znamky.length;
    }
}
