# T18_ukol-03 — Kalkulačka faktoriálu

Samostatný projekt v Javě 17 se Swing GUI, třemi vrstvami a testy JUnit 5.

Výpočet a původních 32 testů jsou skutečně převzaty z vašeho odevzdaného projektu 01. `MyMath.factorial` používá `CalcFaktorial.calc2` a vlastní `FaktorialException`. Původní soubory jsou zkopírovány beze změn; jejich původ a kontrolní součty jsou v `docs/Puvod_kodu.json`.

## Otevření v IntelliJ IDEA

1. Rozbalte `T18_ukol-03.zip` a otevřete přímo složku `T18_ukol-03` (ne celý repozitář PPT).
2. Nastavte Project SDK na JDK 17 nebo novější. Soubor `pom.xml` načtěte jako Maven projekt.
3. Otevřete `src/cz/vspj/t18/Main.java` a spusťte zelenou šipkou metodu `main`.
4. Testy spusťte přes složku `test` → Run All Tests nebo přes Maven → Lifecycle → test.

Přiložený `T18_ukol-03.jar` lze rovnou spustit příkazem `java -jar T18_ukol-03.jar` (Java 17 nebo novější).

Bez Maven lze na Windows spustit `spustit.bat` (musí být nainstalovaný JDK 17 a `javac` v PATH). Na Linuxu/macOS použijte `sh spustit.sh`.

## Sestavení a testování

```sh
mvn clean package
java -jar target/T18_ukol-03.jar
```

Na Linuxu bez grafického displeje:

```sh
xvfb-run -a mvn clean package
# Přegenerování snímků při GUI testu:
xvfb-run -a mvn -Dscreenshots=true test
```

GUI test vyžaduje aktivní grafický displej, ovládá skutečné okno přes `java.awt.Robot`. Při běhu testu nemanipulujte s myší a klávesnicí. Testy bez displeje neskrýváme ani nepřeskakujeme.

## Obsah

- `src/` — aplikace; `Main.java` sestavuje vrstvy.
- `test/` — 32 původních testů CalcFaktorial a 28 doplněných testů MyMath, služby a GUI.
- `docs/Dokumentace.html` — samostatná dokumentace s vloženými snímky, lze otevřít v prohlížeči a vytisknout.
- `docs/Dokumentace.md` — dokumentace pro čtení na GitHubu.
- `docs/Vysledky_testu.txt` — skutečný protokol: 60 testů, 60 úspěšných, 0 chyb, 0 přeskočených.
- `docs/screenshots/` — čtyři snímky běžící aplikace z GUI testu.

Přesný rozsah: 0 až 12, návratový typ `int`; 0! = 1. Hodnota 13 a vyšší, prázdný vstup, desetinné číslo nebo nečíselný text vytvoří chybový řádek. Záporné číslo má vlastní zprávu. Historie je pouze v operační paměti a po zavření aplikace se neukládá.
