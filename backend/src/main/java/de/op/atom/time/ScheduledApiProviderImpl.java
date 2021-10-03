package de.op.atom.time;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.api.ScheduleApiService;
import de.op.atom.gen.scheduling.model.ScheduledMealDTO;
import de.op.atom.gen.scheduling.model.TimeSlotDTO;
import de.op.atom.time.provider.ScheduledMealApiProvider;

@ApplicationScoped
public class ScheduledApiProviderImpl implements ScheduleApiService {

    @Inject
    ScheduledMealApiProvider scheduledMealProvider;

    @Override
    public Response getScheduledMeals(LocalDate from, LocalDate to, SecurityContext securityContext)
            throws NotFoundException {
        return scheduledMealProvider.getScheduledMeals(from, to);
    }
    
    @Override
    public Response deleteScheduledMeal(LocalDate date, String slot, SecurityContext securityContext)
            throws NotFoundException {
        TimeSlotDTO timeSlot = TimeSlotDTO.valueOf(slot);
        return scheduledMealProvider.deleteScheduledMeal(date, timeSlot);
    }

    @Override
    public Response putScheduledMeal(LocalDate date,
                                     String slot,
                                     ScheduledMealDTO scheduledMealDTO,
                                     SecurityContext securityContext)
            throws NotFoundException {
        TimeSlotDTO timeSlot = TimeSlotDTO.valueOf(slot);
        return scheduledMealProvider.putScheduledMeal(date, timeSlot, scheduledMealDTO);
    }


}
