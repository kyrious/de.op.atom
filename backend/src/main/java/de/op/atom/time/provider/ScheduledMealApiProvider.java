package de.op.atom.time.provider;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import de.op.atom.gen.scheduling.model.ScheduledMealDTO;
import de.op.atom.gen.scheduling.model.TimeSlotDTO;
import de.op.atom.time.domain.entity.ScheduledMeal;
import de.op.atom.time.mapper.ScheduledMealMapper;
import de.op.atom.time.service.ScheduledMealService;

@ApplicationScoped
public class ScheduledMealApiProvider {
    @Inject
    ScheduledMealMapper scheduledMealMapper;
    @Inject
    ScheduledMealService scheduledMealService;

    public Response getScheduledMeals(LocalDate from, LocalDate to) {
        List<ScheduledMeal> scheduledMeals = scheduledMealService.getScheduledMealsInTimeSpan(from, to);
        List<ScheduledMealDTO> dto = scheduledMeals.stream()
                                                   .map(this.scheduledMealMapper::createDtoFromEntity)
                                                   .collect(Collectors.toList());
        return Response.ok(dto)
                       .build();
    }

    public Response deleteScheduledMeal(LocalDate date, TimeSlotDTO timeSlot) {
        // TODO Auto-generated method stub
        return null;
    }

    public Response putScheduledMeal(LocalDate date, TimeSlotDTO timeSlot, ScheduledMealDTO scheduledMealDTO) {
        // TODO Auto-generated method stub
        return null;
    }

}
