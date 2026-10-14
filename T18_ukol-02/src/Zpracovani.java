/** Školní kostra intervalu a vzorků; první fáze zatím doplňuje pouze testy. */
public class Zpracovani {
    /** Vytvoří objekt původní školní kostry. */
    public Zpracovani() { }

    /**
     * Cílové chování: rozhodnout, zda odkud &lt; value &lt; kam.
     * Obě meze jsou vyloučeny; pro shodné a obrácené meze testy očekávají false.
     * @param odkud dolní mez
     * @param kam horní mez
     * @param value ověřovaná hodnota
     * @return nyní vždy false; metoda zatím není implementována
     */
    public static boolean interval(int odkud, int kam, int value) {
        return false;
    }

    /**
     * Cílové chování dle zvoleného kontraktu: sestupné vzorky od odkud po kam
     * s kladným krokem; horní mez se zahrnuje, dolní pouze při dosažení krokem.
     * Pořadí argumentů odpovídá školní kostře, tedy nejprve kam a potom odkud.
     * @param kam dolní mez
     * @param odkud počáteční horní mez
     * @param krok kladný konečný krok
     * @return nyní null; metoda zatím není implementována
     */
    public double[] vzorkySestupně(double kam, double odkud, double krok) {
        return null;
    }
}
