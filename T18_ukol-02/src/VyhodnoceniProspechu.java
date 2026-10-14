/**
 * Školní kostra pro vyhodnocení prospěchu; první fáze obsahuje pouze testy.
 * Těla metod zůstávají nedoplněná. Komentáře popisují cílový kontrakt testů.
 */
public class VyhodnoceniProspechu {
    /** Původní seznam slovních hodnocení ze školního projektu. */
    private String[] slovniHodnocení = new String[] {"výborně", "chvalitebně", "dobře", "dostatečně", "nedostatečně"};

    /** Vytvoří objekt původní školní kostry. */
    public VyhodnoceniProspechu() { }

    /**
     * Cílové chování: slovní hodnocení zaokrouhleného průměru známek 1 až 5.
     * Předpoklad testů: půlka se zaokrouhlí k horší známce.
     * Ve fázi 2-i metoda zůstává neimplementovaná a vrací prázdný řetězec.
     * @param znamky neprázdné pole známek 1 až 5 (cílový kontrakt)
     * @return nyní prázdný řetězec; po implementaci slovní hodnocení
     */
    public String prospech(int znamky[]) {
        return "";
    }

    /**
     * Cílové chování: aritmetický průměr bez ztráty desetinné části.
     * Soukromá metoda se ověřuje nepřímo přes veřejnou metodu prospech.
     * @param znamky známky pro výpočet průměru
     * @return nyní 0.0; výpočet není v této fázi doplněn
     */
    private double prumer(int znamky[]) {
        return 0.0;
    }
}
