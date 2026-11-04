package cz.vspj.t18;
import cz.vspj.t18.logic.MyMath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
class MyMathTest {
    @ParameterizedTest
    @CsvSource({"0,1", "1,1", "2,2", "5,120", "10,3628800", "12,479001600", "13,6227020800", "20,2432902008176640000"})
    void correctValues(int n, long expected) { assertEquals(expected, MyMath.factorial(n)); }
    @ParameterizedTest @ValueSource(ints={-1,-5,Integer.MIN_VALUE})
    void negative(int n) {
        var e = assertThrows(IllegalArgumentException.class, () -> MyMath.factorial(n));
        assertEquals("Nelze spočítat faktoriál záporného čísla.", e.getMessage());
    }
    @ParameterizedTest @ValueSource(ints={21,100,Integer.MAX_VALUE})
    void overflow(int n) { assertThrows(ArithmeticException.class, () -> MyMath.factorial(n)); }
}
