package de.op.atom.foods.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.op.atom.foods.domain.Ingredient;
import de.op.atom.foods.domain.IngredientCategory;
import de.op.atom.gen.foods.model.IngredientDTO;
import de.op.atom.gen.foods.model.IngredientDTO.CategoryEnum;
import de.op.atom.gen.foods.model.MakroNutrientsDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class IngredientMapperTest {
    
    @Inject
    private IngredientMapper mapper;

    private IngredientDTO ingredientDto;

    @BeforeEach
    public void initDto() {
        ingredientDto = new IngredientDTO();
        ingredientDto.setCategory(CategoryEnum.CEREAL);
        ingredientDto.setName("TestCereal");
        MakroNutrientsDTO makroNutrients = new MakroNutrientsDTO();
        makroNutrients.setCalories(0);
        makroNutrients.setCarbohydrates(0);
        makroNutrients.setFiber(0);
        makroNutrients.setProtein(0);
        makroNutrients.setSaturatedFat(0);
        makroNutrients.setSugar(0);
        makroNutrients.setUnsaturatedFat(0);
        ingredientDto.setMakroNutrients(makroNutrients);
    }

    private Ingredient ingredientEntity;

    @BeforeEach
    public void initEntity() {
        ingredientEntity = new Ingredient();
        ingredientEntity.setCategory(IngredientCategory.CEREAL);
        ingredientEntity.setName("TestCereal");
        ingredientEntity.setCalories(0);
        ingredientEntity.setCarbohydrates(0);
        ingredientEntity.setFiber(0);
        ingredientEntity.setProtein(0);
        ingredientEntity.setSaturatedFat(0);
        ingredientEntity.setSugar(0);
        ingredientEntity.setUnsaturatedFat(0);
    }

    @Test
    void testCreateDtoFromEntity() {
        IngredientDTO dto = mapper.createDtoFromEntity(this.ingredientEntity);

        assertEquals(ingredientDto.getName(), dto.getName());
        assertEquals(ingredientDto.getCategory(), dto.getCategory());
        assertEquals(ingredientDto.getMakroNutrients(), dto.getMakroNutrients());
    }
    
    @Test
    void testCreateEntityFromDto() {
        Ingredient entity = mapper.createEntityFromDto(this.ingredientDto);

        assertEquals(ingredientEntity.getName(), entity.getName());
        assertEquals(ingredientEntity.getCategory(), entity.getCategory());
        assertEquals(ingredientEntity.getCalories(), entity.getCalories());
        assertEquals(ingredientEntity.getCarbohydrates(), entity.getCarbohydrates());
        assertEquals(ingredientEntity.getFiber(), entity.getFiber());
        assertEquals(ingredientEntity.getProtein(), entity.getProtein());
        assertEquals(ingredientEntity.getSaturatedFat(), entity.getSaturatedFat());
        assertEquals(ingredientEntity.getSugar(), entity.getSugar());
        assertEquals(ingredientEntity.getUnsaturatedFat(), entity.getUnsaturatedFat());
    }

}
