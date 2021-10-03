package de.op.atom.time.domain.entity.pk;

import java.time.LocalDate;

import de.op.atom.time.domain.enums.TimeSlot;

public class ScheduledMealPrimaryKey {

    private TimeSlot timeSlot;
    private LocalDate date;

    public ScheduledMealPrimaryKey(TimeSlot timeSlot, LocalDate date) {
        this.timeSlot = timeSlot;
        this.date = date;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScheduledMealPrimaryKey other = (ScheduledMealPrimaryKey) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (timeSlot != other.timeSlot)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ScheduledMealPrimaryKey [timeSlot=" + timeSlot + ", date=" + date + "]";
    }

}
