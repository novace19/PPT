package cz.vspj.t18.logic;

import cz.pokrocile.faktorial.FaktorialException;
import cz.vspj.t18.data.CalculationHistory;
import cz.vspj.t18.data.CalculationRecord;

/** Logická vrstva: validace vstupu, výpočet, formátování a správa historie. */
public final class FactorialService {
    private final CalculationHistory history;
    public FactorialService(CalculationHistory history) { this.history = history; }

    /** Každý požadavek uloží jako nový záznam; očekávané výjimky převede na zprávu. */
    public CalculationRecord calculate(String input, boolean showSteps) {
        CalculationRecord record;
        Integer n = null;
        try {
            n = Integer.valueOf(input == null ? "" : input.trim());
            int value = MyMath.factorial(n);
            StringBuilder text = new StringBuilder(n + "! = ");
            if (showSteps && n > 0) {
                for (int i = n; i >= 1; i--) {
                    text.append(i);
                    if (i > 1) text.append(" * ");
                }
                text.append(" = ");
            }
            record = new CalculationRecord(text.append(value).toString(), true);
        } catch (NumberFormatException e) {
            record = new CalculationRecord("=> Chyba:Vstupní údaj není správný.", false);
        } catch (FaktorialException e) {
            String message = n < 0
                    ? n + "! => Chyba:Nelze spočítat faktoriál záporného čísla."
                    : "=> Chyba:Vstupní údaj není správný.";
            record = new CalculationRecord(message, false);
        }
        history.add(record);
        return record;
    }
    public void clearHistory() { history.clear(); }
}
