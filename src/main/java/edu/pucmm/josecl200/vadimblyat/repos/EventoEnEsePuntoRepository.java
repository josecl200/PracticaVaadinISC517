package edu.pucmm.josecl200.vadimblyat.repos;

import edu.pucmm.josecl200.vadimblyat.entidades.EventoEnEsePunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventoEnEsePuntoRepository extends JpaRepository<EventoEnEsePunto,Long> {
    List<EventoEnEsePunto> findAllByDate(Date date);

    @Query("select event from EventoEnEsePunto event where event.date between ?1 and ?2")
    List<EventoEnEsePunto> findByDatesBetween(Date start, Date end);
}
