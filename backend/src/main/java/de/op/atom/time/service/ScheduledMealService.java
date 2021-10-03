package de.op.atom.time.service;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.op.atom.time.domain.entity.ScheduledMeal;
import de.op.atom.time.domain.entity.pk.ScheduledMealPrimaryKey;
import de.op.atom.time.domain.enums.TimeSlot;

@ApplicationScoped
public class ScheduledMealService {

    @PersistenceContext
    private EntityManager em;

    public ScheduledMeal getScheduledMeal(LocalDate date, TimeSlot timeSlot) {
        return em.find(ScheduledMeal.class, new ScheduledMealPrimaryKey(timeSlot, date));
    }

    public List<ScheduledMeal> getScheduledMealsInTimeSpan(LocalDate from, LocalDate to) {
        TypedQuery<ScheduledMeal> nq = em.createNamedQuery(ScheduledMeal.SELECT_ALL_SCHEDULED_MEALS_IN_TIME_SPAN, ScheduledMeal.class);
        
        nq.setParameter("from", from);
        nq.setParameter("to", to);
        
        return nq.getResultList();
    }

}
