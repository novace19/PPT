package cz.vspj.t18.logic;

/** Matematické operace nezávislé na uživatelském rozhraní. */
public final class MyMath {
    private MyMath() { }

    /**
     * Vypočítá n! přesně v typu long (0! = 1).
     * @param n celé číslo v rozsahu 0 až 20
     * @return faktoriál čísla n
     * @throws IllegalArgumentException pokud je n záporné
     * @throws ArithmeticException pokud výsledek nelze uložit do long
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Nelze spočítat faktoriál záporného čísla.");
        }
        if (n > 20) {
            throw new ArithmeticException("Výsledek je mimo rozsah typu long.");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = Math.multiplyExact(result, i);
        }
        return result;
    }
}
