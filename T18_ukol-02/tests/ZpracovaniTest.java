import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/** Hraniční testy intervalu a generování vzorků. */
class ZpracovaniTest {
    /** Testovaný objekt školní kostry. */
    private final Zpracovani z = new Zpracovani();
    /** Ověřuje případ: interval.
     * @param a hodnota testovacího případu
     * @param b hodnota testovacího případu
     * @param x hodnota testovacího případu
     * @param expected hodnota testovacího případu
     */
    @ParameterizedTest
    @CsvSource({"0,10,-1,false", "0,10,0,false", "0,10,1,true", "0,10,9,true", "0,10,10,false", "0,10,11,false", "10,0,5,false", "2,2,2,false", "-10,-2,-5,true"})
    void interval(int a, int b, int x, boolean expected) { assertEquals(expected, Zpracovani.interval(a,b,x)); }
    /** Ověřuje případ: krajni Int.
     */
    @Test void krajniInt() { assertTrue(Zpracovani.interval(Integer.MIN_VALUE, Integer.MAX_VALUE, 0)); }
    /** Ověřuje případ: cele Kroky.
     */
    @Test void celeKroky() { assertArrayEquals(new double[]{5,3,1}, z.vzorkySestupně(1,5,2), 1e-12); }
    /** Ověřuje případ: nedosazitelna Mez.
     */
    @Test void nedosazitelnaMez() { assertArrayEquals(new double[]{5,3}, z.vzorkySestupně(2,5,2), 1e-12); }
    /** Ověřuje případ: desetinne Kroky.
     */
    @Test void desetinneKroky() { assertArrayEquals(new double[]{0.3,0.2,0.1,0}, z.vzorkySestupně(0,0.3,0.1), 1e-12); }
    /** Ověřuje případ: zaporne Meze.
     */
    @Test void zaporneMeze() { assertArrayEquals(new double[]{-1,-2,-3}, z.vzorkySestupně(-3,-1,1), 1e-12); }
    /** Ověřuje případ: stejne Meze.
     */
    @Test void stejneMeze() { assertArrayEquals(new double[]{2}, z.vzorkySestupně(2,2,1)); }
    /** Ověřuje případ: velky Krok.
     */
    @Test void velkyKrok() { assertArrayEquals(new double[]{5}, z.vzorkySestupně(1,5,10)); }
    /** Ověřuje případ: obracene Meze.
     */
    @Test void obraceneMeze() { assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(5,1,1)); }
    /** Ověřuje případ: spatny Krok.
     */
    @Test void spatnyKrok() { for (double k : new double[]{0,-1,Double.NaN,Double.POSITIVE_INFINITY}) assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(0,1,k)); }
    /** Ověřuje případ: nekonecne Meze.
     */
    @Test void nekonecneMeze() { for (double n : new double[]{Double.NaN,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY}) { assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(n,1,1)); assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(0,n,1)); } }
}
