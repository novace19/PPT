package cz.vspj.dvojbran;

/** Zdroj napětí se sériovým vnitřním odporem a odporovou zátěží.
 * I = U / (Ri + Rz), U2 = I * Rz; napětí ve V, odpory v ohmech. */
public final class Dvojbran {
    private Dvojbran() { }

    public static Vysledek vypocitej(double napeti, double vnitrniOdpor, double zatez) {
        if (!Double.isFinite(napeti) || !Double.isFinite(vnitrniOdpor) || !Double.isFinite(zatez)
                || vnitrniOdpor < 0 || zatez <= 0) {
            throw new IllegalArgumentException("Napětí musí být konečné, vnitřní odpor nezáporný a zátěž kladná.");
        }
        double celkovyOdpor = vnitrniOdpor + zatez;
        double proud = napeti / celkovyOdpor;
        double ubytek = proud * vnitrniOdpor;
        double vystupniNapeti = proud * zatez;
        if (!Double.isFinite(celkovyOdpor) || !Double.isFinite(proud)
                || !Double.isFinite(ubytek) || !Double.isFinite(vystupniNapeti)) {
            throw new IllegalArgumentException("Výsledek nelze vyjádřit konečným číslem.");
        }
        return new Vysledek(celkovyOdpor, proud, ubytek, vystupniNapeti);
    }

    public static final class Vysledek {
        public final double celkovyOdpor, proud, ubytek, vystupniNapeti;
        private Vysledek(double r, double i, double u, double vystup) {
            celkovyOdpor = r; proud = i; ubytek = u; vystupniNapeti = vystup;
        }
    }
}
