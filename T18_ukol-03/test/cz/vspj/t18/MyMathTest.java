package cz.vspj.t18;
import cz.vspj.t18.logic.MyMath;
import cz.pokrocile.faktorial.FaktorialException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
class MyMathTest {
    @ParameterizedTest
    @CsvSource({"0,1", "1,1", "2,2", "3,6", "5,120", "10,3628800", "11,39916800", "12,479001600"})
    void correctValues(int n, int expected) throws FaktorialException { assertEquals(expected, MyMath.factorial(n)); }
    @ParameterizedTest @ValueSource(ints={-1,-5,Integer.MIN_VALUE})
    void negative(int n) {
        var e = assertThrows(FaktorialException.class, () -> MyMath.factorial(n));
        assertEquals("Faktoriál záporného čísla není definován: " + n, e.getMessage());
    }
    @ParameterizedTest @ValueSource(ints={13,100,Integer.MAX_VALUE})
    void overflow(int n) { assertThrows(FaktorialException.class, () -> MyMath.factorial(n)); }
}
