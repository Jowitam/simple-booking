package com.github.jowitam;

/**
 * interfejs niezbędny dla springa.
 * Spring wychwytuje szukanie rezerwacji po dacie pomiędzy datami from i to
 */

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByReservationDataBetween(LocalDate from, LocalDate to);
}
