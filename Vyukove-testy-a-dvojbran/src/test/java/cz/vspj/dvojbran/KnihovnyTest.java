package cz.vspj.dvojbran;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class KnihovnyTest {
    private static final double EPS = 1e-12;
    @Test void odmocnina() {
        assertEquals(3, Math.sqrt(9), EPS);
        assertEquals(0, Math.sqrt(0), EPS);
        assertTrue(Double.isNaN(Math.sqrt(-1)));
        assertEquals(Double.POSITIVE_INFINITY, Math.sqrt(Double.POSITIVE_INFINITY));
    }
    @Test void tangens() {
        assertEquals(0, Math.tan(0), EPS);
        assertEquals(1, Math.tan(Math.PI / 4), EPS);
        assertEquals(-1, Math.tan(-Math.PI / 4), EPS);
        assertTrue(Double.isNaN(Math.tan(Double.POSITIVE_INFINITY)));
    }
    @Test void prevodyUhlu() {
        assertEquals(Math.PI, Math.toRadians(180), EPS);
        assertEquals(-Math.PI / 2, Math.toRadians(-90), EPS);
        assertEquals(180, Math.toDegrees(Math.PI), EPS);
        assertEquals(-90, Math.toDegrees(-Math.PI / 2), EPS);
        assertEquals(37, Math.toDegrees(Math.toRadians(37)), EPS);
    }
    @Test void indexPrvnihoVyskytu() {
        List<String> seznam = new ArrayList<>(Arrays.asList("A", "B", "A", null));
        assertEquals(0, seznam.indexOf("A"));
        assertEquals(1, seznam.indexOf("B"));
        assertEquals(3, seznam.indexOf(null));
        assertEquals(-1, seznam.indexOf("C"));
        assertEquals(-1, Collections.emptyList().indexOf("A"));
    }
    // Slovník v Javě představuje rozhraní Map, např. implementace HashMap.
    // Místo Add používá put; další operace jsou get, containsKey, remove a size.
    // put pro existující klíč přepíše hodnotu; containsKey rozliší chybějící klíč od null.
    @Test void vlozeniDoMapy() {
        Map<String, Integer> mapa = new HashMap<>();
        assertTrue(mapa.isEmpty());
        assertNull(mapa.put("jablko", 1));
        assertTrue(mapa.containsKey("jablko"));
        assertEquals(Integer.valueOf(1), mapa.get("jablko"));
        assertEquals(Integer.valueOf(1), mapa.put("jablko", 2));
        assertEquals(1, mapa.size());
        assertEquals(Integer.valueOf(2), mapa.get("jablko"));
        mapa.put("prazdne", null);
        assertTrue(mapa.containsKey("prazdne"));
        assertNull(mapa.get("prazdne"));
        assertFalse(mapa.containsKey("chybi"));
        assertNull(mapa.get("chybi"));
    }
}
