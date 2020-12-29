package de.op.atom.foods;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.gen.api.FoodsApiService;
import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.foods.model.IngredientDTO;
import de.op.atom.gen.foods.model.RecipeDTO;

@ApplicationScoped
public class FoodsApiProviderImpl implements FoodsApiService {

    @Inject
    IngredientApiProvider ingredientApiProvider;
    @Inject
    RecipeApiProvider recipeApiProvider;

    @Override
    public Response getIngredientbyId(Long id, SecurityContext securityContext) throws NotFoundException {
        return this.ingredientApiProvider.getIngredientbyId(id, securityContext);
    }

    @Override
    public Response getIngredients(SecurityContext securityContext) throws NotFoundException {
        return this.ingredientApiProvider.getIngredients(securityContext);
    }

    @Override
    public Response getRecipes(SecurityContext securityContext) throws NotFoundException {
        return this.recipeApiProvider.getRecipes(securityContext);
    }

    @Override
    public Response getRecipebyId(Long id, SecurityContext securityContext) throws NotFoundException {
        return this.recipeApiProvider.getRecipebyId(id, securityContext);
    }

    @Override
    public Response postNewIngredients(IngredientDTO ingredientDTO, SecurityContext securityContext)
            throws NotFoundException {
        return this.ingredientApiProvider.postNewIngredients(ingredientDTO, securityContext);
    }

    @Override
    public Response postNewRecipe(RecipeDTO recipeDTO, SecurityContext securityContext) throws NotFoundException {
        return this.recipeApiProvider.postNewRecipe(recipeDTO, securityContext);
    }

    @Override
    public Response putIngredientToId(Long id, IngredientDTO ingredientDTO, SecurityContext securityContext)
            throws NotFoundException {
        return this.ingredientApiProvider.putIngredientToId(id, ingredientDTO, securityContext);
    }

    @Override
    public Response putRecipeToId(Long id, RecipeDTO recipeDTO, SecurityContext securityContext)
            throws NotFoundException {
        return this.recipeApiProvider.putRecipeToId(id, recipeDTO, securityContext);
    }

    @Override
    public Response deleteIngredientWithId(Long id, SecurityContext securityContext) throws NotFoundException {
        return this.ingredientApiProvider.deleteIngredientWithId(id, securityContext);
    }

    @Override
    public Response deleteRecipeWithId(Long id, SecurityContext securityContext) throws NotFoundException {
        return this.recipeApiProvider.deleteRecipeWithId(id, securityContext);
    }

}
