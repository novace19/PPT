package cz.vspj.ppt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Testy tříd ekvivalence, hranic a regrese celočíselného dělení. */
class VyhodnoceniProspechuTest {
    private final VyhodnoceniProspechu v = new VyhodnoceniProspechu();
    @Test void desetinnaCastPrumeru() { assertEquals(1.5, v.prumer(new int[]{1, 2}), 1e-12); }
    @Test void nadHraniciVyznamenani() { assertEquals("prospěl", v.prospech(new int[]{1, 2, 2})); }
    @Test void presnaHranice() { assertEquals("prospěl s vyznamenáním", v.prospech(new int[]{1, 2})); }
    @Test void sameJednicky() { assertEquals("prospěl s vyznamenáním", v.prospech(new int[]{1, 1})); }
    @Test void trojkaBraniVyznamenani() { assertEquals("prospěl", v.prospech(new int[]{1, 1, 1, 3})); }
    @Test void petkaMaPrednost() { assertEquals("neprospěl", v.prospech(new int[]{1, 1, 1, 1, 1, 1, 1, 5})); }
    @Test void samotnaPetka() { assertEquals("neprospěl", v.prospech(new int[]{5})); }
    @Test void ctyrkaProjde() { assertEquals("prospěl", v.prospech(new int[]{4})); }
    @Test void prazdnePole() { assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{})); }
    @Test void nullPole() { assertThrows(NullPointerException.class, () -> v.prumer(null)); }
    @Test void neplatneZnamky() { for (int n : new int[]{0, 6, -1}) assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{n})); }
    @Test void nemeniVstup() { int[] a = {2, 1}; v.prospech(a); assertArrayEquals(new int[]{2, 1}, a); }
}
