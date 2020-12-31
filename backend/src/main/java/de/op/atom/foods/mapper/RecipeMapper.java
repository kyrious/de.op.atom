package de.op.atom.foods.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.modelmapper.convention.MatchingStrategies;

import de.op.atom.core.AbstractEntityMapper;
import de.op.atom.foods.domain.Recipe;
import de.op.atom.gen.foods.model.RecipeDTO;

@ApplicationScoped
public class RecipeMapper extends AbstractEntityMapper<RecipeDTO, Recipe> {

    protected RecipeMapper() {
        super(RecipeDTO.class, Recipe.class);
        super.modelMapper.getConfiguration()
                         .setMatchingStrategy(MatchingStrategies.LOOSE);
        super.modelMapper.validate();
    }
}
