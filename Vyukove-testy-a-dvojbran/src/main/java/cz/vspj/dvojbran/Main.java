package cz.vspj.dvojbran;

import java.util.Locale;

public final class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Použití: java cz.vspj.dvojbran.Main <napětí V> <vnitřní odpor Ω> <zátěž Ω>");
            System.exit(1);
        }
        try {
            Dvojbran.Vysledek v = Dvojbran.vypocitej(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            System.out.printf(Locale.US, "Celkový odpor: %.6f Ω%nProud před zaokrouhlením: %.6f A%nÚbytek na vnitřním odporu: %.6f V%nNapětí na zátěži před zaokrouhlením: %.6f V%nVýstupní napětí: %.2f V%nVýstupní proud: %.2f A%n", v.celkovyOdpor, v.proud, v.ubytek, v.vystupniNapeti, v.vystupniNapeti, v.proud);
        } catch (IllegalArgumentException e) {
            System.err.println("Chybný vstup: " + e.getMessage());
            System.exit(1);
        }
    }
}
