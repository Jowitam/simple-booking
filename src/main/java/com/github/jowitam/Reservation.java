// tag::sample[]
package com.github.jowitam;
/**
 *zapis do bazy
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Reservation implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private LocalDate reservationData;


    protected Reservation() {}

    public Reservation(LocalDate reservationData) {

        this.reservationData = reservationData;
    }


}

