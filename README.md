# Forma-1 Történelmi Adatbázis

Ez a repository egy Forma-1 történelmi adatbázis alkalmazást tartalmazza, amely lehetővé teszi a versenyek eredményeinek bevitelét és a világbajnoki szezonok adatainak kinyerését. Az alkalmazás célja, hogy egy egyszerű, de hatékony eszközt nyújtson az F1 történelem adatainak kezelésére és elemzésére.

**Működési elv:**

Az alkalmazás egy fájlból olvas be adatokat, amelyek szigorúan definiált parancsformátumot követnek. Ez a formátum lehetővé teszi a versenyek, eredmények és egyéb releváns információk strukturált bevitelét. A parancsok pontosvesszővel elválasztott részekből állnak, ahol az első rész a parancsszó.

**Támogatott parancsok és funkciók:**

*   **RACE (Verseny):** Ezzel a paranccsal lehet egy új versenyt bevinni az adatbázisba. Meg kell adni az évszámot, a nagydíj nevét, a verseny sorszámát az adott évben, valamint egy pontszorzót. Ez utóbbi lehetővé teszi a speciális versenyek (pl. dupla pontot érő futamok) kezelését.

*   **RESULT (Eredmény):** A `RACE` parancs után kiadva lehet bevinni a versenyzők eredményeit az adott versenyhez. Meg kell adni a célba érkezési pozíciót, a versenyző nevét és a csapat nevét.

*   **FASTEST (Leggyorsabb kör):** A `RACE` parancs után kiadva lehet rögzíteni a leggyorsabb kört futó versenyzőt és csapatát.

*   **FINISH (Befejezés):** Ez a parancs jelzi egy verseny bevitelének végét. Az alkalmazás ellenőrzi, hogy legalább 10 versenyző eredménye lett-e bevíve a versenyhez.

*   **QUERY (Lekérdezés):** Ezzel a paranccsal lehet lekérdezni az adatbázist egy adott évre vonatkozóan. Kétféle lekérdezés lehetséges: az adott év végeredményének lekérdezése, vagy egy adott verseny utáni állás lekérdezése.

*   **POINT (Pontszámítás):** A `QUERY` parancs után kiadva lehet beállítani a lekérdezéshez használt pontszámítási módszert. A következő módszerek támogatottak:
    *   `CLASSIC`: A klasszikus pontozási rendszer.
    *   `MODERN`: Egy modernebb pontozási rendszer.
    *   `NEW`: Egy újabb pontozási rendszer.
    *   `PRESENT`: A jelenlegi pontozási rendszer, amely a leggyorsabb körért járó pontot is figyelembe veszi.

**Adattárolás és lekérdezések:**

Az alkalmazás belsőleg tárolja a beolvasott adatokat, ami lehetővé teszi a hatékony lekérdezéseket. A lekérdezések a kiválasztott pontozási rendszer alapján számítják ki az eredményeket.

**Hibakezelés:**

Az alkalmazás igyekszik a lehető legérthetőbb hibaüzenetekkel jelezni a helytelen bemenetet vagy parancsokat, segítve a felhasználót a hibák elhárításában.

**Technikai megvalósítás:**

Az alkalmazás Java nyelven készült, az objektumorientált programozás elveit követve. Ez biztosítja a kód átláthatóságát, karbantarthatóságát és bővíthetőségét.

**További fejlesztési lehetőségek:**

*   A bemeneti adatok részletesebb validálása.
*   A lekérdezések kimenetének formázásának javítása.
*   Teljesítmény optimalizálás nagyobb adathalmazok esetén.
*   Grafikus felhasználói felület (GUI) létrehozása.
