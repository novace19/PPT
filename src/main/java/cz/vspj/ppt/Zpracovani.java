package cz.vspj.ppt;

import java.math.BigDecimal;

/** Komparační funkce pro otevřený interval a generování sestupných vzorků. */
public class Zpracovani {
    /** Vytvoří objekt pro zpracování. */
    public Zpracovani() { }

    /**
     * Ověří příslušnost do otevřeného intervalu (odkud, kam).
     * @param odkud dolní mez, nezahrnutá
     * @param kam horní mez, nezahrnutá
     * @param value testovaná hodnota
     * @return true právě když odkud &lt; value &lt; kam; pro obrácené či stejné meze false
     */
    public boolean interval(int odkud, int kam, int value) {
        return odkud < value && value < kam;
    }

    /**
     * Generuje hodnoty odkud, odkud-krok, ... až po hodnotu nejméně kam.
     * Zachovává pořadí parametrů ze zadání: nejprve kam, potom odkud.
     * Dolní mez se zahrne pouze tehdy, pokud na ni vyjde přesný počet kroků.
     * Výpočet používá desetinnou reprezentaci vstupů, nikoli opakované double odčítání.
     * @param kam dolní mez včetně, je-li dosažitelná
     * @param odkud horní mez včetně
     * @param krok kladná konečná velikost kroku
     * @return sestupné pole; při shodných mezích jednoprvkové
     * @throws IllegalArgumentException pokud vstup není konečný, krok není kladný,
     *         odkud je menší než kam nebo by vzniklo více než milion vzorků
     */
    public double[] vzorkySestupně(double kam, double odkud, double krok) {
        if (!Double.isFinite(kam) || !Double.isFinite(odkud) || !Double.isFinite(krok)
                || krok <= 0 || odkud < kam) {
            throw new IllegalArgumentException("Vyžadovány konečné meze, odkud >= kam a krok > 0.");
        }
        BigDecimal start = BigDecimal.valueOf(odkud);
        BigDecimal step = BigDecimal.valueOf(krok);
        BigDecimal count = start.subtract(BigDecimal.valueOf(kam)).divideToIntegralValue(step).add(BigDecimal.ONE);
        if (count.compareTo(BigDecimal.valueOf(1_000_000)) > 0)
            throw new IllegalArgumentException("Nejvýše milion vzorků.");
        double[] vysledek = new double[count.intValueExact()];
        for (int i = 0; i < vysledek.length; i++) {
            vysledek[i] = start.subtract(step.multiply(BigDecimal.valueOf(i))).doubleValue();
            if (i > 0 && vysledek[i] >= vysledek[i - 1])
                throw new IllegalArgumentException("Krok je příliš malý pro rozlišení double.");
        }
        return vysledek;
    }
}
