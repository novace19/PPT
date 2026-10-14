import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy cílového chování prospěchu. Zaokrouhlení a reakce na neplatné vstupy
 * jsou návrhové předpoklady uvedené v dokumentaci první fáze.
 */
class VyhodnoceniProspechuTest {
    /** Testovaný objekt školní kostry. */
    private final VyhodnoceniProspechu v = new VyhodnoceniProspechu();
    /** Ověřuje případ: jednotlive Znamky.
     * @param znamka hodnota testovacího případu
     * @param text hodnota testovacího případu
     */
    @ParameterizedTest
    @CsvSource({"1,výborně", "2,chvalitebně", "3,dobře", "4,dostatečně", "5,nedostatečně"})
    void jednotliveZnamky(int znamka, String text) { assertEquals(text, v.prospech(new int[]{znamka})); }
    /** Ověřuje případ: presne Poloviny.
     * @param a hodnota testovacího případu
     * @param b hodnota testovacího případu
     * @param text hodnota testovacího případu
     */
    @ParameterizedTest
    @CsvSource({"1,2,chvalitebně", "2,3,dobře", "3,4,dostatečně", "4,5,nedostatečně"})
    void presnePoloviny(int a, int b, String text) { assertEquals(text, v.prospech(new int[]{a,b})); }
    /** Ověřuje případ: pod Hranici.
     */
    @Test void podHranici() { assertEquals("výborně", v.prospech(new int[]{1,1,2})); }
    /** Ověřuje případ: nad Hranici.
     */
    @Test void nadHranici() { assertEquals("chvalitebně", v.prospech(new int[]{1,2,2})); }
    /** Ověřuje případ: ruzne Znamky.
     */
    @Test void ruzneZnamky() { assertEquals("dobře", v.prospech(new int[]{1,2,3,4,5})); }
    /** Ověřuje případ: petka Nema Zvlastni Pravidlo.
     */
    @Test void petkaNemaZvlastniPravidlo() { assertEquals("dobře", v.prospech(new int[]{1,5})); }
    /** Ověřuje případ: prazdne Pole.
     */
    @Test void prazdnePole() { assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{})); }
    /** Ověřuje případ: null Pole.
     */
    @Test void nullPole() { assertThrows(NullPointerException.class, () -> v.prospech(null)); }
    /** Ověřuje případ: neplatne Znamky.
     */
    @Test void neplatneZnamky() { for (int n : new int[]{0,6,-1,Integer.MAX_VALUE}) assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{n})); }
    /** Ověřuje případ: nemeni Vstup.
     */
    @Test void nemeniVstup() { int[] a = {2,1}; v.prospech(a); assertArrayEquals(new int[]{2,1}, a); }
}
