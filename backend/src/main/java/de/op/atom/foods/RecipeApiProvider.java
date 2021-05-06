package de.op.atom.foods;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.core.ErrorResponseBuilder;
import de.op.atom.foods.domain.entity.Ingredient;
import de.op.atom.foods.domain.entity.Recipe;
import de.op.atom.foods.domain.entity.RecipePart;
import de.op.atom.foods.mapper.RecipeMapper;
import de.op.atom.foods.services.IngredientService;
import de.op.atom.foods.services.RecipeService;
import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.foods.model.RecipeDTO;
import de.op.atom.gen.foods.model.RecipePartDTO;

@ApplicationScoped
public class RecipeApiProvider {

    @Inject
    RecipeService recipeService;
    @Inject
    RecipeMapper recipeMapper;
    @Inject
    IngredientService ingredientService;

    @Transactional
    public Response getRecipes(SecurityContext securityContext) throws NotFoundException {
        List<Recipe> allRecipes = this.recipeService.getAllRecipes();
        List<RecipeDTO> allRecipeDTOs = allRecipes.stream()
                                                  .map(this.recipeMapper::createDtoFromEntity)
                                                  .collect(Collectors.toList());
        return Response.ok(allRecipeDTOs)
                       .build();
    }

    @Transactional
    public Response getRecipebyId(Long id, SecurityContext securityContext) throws NotFoundException {
        Recipe recipeWithId = this.recipeService.getRecipeWithId(id);
        RecipeDTO dto = this.recipeMapper.createDtoFromEntity(recipeWithId);
        return Response.ok(dto)
                       .build();
    }

    @Transactional
    public Response postNewRecipe(RecipeDTO recipeDTO, SecurityContext securityContext) throws NotFoundException {
        Response validationError = validateRecipeDTOForPostNew(recipeDTO);
        if (validationError != null) {
            return validationError;
        }
        Recipe newRecipeEntity = this.recipeMapper.createEntityFromDto(recipeDTO);
        enhanceRecipe(newRecipeEntity);
        this.recipeService.updateOrCreate(newRecipeEntity);
        RecipeDTO returnedDto = this.recipeMapper.createDtoFromEntity(newRecipeEntity);
        return Response.ok(returnedDto)
                       .build();
    }

    private void enhanceRecipe(Recipe recipe) {
        recipe.getParts()
              .forEach(this::enhanceRecipePart);
    }

    private void enhanceRecipePart(RecipePart recipePart) {
        Long id = recipePart.getIngredient()
                            .getId();
        Ingredient proxy = this.ingredientService.getIngredientWithIdForReferences(id);
        recipePart.setIngredient(proxy);
    }

    @Transactional
    public Response putRecipeToId(Long id, RecipeDTO recipeDTO, SecurityContext securityContext) {
        Response validationError = validateRecipeDtoForPut(id, recipeDTO);
        if (validationError != null) {
            return validationError;
        }
        try {
            Recipe newRecipeEntity = this.recipeMapper.createEntityFromDto(recipeDTO);
            this.recipeService.updateOrCreate(newRecipeEntity);
            RecipeDTO returnedDto = this.recipeMapper.createDtoFromEntity(newRecipeEntity);
            return Response.ok(returnedDto)
                           .build();
        } catch (OptimisticLockException e) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "Given entity was out of date. Reread the entity, make your changes and re-put it again.")
                                       .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok()
                           .build();
        }
    }

    @Transactional
    public Response deleteRecipeWithId(Long id, SecurityContext securityContext) {
        if (id == null) {
            return Response.status(Status.BAD_REQUEST)
                           .build();
        }
        try {
            this.recipeService.deleteRecipeWithId(id);
            return Response.ok()
                           .build();
        } catch (NoSuchElementException e) {
            return Response.status(Status.NOT_FOUND)
                           .build();
        }
    }

    private Response validateRecipeDTOForPostNew(RecipeDTO recipeDTO) {
        if (recipeDTO.getVersion() != null) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "There must not be a version, when creating a new entity.")
                                       .build();
        } else if (recipeDTO.getId() != null) {
            return ErrorResponseBuilder.error(Status.CONFLICT, "There must not be an ID, when creating a new entity.")
                                       .build();
        }
        return validateRecipePartDto(recipeDTO);
    }

    private Response validateRecipeDtoForPut(Long id, RecipeDTO recipeDTO) {
        if (recipeDTO.getVersion() == null) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "There must be a version, when updating an existing entity")
                                       .build();
        }
        if (recipeDTO.getId() == null) {
            return ErrorResponseBuilder.error(Status.CONFLICT, "There must be an ID, when updating an existing entity")
                                       .build();
        }
        if (!Objects.equals(recipeDTO.getId(), id)) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "Given ID must match entities ID, when updating an existing entity. Pathparameter-ID='%d'; Entity-ID='%d'",
                                              id,
                                              recipeDTO.getId())
                                       .build();
        }
        return validateRecipePartDto(recipeDTO);
    }

    private Response validateRecipePartDto(RecipeDTO recipeDTO) {
        if (recipeDTO.getParts() != null) {
            for (RecipePartDTO part : recipeDTO.getParts()) {
                if (part.getIngredient() == null) {
                    return ErrorResponseBuilder.error(Status.BAD_REQUEST,
                                                      "There must be a Ingredient present for each RecipePart, when creating a new entity.")
                                               .build();
                } else if (part.getIngredient()
                               .getId() == null) {
                    return ErrorResponseBuilder.error(Status.BAD_REQUEST,
                                                      "Each ingredient must contain an ID, when creating a new entity.")
                                               .build();
                }
            }
        }
        return null;
    }
}
