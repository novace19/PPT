# Výukové testy a zatížený dvojbran

Samostatný projekt Java 8+ a JUnit 5. Ve složce jsou výukové testy knihoven, program a jeho testy. Potřebujete JDK a Maven. V této složce spusťte:

```text
mvn test
mvn package
java -cp target/classes cz.vspj.dvojbran.Main 12 100 200
```

Čísla se zadávají s desetinnou tečkou: napětí ve voltech, vnitřní a zatěžovací odpor v ohmech. Program vypisuje mezivýsledky na šest míst a výsledky na dvě místa. Výpočet pracuje s nezaokrouhlenými hodnotami; zaokrouhluje se až při výpisu. Příklad dává celkový odpor 300 Ω, proud 0,04 A, úbytek 4 V a výstupní napětí 8 V. Chybný počet argumentů nebo neplatné hodnoty skončí chybovým kódem 1.

## Návrh dvojbranu

Zadání neuvádí schéma ani parametry obecného dvojbranu. Zde proto předpokládáme Theveninovu náhradu: ideální zdroj U se sériovým vnitřním odporem Ri a k němu připojená odporová zátěž Rz. Platí R = Ri + Rz, I₂ = U / R, úbytek Ui = I₂ × Ri a U₂ = I₂ × Rz. Proud je kladný ze zdroje do zátěže; záporné napětí obrátí jeho směr. Ri ≥ 0, Rz > 0; konečné vstupy i výsledky jsou kontrolovány. Zkrat ani nezatížený výstup zde nejsou modelovány.

## Co ověřují výukové testy

- `Math.sqrt`: odmocnina kladného čísla, nuly, záporného čísla (`NaN`) a nekonečna.
- `Math.tan`: vstup je v radiánech, ověřuje se nula, ±π/4 a nekonečno. U π/2 nelze spoléhat na přesnou reprezentaci pólu.
- `Math.toRadians` a `Math.toDegrees` převádějí stupně a radiány. V zadání je překlep `toDegress`; správný název je `toDegrees`. Hodnoty `double` se porovnávají s tolerancí.
- `List` je rozhraní, `ArrayList` jeho implementace. `indexOf` vrací index prvního výskytu od nuly nebo −1, pokud prvek chybí; test zahrnuje duplicitu, `null` a prázdný seznam.
- Slovník je v moderní Javě rozhraní `Map<K,V>`, např. implementace `HashMap`. Starší abstraktní třída `Dictionary` také existuje. Java `Map` nemá metodu `Add`; ekvivalentní vložení je `put(klic, hodnota)`. Nový klíč zvětší mapu, existující hodnotu nahradí a vrátí původní hodnotu. `get` vrací `null` jak u chybějícího klíče, tak u uložené hodnoty `null`, proto existenci ověřujeme `containsKey`. Další metody: `putIfAbsent`, `remove`, `containsValue`, `size`, `isEmpty`, `keySet`, `values`, `entrySet` a `clear`. `HashMap` připouští `null`, jiné implementace mohou mít odlišná omezení.

## Testování a role

`DvojbranTest` ověřuje výpočet, mezivýsledky a hranice přímo přes veřejnou metodu. `VnejsiTest` spouští program jako samostatný proces a kontroluje výstup, zaokrouhlení a chybové kódy. Je to připravený scénář externího testera; samostatnou týmovou práci ani nezávislé ověření jinou osobou tento projekt sám nezajišťuje.

Dokumentace: [Math](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Math.html), [List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html), [Map](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html).
