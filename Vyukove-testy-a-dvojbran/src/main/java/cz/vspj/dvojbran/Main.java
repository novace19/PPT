package cz.vspj.dvojbran;

import java.util.Locale;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        try {
            Scanner vstup = new Scanner(System.in);
            System.out.print("Zadej napětí zdroje (V): ");
            double napeti = Double.parseDouble(vstup.nextLine().trim().replace(',', '.'));
            System.out.print("Zadej vnitřní odpor (Ω): ");
            double odpor = Double.parseDouble(vstup.nextLine().trim().replace(',', '.'));
            System.out.print("Zadej odpor zátěže (Ω): ");
            double zatez = Double.parseDouble(vstup.nextLine().trim().replace(',', '.'));
            Dvojbran.Vysledek v = Dvojbran.vypocitej(napeti, odpor, zatez);
            System.out.printf(Locale.US, "Celkový odpor: %.6f Ω%nProud před zaokrouhlením: %.6f A%nÚbytek na vnitřním odporu: %.6f V%nNapětí na zátěži před zaokrouhlením: %.6f V%nVýstupní napětí: %.2f V%nVýstupní proud: %.2f A%n", v.celkovyOdpor, v.proud, v.ubytek, v.vystupniNapeti, v.vystupniNapeti, v.proud);
        } catch (IllegalArgumentException | java.util.NoSuchElementException e) {
            System.err.println("Chybný vstup: " + e.getMessage());
            System.exit(1);
        }
    }
}
