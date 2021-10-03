package de.op.atom.time.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.modelmapper.convention.MatchingStrategies;

import de.op.atom.core.AbstractEntityMapper;
import de.op.atom.gen.scheduling.model.ScheduledMealDTO;
import de.op.atom.time.domain.entity.ScheduledMeal;

@ApplicationScoped
public class ScheduledMealMapper extends AbstractEntityMapper<ScheduledMealDTO, ScheduledMeal> {

    protected ScheduledMealMapper(Class<ScheduledMealDTO> dtoClass, Class<ScheduledMeal> entityClass) {
        super(dtoClass, entityClass);
        super.modelMapper.getConfiguration()
                         .setMatchingStrategy(MatchingStrategies.LOOSE);
        super.modelMapper.validate();
    }

}
