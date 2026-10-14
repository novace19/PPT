import java.util.Objects;

/** Komparační funkce se znaménkovou konvencí -1, 0 a 1. */
public class ZpracovaniMoje {
    /** Vytvoří objekt komparačních funkcí. */
    public ZpracovaniMoje() { }

    /**
     * Porovná dvě celá čísla bez přetečení při odčítání.
     * @param a první číslo
     * @param b druhé číslo
     * @return -1 pokud a je menší, 0 při rovnosti, jinak 1
     */
    public static int comp(int a, int b) {
        return Integer.compare(a, b);
    }

    /**
     * Porovná řetězce lexikograficky podle UTF-16, rozlišuje velikost písmen.
     * Nejde o české abecední řazení; null není přípustný.
     * @param a první neprázdný nebo prázdný řetězec, nikoli null
     * @param b druhý neprázdný nebo prázdný řetězec, nikoli null
     * @return -1 pokud a předchází b, 0 při shodě, jinak 1
     * @throws NullPointerException pokud je kterýkoli řetězec null
     */
    public static int comp(String a, String b) {
        Objects.requireNonNull(a, "První řetězec nesmí být null.");
        Objects.requireNonNull(b, "Druhý řetězec nesmí být null.");
        return Integer.signum(a.compareTo(b));
    }
}
