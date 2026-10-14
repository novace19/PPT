# PPT – rozbor problému a testování

Java 17, Maven, JUnit 5. Autor: Vojtěch Nováček.

## Otevření v IntelliJ IDEA
1. Rozbalte ZIP a otevřete složku PPT (File → Open, vyberte pom.xml jako projekt).
2. Nastavte Project SDK na JDK 17 nebo novější a nechte Maven stáhnout závislosti.
3. Pravým tlačítkem na src/test/java → Run All Tests, případně v panelu Maven spusťte Lifecycle → test.
4. Dokumentaci otevřete v docs/apidocs/index.html. Nově ji vytvoří `mvn javadoc:javadoc` (target/site/apidocs).

Příkazové ověření: `mvn clean test javadoc:javadoc`.

## Výslovné předpoklady zadání
Původní kód ani rozbor cvičení 02 nebyly poskytnuty. Toto je samostatná referenční implementace, nikoli tvrzení o chybě v neznámém původním kódu.
- Známky 1–5, neprázdné pole. Pětka znamená neprospěl. Vyznamenání: průměr <= 1,5 a žádná známka horší než 2. Ostatní platné případy: prospěl. Známka z chování není vstupem.
- Interval je otevřený na obou stranách. Obrácené a shodné meze vracejí false.
- `vzorkySestupně(double kam, double odkud, double krok)` zachovává pořadí ze zadání. Začíná v odkud, sestupuje kladným krokem; kam nepodkročí. Dosažitelný konec zahrnuje. Stejné meze dávají jeden vzorek. Neplatné vstupy vyvolají výjimku.
- Limit milion vzorků chrání paměť. Pokud double nerozliší sousední vzorky, metoda vyvolá výjimku.

## Rozbor a odhalení chyby
Viz [docs/rozbor.md](docs/rozbor.md). Ukázková chybná varianta je mimo src, v examples/chyba; nesmí být použita jako výsledná implementace.
Test `desetinnaCastPrumeru` očekává pro [1,2] hodnotu 1,5. Chybná varianta vrátí 1,0.
Test `nadHraniciVyznamenani` očekává pro [1,2,2] „prospěl“. Při celočíselném dělení vyjde 1,0 a chybně vyznamenání.

## Obsah odevzdání
Implementace v src/main/java, testy v src/test/java, rozbor a vygenerovaná Javadoc v docs, skutečné výsledky ověření v docs/overeni.txt.
