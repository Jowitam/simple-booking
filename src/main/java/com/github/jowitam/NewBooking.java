package com.github.jowitam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

/**
 * klasa tworząca format daty w JSON ddMMyyyy i pozwalająca na operowanie
 * datami from i to. (od, do)
 */
public class NewBooking {
    @JsonFormat(pattern = "ddMMyyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate from;

    @JsonFormat(pattern = "ddMMyyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate to;

   public void setFrom(LocalDate from){
       this.from = from;
   }

    public void setTo(LocalDate to){
        this.to = to;
    }

    public LocalDate getFrom(){
        return from;
    }

    public LocalDate getTo(){
        return to;
    }
}
