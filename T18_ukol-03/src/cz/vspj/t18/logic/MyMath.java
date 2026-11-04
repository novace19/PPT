package cz.vspj.t18.logic;

import cz.pokrocile.faktorial.CalcFaktorial;
import cz.pokrocile.faktorial.FaktorialException;

/** Matematické rozhraní úkolu 03 nad původním výpočtem z úkolu 01. */
public final class MyMath {
    private MyMath() { }

    /**
     * Použije původní variantu calc2 s vlastní kontrolovanou výjimkou.
     * Implementace i testy CalcFaktorial byly převzaty beze změn z odevzdání T18.
     * @param n celé číslo v rozsahu 0 až 12
     * @return přesný faktoriál čísla n v typu int; 0! = 1
     * @throws FaktorialException pokud n je záporné nebo větší než 12
     */
    public static int factorial(int n) throws FaktorialException {
        return CalcFaktorial.calc2(n);
    }
}
