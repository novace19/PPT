# T18_ukol-03 — Dokumentace

## Zadání a výsledek

<p><strong>Skupina T18 · Úkol 03 · 4. listopadu 2026</strong></p><p>Byla vytvořena desktopová aplikace „Kalkulačka faktoriálu“ v Javě 17 s knihovnou Swing. Jedno textové pole přijímá celé číslo; tlačítko „Výpočet faktoriál“ spouští výpočet. Víceřádkový výstup zachovává předchozí výsledky a přidává každý požadavek na nový řádek. Volba „Zobrazit postup výpočtu“ doplní součin činitelů, tlačítko „Smazat text“ vymaže výstup i datovou historii. Výpočet lze spustit také Enterem ze vstupního pole.</p>

## Návaznost na projekt 01

Původní `CalcFaktorial.java`, `FaktorialException.java` a `CalcFaktorialTest.java` jsou převzaty beze změn ze skutečného odevzdání T18 v Moodlu. `MyMath.factorial` volá přímo `CalcFaktorial.calc2`; GUI používá variantu s výjimkou. Původní `calc1` zůstává zachována kvůli návaznosti a testům. Kontrolní součty: [Puvod_kodu.json](Puvod_kodu.json).

## Návrh tří vrstev

<table><tr><th>Vrstva</th><th>Třídy</th><th>Odpovědnost</th></tr><tr><td>Prezentační</td><td>CalculatorPanel</td><td>Swing komponenty, zpracování kliknutí, zobrazení vráceného výstupu. Neprovádí matematický výpočet.</td></tr><tr><td>Logická</td><td>MyMath, FactorialService, převzaté CalcFaktorial a FaktorialException</td><td>Faktoriál, převod vstupu, zachycení očekávaných výjimek, sestavení výstupního řádku a předání záznamu historii.</td></tr><tr><td>Datová</td><td>CalculationRecord, CalculationHistory</td><td>Neměnný záznam výsledku a seznam historie v paměti. Nezávisí na Swingu ani na logické vrstvě.</td></tr></table><p><code>Main</code> je vstupní bod a místo sestavení závislostí. Vytváří historii, předá ji službě a službu předá panelu. Závislosti směřují z prezentace do logiky a z logiky do dat. Datová vrstva nezná žádnou vyšší vrstvu. Její metoda <code>records()</code> vrací neměnnou kopii, takže klient nemůže změnit vnitřní seznam.</p><p>Okno vzniká a události se zpracovávají na Swing Event Dispatch Thread. Výpočet má nejvýše 11 násobení, proto zde není nutné zavádět práci na pozadí.</p>

## Algoritmus, rozsah a výjimky

<p>Metoda <code>MyMath.factorial(int n)</code> vrací <code>int</code>. Neobsahuje ovládací prvky ani textové vstupy. Deleguje na původní metodu calc2, která inicializuje výsledek na 1 a násobí čísly od 2 do n, takže 0! i 1! jsou rovny 1. Časová složitost je O(n), pomocná paměť O(1); po kontrole rozsahu je n nejvýše 12.</p><p>Hodnota 12! = 479001600 se do <code>int</code> vejde, 13! už ne. Rozsah je proto záměrně omezen na 0–12; nepoužívá se přibližný desetinný výpočet. Rozsah kontroluje původní metoda ještě před násobením, proto ve výpočtu nepřeteče int. Postup pro 0 se nevypisuje jako součin, protože jde o prázdný součin: výstup je <code>0! = 1</code>.</p><table><tr><th>Situace</th><th>Výjimka a zpracování</th><th>Výstup</th></tr><tr><td>Záporné číslo, např. −1</td><td>Původní CalcFaktorial.calc2 vyvolá FaktorialException; služba ji zachytí a zobrazí text podle zadání.</td><td>−1! =&gt; Chyba:Nelze spočítat faktoriál záporného čísla.</td></tr><tr><td>Prázdný vstup, „pět“, 5.0 nebo číslo mimo int</td><td>Integer.valueOf vyvolá NumberFormatException.</td><td>=&gt; Chyba:Vstupní údaj není správný.</td></tr><tr><td>Celé číslo větší než 12</td><td>Původní CalcFaktorial.calc2 vyvolá FaktorialException pro překročení rozsahu int.</td><td>=&gt; Chyba:Vstupní údaj není správný.</td></tr></table><p>Okolní mezery se odstraní pomocí <code>trim()</code>. MyMath propouští původní FaktorialException volajícímu a nemění její zprávu ani typ. Služba odlišuje záporný vstup od překročení rozsahu podle parsované hodnoty, nikoli podle textu výjimky. Služba převádí pouze očekávané výjimky na zprávy pro uživatele. NumberFormatException znamená chybu převodu textu. Vlastní kontrolovaná FaktorialException označuje záporný vstup nebo překročení rozsahu původního matematického výpočtu. Služba tyto chyby převádí na přesné uživatelské zprávy požadované v úkolu 03.</p>

## Postup realizace

<ol><li>V Moodlu bylo ověřeno zadání projektu 01 a staženo skutečné odevzdání skupiny T18 (T18_ukol-01.zip). Ze složky cz/pokrocile/faktorial byly beze změn převzaty CalcFaktorial.java, FaktorialException.java a CalcFaktorialTest.java. Shoda všech tří souborů je doložena SHA-256 v Puvod_kodu.json.</li><li>Třída MyMath poskytuje metodu factorial(int) a deleguje výpočet na převzatou CalcFaktorial.calc2(int). Nepřidává vlastní druhý algoritmus. Zůstává zachován původní návratový typ int, rozsah 0–12 a vlastní kontrolovaná FaktorialException.</li><li>Byla přidána datová historie a služba pro validaci a formátování výsledku.</li><li>Bylo vytvořeno GUI a propojeno se službou; zajištěn běh na EDT a výstup pouze pro čtení.</li><li>Byly přidány parametrizované testy a GUI scénář se skutečnými událostmi myši a klávesnice.</li><li>Aplikace byla přeložena, testy spuštěny a z reálného okna pořízeny čtyři snímky.</li></ol>

## Testování a výsledky

<p><strong>Výsledek ověření: 60 testů spuštěno, 60 úspěšných, 0 neúspěšných, 0 přeskočených.</strong> Kompletní protokol je přiložen v <code>Vysledky_testu.txt</code>. Běh proběhl na OpenJDK 17.0.20, JUnit 5.10.2, Maven 3.9.9 / Surefire 3.2.5, Linuxu a virtuálním displeji Xvfb. GUI test ovládal zobrazené okno přes <code>java.awt.Robot</code>; všechny Swing operace a kontroly komponent probíhají na EDT.</p><table><tr><th>Testovací třída</th><th>Počet</th><th>Pokrytí</th></tr><tr><td>CalcFaktorialTest (převzato beze změn)</td><td>32</td><td>Obě původní varianty calc1 a calc2, pevně dané výsledky, chybové vstupy, rekurentní vztah a shoda obou metod.</td></tr><tr><td>MyMathTest</td><td>14</td><td>0, 1, 2, 3, 5, 10, 11, 12; záporné hodnoty včetně Integer.MIN_VALUE; 13, 100 a Integer.MAX_VALUE.</td></tr><tr><td>FactorialServiceTest</td><td>13</td><td>10 neplatných vstupů, postup a mezery, zpráva záporného vstupu, historie, neměnnost kopie a smazání.</td></tr><tr><td>CalculatorUiTest</td><td>1 komplexní scénář</td><td>Skutečné zadání 5 klávesnicí, klikání, přepnutí postupu, 0 a 12, záporné a neplatné vstupy, nové řádky, vymazání výstupu i historie, výpočet Enterem a výstup pouze pro čtení.</td></tr></table><p>Parametrizované testy používají pevně zadané očekávané výsledky. Navíc se beze změn spouští původní testovací třída z projektu 01. Doplněné testy trojky ověřují přesné řetězce včetně nových řádků. Původní CalcFaktorialTest zůstává zcela beze změn; vedle pevných očekávaných hodnot obsahuje také testy rekurentního vztahu a shody obou variant. GUI test se bez displeje nezamlčí: skončí s jasnou instrukcí k použití Xvfb.</p>

## Doklady funkčnosti a diskuze

<p>Následující snímky jsou zachyceny ze skutečně spuštěného Swing okna během automatizovaného GUI testu. Vzhled odpovídá Linuxu bez správce oken; na Windows se může lišit dekorace a vykreslení písma.</p>

### Výchozí stav

![Výchozí stav](screenshots/01_start.png)

Prázdný výstup, jedno vstupní pole a všechny požadované ovládací prvky.


### Výsledky a postup výpočtu

![Výsledky a postup výpočtu](screenshots/02_vypocty.png)

Ve stejné historii je jednoduchý výstup 5! = 120, rozvinutý součin, 0! a nejvyšší podporovaný faktoriál 12!. Dlouhý řádek se vizuálně zalamuje; v textu zůstává jedním záznamem ukončeným novým řádkem.


### Chybové stavy

![Chybové stavy](screenshots/03_chyby.png)

Záporný vstup má specifickou zprávu. Text „pět“, hodnota 13, celé číslo mimo rozsah int a prázdný vstup mají obecnou chybu podle zadání. Aplikace po chybě zůstává použitelná.


### Vymazání historie

![Vymazání historie](screenshots/04_smazani.png)

Tlačítko Smazat text odstranilo výstup. Test zároveň ověřil prázdnou datovou historii a následně úspěšný nový výpočet Enterem.


## Zhodnocení a spuštění

Rozsah 0–12 odpovídá typu int. Historie je pouze v paměti. Ověřeno na Linuxu s OpenJDK 17 a Xvfb, nikoli na Windows. Postup spuštění je v [README](../README.md). Úplná samostatná dokumentace s vloženými snímky je v [Dokumentace.html](Dokumentace.html).
