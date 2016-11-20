# Booking room

Prosta usługa sieciowa obsługująca rezerwację pokoju hotelowego. Umożliwia sprawdzenie dostępności pokoju hotelowego oraz dodanie nowych rezerwacji dla wybranego zakresu dat.

Proste API Restowe - usługa sieciowa w języku Java z wykorzystaniem systemu zarządzania bazą danych. Komunikacja odbywa się w formacie JSON. Usługa oczekuje że daty przekazane jako parametry będą w formacie ddMMyyyy.
Dane dotyczące rezerwacji są zapisywane i odczytywane z bazy danych.

## Instrukcja użytkowania

### Sprawdzenie dostępności rezerwacji dla podanego zakresu dat
`GET /booking?from={OdKiedy}&to={DoKiedy}`

#### Parametry

|parametr|funkcja|
|---------|---|
|from|początkowa data rezerwacja w formacie ddMMyyyy, np. 14032016|
|to|końcowa data rezerwacji w formacie ddMMyyyy, np. 16032016|

#### Wynik

Zwraca komunikat w JSON

```
{
  "available": true
}
```

#### Kod odpowiedzi

|kod|wyjaśnienie|komunikat|
|-----|----|---|
|200|zwrócono poprawną wartość|brak|
|400|niepoprawny zakres dat - data `from` późniejsza niż data `to`|Niepoprawny zakres dat: {OdKiedy} - {DoKiedy}|
|400|data niezgodna z wymaganym formatem|domyślny komunikat Springa|

### Rezerwacja pokoju dla podanego zakresu dat

`POST /booking`

#### Parametry

Szablon treść żądania w formacie JSON
```
{"from": "{OdKiedy}", "to": "{DoKiedy}"}
```
|pole obiektu JSON|funkcja|
|---------|---|
|from|początkowa data rezerwacja w formacie ddMMyyyy, np. 14032016|
|to|końcowa data rezerwacji w formacie ddMMyyyy, np. 16032016|

#### Wynik

Brak treści odpowiedzi.



#### Kod odpowiedzi

|kod|wyjaśnienie|komunikat|
|-----|----|---|
|200|udana rezerwacja|brak|
|400|dla podanego zakresu dat rezerwacja niedostępna ze względu na wcześniejsze zarezerwowanie pokoju w wybranym terminie |Rezerwacja niedostępna|
|400|niepoprawny zakres dat - data `from` późniejsza niż data `to`|Niepoprawny zakres dat: {OdKiedy} - {DoKiedy}|
|400|data niezgodna z wymaganym formatem|domyślny komunikat Springa|


## Jak uruchomić

### Wymagania
- Java 8
- PostgreSQL  albo MySQL 5.7

### Wykonanie krok po kroku dla WINDOWS
- budowanie pliku jar: w konsoli wpisujemy `gradlew.bat build`. Plik jar utworzy się w katalogu `\build\libs\`
- skonfigurować plik `application.properties` znajdujący się w głównym katalogu projektu. Konfiguracja zależy od tego jaką bazę chcemy podłączyć (projekt wstępnie przygotowany do obsługi PostgreSQL i MySQL).
- następne należy uruchomić aplikację: w konsoli wpisujemy `java -jar build\libs\[nazwaPliku].jar`
