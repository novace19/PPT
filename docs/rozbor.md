# Rozbor problému

## a) Vyhodnocení prospěchu
Vstup validujeme před hodnocením. Dělení musí proběhnout v double. Pětku kontrolujeme před vyznamenáním; nízký průměr nesmí skrýt neprospěch. Samotný průměr také neodhalí trojku, která brání vyznamenání.

Modelová chyba: `return soucet / znamky.length;`, oba operandy jsou celá čísla. Java nejprve provede celočíselné dělení a až pak převede výsledek na double. Oprava: `return (double) soucet / znamky.length;`. Čtením kódu ji poznáme podle typů operandů. Test ji odhalí na neceločíselném průměru. Součet má typ long, aby nepřetekl při velkém poli.

## b) Otevřený interval
Podmínka `odkud < value && value < kam`. Použití <= na jedné straně odhalí test přesně na příslušné mezi. Použití || odhalí test mimo interval. Žádné odčítání, tedy ani riziko přetečení pro krajní int.

## c) Sestupné vzorky
Počet = floor((odkud-kam)/krok)+1. Při opakovaném odečítání double může 0,3 - 0,1 - 0,1 - 0,1 minout nulu. BigDecimal.valueOf převádí vstup na jeho kanonickou desetinnou reprezentaci. Každý člen počítáme od začátku; konečný výstup zůstává double[]. Krok musí být kladný, jinak hrozí nekonečná smyčka v naivní implementaci.

## Návrh pokrytí
| Oblast | Případy |
| --- | --- |
| Průměr | desetinný výsledek, null, prázdné pole, známka mimo 1–5 |
| Prospěch | vyznamenání, přesně 1,5, nad 1,5, trojka i při 1,5, čtyřka, pětka s nízkým průměrem |
| Interval | pod mezí, obě meze, uvnitř, nad mezí, obrácené/shodné meze, záporné i krajní int |
| Vzorky | celý/desetinný krok, nedosažitelný konec, stejné/záporné/obrácené meze, velký/neplatný krok, nekonečné vstupy, limit a rozlišení double |

Jde o funkční a hraniční pokrytí. Procentuální pokrytí instrumentačním nástrojem se zde netvrdí.
