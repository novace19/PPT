# T18_cviceni-CSV — studenti, třídy a známky

Samostatné cvičení skupiny T18 v Javě 17. Načítá všechny tři zadané formáty seznamů studentů a známek, včetně pojmenovaných předmětů v libovolném pořadí. Rozdělí seznam do souborů podle tříd a vytvoří přehledy prospěchu. Zadání nevyžaduje GUI, aplikace má konzolové ovládání.

## Nejrychlejší spuštění

1. Rozbalte ZIP do složky.
2. Na Windows v Průzkumníku dvakrát klikněte na **spustit.bat**. Spouštěč najde JDK stažené přes IntelliJ ve složce `%USERPROFILE%\.jdks`, případně použije `JAVA_HOME` nebo `java` z PATH. Potřebujete Java 17 nebo novější; není nutné nastavovat PATH, pokud již máte JDK stažené přes IntelliJ.
3. Ukázka načte data z `data/`, vypíše výsledky a vytvoří novou složku `vystup-demo-ČÍSLO`. Každé spuštění ukázky použije novou složku.

Spouštěč funguje i ze síťové složky (UNC) pomocí `pushd`, cesty s mezerami jsou uzavřené v uvozovkách. Spouští přiložený JAR přímo, takže nevyžaduje kompilaci ani import projektu v IntelliJ. Chyby zůstávají v otevřeném okně. Soubor musí být rozbalený vedle JARu a složky `data`.

Na Linuxu/macOS: `sh spustit.sh`.

## IntelliJ IDEA

Přes **File → Open** vyberte přímo soubor `pom.xml` v této složce a otevřete jej jako projekt. Nastavte JDK 17 nebo novější, počkejte na import Maven a spusťte `src/cz/vspj/t18/csv/Main.java`. Ukázka si najde složku `data` automaticky podle umístění JARu nebo přeložených tříd, takže pracovní adresář IntelliJ nemusíte pro ukázku nastavovat.

Přiložený `T18_cviceni-CSV.jar` už je sestavený; Maven potřebujete pro nové sestavení a testy.

## Vlastní vstupy

```sh
java -jar T18_cviceni-CSV.jar data/studenti-ii.csv ii data/znamky-iii-named.csv iii-named vystup-vlastni
```

Parametry jsou v pořadí: **soubor studentů, formát studentů, soubor známek, formát známek, nová výstupní složka**. Výstupní složka nesmí existovat; použijte pokaždé nový název. Soubory se načítají v UTF-8, podporováno je i UTF-8 BOM. Pořadí studentů ve známkách musí odpovídat pořadí v seznamu stejné třídy.

| Formát | Seznam studentů | Známky |
|---|---|---|
| `i` | Zkratka třídy, řádky `jméno;příjmení`, prázdný řádek mezi třídami | Zkratka třídy, řádek pěti známek na studenta, prázdný řádek mezi třídami |
| `ii` | Zkratka třídy, počet studentů, řádky `jméno;příjmení` | Zkratka třídy, počet sad, řádek pěti známek na studenta |
| `iii` | Zkratka třídy, počet, jméno a příjmení na samostatných řádcích | Zkratka třídy, počet sad, každá známka na samostatném řádku |

Pro známky přidejte `-named`, pokud používáte pojmenované předměty: `i-named`, `ii-named`, `iii-named`. V prvních dvou variantách např. `MAT:1;FYZ:2;IT:1;CJ:1;TV:5`; ve třetí variantě jedna dvojice na řádek. Musí být uvedeno všech pět předmětů právě jednou. Bez názvů platí pořadí **MAT, FYZ, IT, TV, CJ**. Všechny kombinace formátu studentů a formátu známek lze míchat.

## Výstupy

- `7A.csv`, `7B.csv`, …: každá třída ve stejném formátu jako vstupní seznam studentů; zachované pořadí a víceslovná jména.
- `prehledy/studenti.csv`: pořadové číslo, oddělená jména a příjmení, známky, průměr a prospěch.
- `prehledy/tridy.csv`: počet studentů a průměry třídy po předmětech.

Čísla studentů začínají v každé třídě od 1. Průměr je aritmetický; známka 5 znamená `NEPROSPĚL`, ostatní kombinace `PROSPĚL`. Kritéria vyznamenání zadání neurčuje, proto se neodvozují. U prázdné třídy se místo průměru vypíše pomlčka. Export zaokrouhluje až při zobrazení na dvě desetinná místa.

## Sestavení a testy

```sh
mvn clean package
java -jar target/T18_cviceni-CSV.jar
```

Ověřeno: **70 testů, 0 chyb, 0 přeskočených**. Závislosti testů jsou v `pom.xml` (JUnit 5). Podrobnosti a popis návrhových vzorů najdete v [docs/Dokumentace.md](docs/Dokumentace.md), skutečný protokol v [docs/Vysledky_testu.txt](docs/Vysledky_testu.txt).


## Spuštění přímo v IntelliJ přes JAR

Přiložená konfigurace `CSV - ukazka` spouští hotový JAR. Pokud už máte vlastní konfiguraci typu **JAR Application**, stačí v ní ponechat cestu k `T18_cviceni-CSV.jar`; ukázková data se hledají automaticky vedle JARu. Při opravě existující instalace nahraďte starý JAR novým a spusťte stejnou konfiguraci znovu. Explicitní vlastní vstupní cesty zadané argumenty jsou nadále relativní k pracovní složce, pokud nejsou absolutní.

ZIP obsahuje `pom.xml`, `src`, `data` a JAR přímo v kořeni archivu. Po extrahování otevřete v IntelliJ složku, kde tyto položky skutečně jsou.
