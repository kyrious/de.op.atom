package de.op.atom.time.domain.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.op.atom.foods.domain.entity.Recipe;
import de.op.atom.time.domain.entity.pk.ScheduledMealPrimaryKey;
import de.op.atom.time.domain.enums.TimeSlot;

@Entity
@IdClass(ScheduledMealPrimaryKey.class)
@NamedQueries({
        @NamedQuery(name = ScheduledMeal.SELECT_ALL_SCHEDULED_MEALS_IN_TIME_SPAN, query = "select e from ScheduledMeal e where e.date >= :from and e.date <= :to") })
public class ScheduledMeal {

    public static final String SELECT_ALL_SCHEDULED_MEALS_IN_TIME_SPAN = "ScheduledMeal.SELECT_ALL_SCHEDULED_MEALS_IN_TIME_SPAN";

    @Id
    private TimeSlot timeSlot;
    @Id
    private LocalDate date;
    private Recipe scheduledRecipe;

    public ScheduledMeal() {
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Recipe getScheduledRecipe() {
        return scheduledRecipe;
    }

    public void setScheduledRecipe(Recipe scheduledRecipe) {
        this.scheduledRecipe = scheduledRecipe;
    }

}
