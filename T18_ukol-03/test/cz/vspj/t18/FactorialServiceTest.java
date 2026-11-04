package cz.vspj.t18;
import cz.vspj.t18.data.*;
import cz.vspj.t18.logic.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
class FactorialServiceTest {
    private final CalculationHistory history = new CalculationHistory();
    private final FactorialService service = new FactorialService(history);
    @ParameterizedTest @NullAndEmptySource
    @ValueSource(strings={" ","pět","5.0","1,5","2147483648","9999999999999999999999","21","2147483647"})
    void invalidInput(String input) {
        var result = service.calculate(input, true);
        assertEquals("=> Chyba:Vstupní údaj není správný.", result.output());
        assertFalse(result.successful()); assertEquals(1, history.records().size());
    }
    @Test void stepsAndWhitespace() {
        assertEquals("5! = 5 * 4 * 3 * 2 * 1 = 120", service.calculate(" 5 ",true).output());
        assertEquals("0! = 1", service.calculate("0",true).output());
        assertEquals("1! = 1 = 1", service.calculate("1",true).output());
        assertEquals("5! = 120", service.calculate("5",false).output());
    }
    @Test void negative() {
        var result = service.calculate("-1",false);
        assertEquals("-1! => Chyba:Nelze spočítat faktoriál záporného čísla.",result.output());
        assertFalse(result.successful());
    }
    @Test void historyAndClear() {
        service.calculate("5",false); service.calculate("pět",false);
        var snapshot = history.records();
        assertEquals(2,snapshot.size());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.clear());
        service.clearHistory(); assertTrue(history.records().isEmpty());
        assertEquals(2,snapshot.size());
        assertEquals("2! = 2", service.calculate("2",false).output());
    }
}
