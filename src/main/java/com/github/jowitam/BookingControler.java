package com.github.jowitam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controler w którym jest metoda GET i POST
 * dla GET dla zasobu /booking wroci wynik  {"available": true}
 * dla POST możliwa jest rezerwacja pokoju (informacja przesłana JSON)
 */

@RestController
public class BookingControler {

    @Autowired//spring wstrzyknie obiekt bedacy implementacja tego interfejsu
    private ReservationRepository repository;

    @RequestMapping("/booking")
    public Booking booking(@RequestParam("from") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate from,
                           @RequestParam("to") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate to) {
/**
 *aby sprawdzic dostepnosc dla podanego zakresu od do muszę po kolei dla podanych liczb
 * z zakresu sprawdzić na liście rezerwacji czy tam wystepują jak jakakolwiek
 * jest to dostepnosci brak
 */

        if (!from.isBefore(to)) {//jezeli nie prawda jest ze from jest przed to
            throw new WrongDateRangeException(from, to); //WYJATEK wyrzucenie nowego wyjatku ktory jest obslugiwany przez Springa  - pisany w osobnej klasie
        }

        List<Reservation> listReservation = repository.findByReservationDataBetween(from, to.minusDays(1));

        if(listReservation.size() == 0){
            return new Booking(true);
        }else{
            return new Booking(false);
        }


    }

    /**
     * rezerwacja po przez wysłanie POST w formacie JSON
     */

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public void createBooking(@RequestBody NewBooking newBooking) {
        LocalDate now = LocalDate.now();
        LocalDate from = newBooking.getFrom();
        if (from.isBefore(now)) {
            throw new WrongDateRangeException(from);
        }
        if (booking(newBooking.getFrom(), newBooking.getTo()).getAvailable()){
                for (LocalDate day = newBooking.getFrom(); day.isBefore(newBooking.getTo()); day = day.plusDays(1)) {
                   repository.save(new Reservation(day));
                }
        } else {
            throw new ReservedRoomException();//WYJATEK ze pokoj jest zarezerwowany
        }
    }

}
