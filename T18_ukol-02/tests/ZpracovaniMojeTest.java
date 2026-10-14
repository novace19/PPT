import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/** Testy znaménka komparací, mezních hodnot, řetězců a vlastností uspořádání. */
class ZpracovaniMojeTest {
    /** Ověřuje případ: cisla.
     * @param a hodnota testovacího případu
     * @param b hodnota testovacího případu
     * @param expected hodnota testovacího případu
     */
    @ParameterizedTest
    @CsvSource({"-5,5,-1", "2,5,-1", "-7,5,-1", "-2,5,-1",
        "5,-5,1", "5,2,1", "5,-7,1", "5,-2,1",
        "5,5,0", "0,0,0", "-5,-5,0",
        "4,5,-1", "6,5,1", "-6,-5,-1", "-4,-5,1",
        "-2147483648,2147483647,-1", "2147483647,-2147483648,1"})
    void cisla(int a, int b, int expected) { assertEquals(expected, ZpracovaniMoje.comp(a,b)); }
    /** Ověřuje případ: retezce.
     * @param a hodnota testovacího případu
     * @param b hodnota testovacího případu
     * @param expected hodnota testovacího případu
     */
    @ParameterizedTest
    @CsvSource({"abc,abd,-1", "abd,abc,1", "abc,abc,0", "a,ab,-1", "A,a,-1", "z,a,1"})
    void retezce(String a, String b, int expected) { assertEquals(expected, ZpracovaniMoje.comp(a,b)); }
    /** Ověřuje případ: prazdne Retezce.
     */
    @Test void prazdneRetezce() { assertEquals(0, ZpracovaniMoje.comp("", "")); assertEquals(-1, ZpracovaniMoje.comp("", "a")); assertEquals(1, ZpracovaniMoje.comp("a", "")); }
    /** Ověřuje případ: shoda Obsahu.
     */
    @Test void shodaObsahu() { assertEquals(0, ZpracovaniMoje.comp(new String("abc"), new String("abc"))); }
    /** Ověřuje případ: null Retezce.
     */
    @Test void nullRetezce() { assertThrows(NullPointerException.class, () -> ZpracovaniMoje.comp(null,"a")); assertThrows(NullPointerException.class, () -> ZpracovaniMoje.comp("a",null)); assertThrows(NullPointerException.class, () -> ZpracovaniMoje.comp(null,null)); }
    /** Ověřuje případ: retezce Antisymetrie ATranzitivita.
     */
    @Test void retezceAntisymetrieATranzitivita() {
        String[] hodnoty = {"", "A", "a", "ab", "b"};
        for (String a : hodnoty) for (String b : hodnoty) {
            assertEquals(-ZpracovaniMoje.comp(b,a), ZpracovaniMoje.comp(a,b));
            for (String c : hodnoty) if (ZpracovaniMoje.comp(a,b)<0 && ZpracovaniMoje.comp(b,c)<0)
                assertTrue(ZpracovaniMoje.comp(a,c)<0);
        }
    }
    /** Ověřuje případ: antisymetrie ATranzitivita.
     */
    @Test void antisymetrieATranzitivita() {
        int[] cisla = {Integer.MIN_VALUE,-1,0,1,Integer.MAX_VALUE};
        for (int a : cisla) for (int b : cisla) {
            assertEquals(-ZpracovaniMoje.comp(b,a), ZpracovaniMoje.comp(a,b));
            for (int c : cisla) if (ZpracovaniMoje.comp(a,b)<0 && ZpracovaniMoje.comp(b,c)<0) assertTrue(ZpracovaniMoje.comp(a,c)<0);
        }
    }
}
