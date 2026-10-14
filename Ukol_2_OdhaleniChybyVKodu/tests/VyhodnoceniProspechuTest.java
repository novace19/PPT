import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/** Testy hranic zaokrouhlení, vstupů a regrese celočíselného dělení. */
class VyhodnoceniProspechuTest {
    private final VyhodnoceniProspechu v = new VyhodnoceniProspechu();
    @ParameterizedTest
    @CsvSource({"1,výborně", "2,chvalitebně", "3,dobře", "4,dostatečně", "5,nedostatečně"})
    void jednotliveZnamky(int znamka, String text) { assertEquals(text, v.prospech(new int[]{znamka})); }
    @ParameterizedTest
    @CsvSource({"1,2,chvalitebně", "2,3,dobře", "3,4,dostatečně", "4,5,nedostatečně"})
    void presnePoloviny(int a, int b, String text) { assertEquals(text, v.prospech(new int[]{a,b})); }
    @Test void podHranici() { assertEquals("výborně", v.prospech(new int[]{1,1,2})); }
    @Test void nadHranici() { assertEquals("chvalitebně", v.prospech(new int[]{1,2,2})); }
    @Test void ruzneZnamky() { assertEquals("dobře", v.prospech(new int[]{1,2,3,4,5})); }
    @Test void petkaNemaZvlastniPravidlo() { assertEquals("dobře", v.prospech(new int[]{1,5})); }
    @Test void prazdnePole() { assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{})); }
    @Test void nullPole() { assertThrows(NullPointerException.class, () -> v.prospech(null)); }
    @Test void neplatneZnamky() { for (int n : new int[]{0,6,-1,Integer.MAX_VALUE}) assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{n})); }
    @Test void nemeniVstup() { int[] a = {2,1}; v.prospech(a); assertArrayEquals(new int[]{2,1}, a); }
}
