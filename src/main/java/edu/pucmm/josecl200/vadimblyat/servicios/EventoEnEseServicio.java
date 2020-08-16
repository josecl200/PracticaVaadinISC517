package edu.pucmm.josecl200.vadimblyat.servicios;

import edu.pucmm.josecl200.vadimblyat.entidades.EventoEnEsePunto;
import edu.pucmm.josecl200.vadimblyat.repos.EventoEnEsePuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.calendar.CalendarItemTheme;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class EventoEnEseServicio {
    @Autowired
    private EventoEnEsePuntoRepository er;

    public List<EventoEnEsePunto> listEvents() {
        return er.findAll();
    }

    public List<EventoEnEsePunto> findEventByDate(Date date) {
        return er.findAllByDate(date);
    }

    public List<EventoEnEsePunto> findEventInDateRange(Date start, Date end) {
        return er.findByDatesBetween(start, end);
    }

    public EventoEnEsePunto findEventById(long id){
        return er.getOne(id);
    }

    @Transactional
    public EventoEnEsePunto createEvent(long id, Date date, String title, CalendarItemTheme color) {
        return er.save(new EventoEnEsePunto(id, date, title, color));
    }

    public void editEvent(long EventoID) throws Exception {
        try {
            EventoEnEsePunto eventoEnEsePunto = findEventById(EventoID);
            er.save(eventoEnEsePunto);
        } catch (PersistenceException e) {
            throw new PersistenceException("Hubo un error al editar el evento.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el evento hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un evento.");
        }
    }

    @Transactional
    public boolean deleteEvent(EventoEnEsePunto eventoEnEsePunto) {
        er.delete(eventoEnEsePunto);
        return true;
    }
}
