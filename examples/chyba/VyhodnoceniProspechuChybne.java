/** Modelová chyba pro rozbor; není součástí produkčního zdrojového adresáře. */
public class VyhodnoceniProspechuChybne {
    public static double prumer(int[] znamky) {
        int soucet = 0;
        for (int z : znamky) soucet += z;
        return soucet / znamky.length; // CHYBA: celočíselné dělení
    }
    public static void main(String[] args) {
        double skutecnost = prumer(new int[]{1, 2});
        if (skutecnost != 1.5) throw new AssertionError("Očekáváno 1.5, skutečnost " + skutecnost);
    }
}
