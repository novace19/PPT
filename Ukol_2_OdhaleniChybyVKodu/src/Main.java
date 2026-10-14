/** Krátká ukázka použití; automatické testy jsou ve složce tests. */
public class Main {
    /** Vytvoří objekt ukázkové aplikace. */
    public Main() { }
    /**
     * Vypíše ukázkové výsledky zadaných metod.
     * @param args nepoužité argumenty příkazové řádky
     */
    public static void main(String[] args) {
        System.out.println(new VyhodnoceniProspechu().prospech(new int[]{1, 2}));
        System.out.println(Zpracovani.interval(0, 10, 5));
        System.out.println(java.util.Arrays.toString(new Zpracovani().vzorkySestupně(0, 0.3, 0.1)));
        System.out.println(ZpracovaniMoje.comp("abc", "abd"));
    }
}
