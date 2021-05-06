package de.op.atom.foods.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.modelmapper.convention.MatchingStrategies;

import de.op.atom.core.AbstractEntityMapper;
import de.op.atom.foods.domain.entity.Ingredient;
import de.op.atom.gen.foods.model.IngredientDTO;

@ApplicationScoped
public class IngredientMapper extends AbstractEntityMapper<IngredientDTO, Ingredient> {

    protected IngredientMapper() {
        super(IngredientDTO.class, Ingredient.class);
        super.modelMapper.getConfiguration()
                         .setMatchingStrategy(MatchingStrategies.LOOSE);
        super.modelMapper.validate();
    }
}
