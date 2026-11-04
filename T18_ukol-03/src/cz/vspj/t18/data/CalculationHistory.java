package cz.vspj.t18.data;

import java.util.ArrayList;
import java.util.List;

/** Datová vrstva: paměťová historie. Neobsahuje výpočty ani Swing. */
public final class CalculationHistory {
    private final List<CalculationRecord> records = new ArrayList<>();
    public void add(CalculationRecord record) { records.add(record); }
    public List<CalculationRecord> records() { return List.copyOf(records); }
    public void clear() { records.clear(); }
}
