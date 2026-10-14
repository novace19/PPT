import java.util.Objects;

/** Slovní vyhodnocení aritmetického průměru známek 1 až 5. */
public class VyhodnoceniProspechu {
    /** Hodnocení odpovídající zaokrouhleným známkám 1 až 5. */
    private final String[] slovniHodnocení = {"výborně", "chvalitebně", "dobře", "dostatečně", "nedostatečně"};

    /** Vytvoří vyhodnocovač. */
    public VyhodnoceniProspechu() { }

    /**
     * Vyhodnotí průměr zaokrouhlený na nejbližší celé číslo; přesná polovina
     * se zaokrouhlí k horší známce (například 1,5 na 2).
     * Toto pravidlo je výslovný předpoklad, protože školní kostra hranice neurčuje.
     * @param znamky neprázdné pole známek 1 až 5
     * @return slovní hodnocení zaokrouhleného průměru
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo známka mimo 1 až 5
     */
    public String prospech(int znamky[]) {
        int znamka = (int) Math.round(prumer(znamky));
        return slovniHodnocení[znamka - 1];
    }

    /**
     * Spočítá aritmetický průměr bez ztráty desetinné části a bez změny vstupu.
     * @param znamky neprázdné pole známek 1 až 5
     * @return desetinný průměr známek
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo obsahuje neplatnou známku
     */
    private double prumer(int znamky[]) {
        Objects.requireNonNull(znamky, "Známky nesmí být null.");
        if (znamky.length == 0) throw new IllegalArgumentException("Pole známek je prázdné.");
        long soucet = 0;
        for (int znamka : znamky) {
            if (znamka < 1 || znamka > 5) throw new IllegalArgumentException("Známky musí být 1 až 5.");
            soucet += znamka;
        }
        return (double) soucet / znamky.length;
    }
}
