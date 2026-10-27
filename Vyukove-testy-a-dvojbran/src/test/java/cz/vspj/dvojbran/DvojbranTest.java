package cz.vspj.dvojbran;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DvojbranTest {
    @Test void normalniVypocetAMezivysledky() {
        Dvojbran.Vysledek v = Dvojbran.vypocitej(12, 100, 200);
        assertEquals(300, v.celkovyOdpor, 1e-12);
        assertEquals(0.04, v.proud, 1e-12);
        assertEquals(4, v.ubytek, 1e-12);
        assertEquals(8, v.vystupniNapeti, 1e-12);
    }
    @Test void idealniZdrojANuloveNapeti() {
        assertEquals(12, Dvojbran.vypocitej(12, 0, 10).vystupniNapeti, 1e-12);
        assertEquals(0, Dvojbran.vypocitej(0, 5, 10).proud, 1e-12);
        assertEquals(-4, Dvojbran.vypocitej(-6, 5, 10).vystupniNapeti, 1e-12);
    }
    @Test void neplatneVstupy() {
        assertThrows(IllegalArgumentException.class, () -> Dvojbran.vypocitej(1, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> Dvojbran.vypocitej(1, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> Dvojbran.vypocitej(Double.NaN, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> Dvojbran.vypocitej(1, Double.MAX_VALUE, Double.MAX_VALUE));
    }
}
