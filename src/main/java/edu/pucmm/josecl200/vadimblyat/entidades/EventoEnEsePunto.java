package edu.pucmm.josecl200.vadimblyat.entidades;
import lombok.*;
import org.vaadin.calendar.CalendarItemTheme;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class EventoEnEsePunto implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private String title;
    private CalendarItemTheme color;
}
