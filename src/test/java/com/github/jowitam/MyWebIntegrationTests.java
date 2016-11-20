package com.github.jowitam;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyWebIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

//dostepnosc Dla Wybranego Zakresu Dat
    @Test
    public void shouldBeAvailable() throws JSONException {
        String body = this.restTemplate.getForObject("/booking?from=15062016&to=16062016", String.class);
        //assertThat(body).isEqualTo("{\"available\":true}");
        JSONAssert.assertEquals("{\"available\":true}", body, false);
    }

//tworzenie Rezerwacji
    @Test
    public void shouldBook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("{\"from\": \"16032116\", \"to\": \"14042116\"}", headers);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("/booking", entity, String.class);

        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    //dokonanie Rezerwacji Na Termin Juz Zajety Zwraca Blad
    @Test
    public void shouldReturn400WhenRoomIsBooked() {
        /*dodanie nagłówka Content-Type=application/json   -  bo aplikacja obsługuje jsony i trzeba mu powiedzieć ze
        przesyłamy jasona a nie jakis inny format*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>("{\"from\": \"16072016\", \"to\": \"18072016\"}", headers);
       //dokonanie rezerwacji na podany zakres
        restTemplate.postForEntity("/booking", entity, String.class);

        //proba ponownego stworzenia rezerwacji na tym samym terminie
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("/booking", entity, String.class);

        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //po sprawdzeniu dostępności nowa rezerwacja na ten sam termin Niedostępna
    @Test
    public void shouldReturnThatRoomIsBookAfterMakingReservation() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("{\"from\": \"16082116\", \"to\": \"18082116\"}", headers);
        restTemplate.postForEntity("/booking", entity, String.class);

        String body = this.restTemplate.getForObject("/booking?from=16082116&to=18082116", String.class);

        JSONAssert.assertEquals("{\"available\":false}", body, false);
    }



    //odwrotne podanie zakresu dat zwraca błąd
    @Test
    public void shouldReturn400WhenToIsBeforeFrom(){
       ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("/booking?from=18052016&to=16052016", String.class);
       assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //nie poprawny format daty zwraca błąd
    @Test
    public void shouldReturn400WhenDateFormatIsInvalid(){
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("/booking?from=dzisiaj&to=20160209", String.class);
        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //brak daty końcowej zwraca błąd
    @Test
    public void shouldReturn400WhenToParameterIsMissing(){
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("/booking?from=14032016", String.class);
        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //brak daty początkowej zwraca błąd
    @Test
    public void shouldReturn400WhenFromParameterIsMissing(){
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("/booking?to=14032016", String.class);
        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //podanie daty rok miesiąc dzień zwraca błąd
    @Test
    public void shouldReturn400WhenDateIsDifferentFormat(){
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("/booking?from=24052016&to=20160527", String.class);
        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    //podanie daty z przeszłości
    @Test
    public void shouldReturn400WhenDateFromPast(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("{\"from\": \"16032015\", \"to\": \"14042116\"}", headers);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("/booking", entity, String.class);

        assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(400);
    }

}