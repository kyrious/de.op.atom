package de.op.atom.foods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.op.atom.core.AbstractAtomTest;
import de.op.atom.gen.foods.model.IngredientDTO;
import de.op.atom.gen.foods.model.IngredientDTO.CategoryEnum;
import de.op.atom.gen.foods.model.RecipeDTO;
import de.op.atom.gen.foods.model.RecipePartDTO;
import de.op.atom.gen.foods.model.UnitDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeApiProviderTest extends AbstractAtomTest {

    private static IngredientDTO ingredientCereal;
    private static IngredientDTO ingredientFruit;
    private RecipeDTO recipeDto;

    @BeforeEach
    void initDtos() {
        if (ingredientCereal == null) {
            ingredientCereal = new IngredientDTO();
            ingredientCereal.setCategory(CategoryEnum.CEREAL);
            ingredientCereal.setName("TestCereal");
            ingredientCereal = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                      .with()
                                      .body(ingredientCereal)
                                      .contentType("application/json")
                                      .when()
                                      .post("/atom/v1/foods/ingredients")
                                      .then()
                                      .statusCode(Status.OK.getStatusCode())
                                      .extract()
                                      .as(IngredientDTO.class);
        }

        if (ingredientFruit == null) {
            ingredientFruit = new IngredientDTO();
            ingredientFruit.setCategory(CategoryEnum.FRUIT);
            ingredientFruit.setName("TestFRUIT");
            ingredientFruit = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                     .with()
                                     .body(ingredientFruit)
                                     .contentType("application/json")
                                     .when()
                                     .post("/atom/v1/foods/ingredients")
                                     .then()
                                     .statusCode(Status.OK.getStatusCode())
                                     .extract()
                                     .as(IngredientDTO.class);
        }

        recipeDto = new RecipeDTO();
        recipeDto.setName("TestRecipe");
        recipeDto.setDescription("TestDescription");
        RecipePartDTO part1 = new RecipePartDTO();
        part1.setAmount(100);
        part1.setUnit(UnitDTO.GRAM);
        part1.setIngredient(ingredientCereal);
        RecipePartDTO part2 = new RecipePartDTO();
        part2.setAmount(100);
        part2.setUnit(UnitDTO.GRAM);
        part2.setIngredient(ingredientFruit);
        recipeDto.setParts(List.of(part1, part2));
    }

    @Test
    @Order(1)
    public void postNewRecipe() {
        int numberOfRecipesOnServerBeforePost = getNumberOfRecipesOnServer();

        RecipeDTO readRecipe = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                      .with()
                                      .body(recipeDto)
                                      .contentType("application/json")
                                      .when()
                                      .post("/atom/v1/foods/recipes")
                                      .then()
                                      .statusCode(Status.OK.getStatusCode())
                                      .extract()
                                      .as(RecipeDTO.class);

        int numberOfRecipesOnServerAfterPost = getNumberOfRecipesOnServer();
        assertEquals(numberOfRecipesOnServerBeforePost + 1, numberOfRecipesOnServerAfterPost);

        assertEquals(recipeDto.getName(), readRecipe.getName());
        assertEquals(recipeDto.getDescription(), readRecipe.getDescription());
        assertCollectionEquals(recipeDto.getParts(), readRecipe.getParts(), this::recipePartsEquals);
    }

    @Test
    @Order(2)
    public void postNewRecipeWithoutIngredients() {
        int numberOfRecipesOnServerBeforePost = getNumberOfRecipesOnServer();
        recipeDto.getParts()
                 .stream()
                 .forEach(p -> {
                     p.setIngredient(null);
                 });

        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.BAD_REQUEST.getStatusCode())))
               .with()
               .body(recipeDto)
               .contentType("application/json")
               .when()
               .post("/atom/v1/foods/recipes")
               .then()
               .statusCode(Status.BAD_REQUEST.getStatusCode());

        int numberOfRecipesOnServerAfterPost = getNumberOfRecipesOnServer();
        assertEquals(numberOfRecipesOnServerBeforePost, numberOfRecipesOnServerAfterPost);
    }

    @Test
    @Order(3)
    public void postNewRecipeWithIngredientsId() {
        int numberOfRecipesOnServerBeforePost = getNumberOfRecipesOnServer();
        recipeDto.getParts()
                 .forEach(p -> {
                     IngredientDTO oldIngredient = p.getIngredient();
                     p.setIngredient(new IngredientDTO());
                     p.getIngredient()
                      .setId(oldIngredient.getId());
                 });

        RecipeDTO readRecipe = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                      .with()
                                      .body(recipeDto)
                                      .contentType("application/json")
                                      .when()
                                      .post("/atom/v1/foods/recipes")
                                      .then()
                                      .statusCode(Status.OK.getStatusCode())
                                      .extract()
                                      .as(RecipeDTO.class);

        int numberOfRecipesOnServerAfterPost = getNumberOfRecipesOnServer();
        assertEquals(numberOfRecipesOnServerBeforePost + 1, numberOfRecipesOnServerAfterPost);

        assertEquals(recipeDto.getName(), readRecipe.getName());
        assertEquals(recipeDto.getDescription(), readRecipe.getDescription());
        assertCollectionEquals(recipeDto.getParts(), readRecipe.getParts(), this::recipePartsEquals);
    }

    @Test
    @Order(4)
    public void getNewlyPostedRecipe() {
        RecipeDTO[] readRecipes = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                         .when()
                                         .get("/atom/v1/foods/recipes/")
                                         .then()
                                         .statusCode(Status.OK.getStatusCode())
                                         .extract()
                                         .as(RecipeDTO[].class);
        assertTrue(readRecipes.length > 0, "No recipes available.");
        RecipeDTO readRecipe = readRecipes[0];

        assertEquals(recipeDto.getName(), readRecipe.getName());
        assertEquals(recipeDto.getDescription(), readRecipe.getDescription());
        assertCollectionEquals(recipeDto.getParts(), readRecipe.getParts(), this::recipePartsEquals);
    }

    @Test
    @Order(5)
    public void updateRecipeMetadata() {
        RecipeDTO[] readRecipes = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                         .when()
                                         .get("/atom/v1/foods/recipes/")
                                         .then()
                                         .statusCode(Status.OK.getStatusCode())
                                         .extract()
                                         .as(RecipeDTO[].class);
        assertTrue(readRecipes.length > 0);
        RecipeDTO readRecipe = readRecipes[0];

        String newName = "updateRecipeMetadata";
        readRecipe.setName(newName);

        RecipeDTO updatedRecipe = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                         .filter(new RequestLoggingFilter())
                                         .with()
                                         .body(readRecipe)
                                         .contentType("application/json")
                                         .when()
                                         .put("/atom/v1/foods/recipes/{id}", readRecipe.getId())
                                         .then()
                                         .statusCode(Status.OK.getStatusCode())
                                         .extract()
                                         .as(RecipeDTO.class);

        assertEquals(newName, updatedRecipe.getName());
        assertEquals(readRecipe.getDescription(), updatedRecipe.getDescription());
        assertCollectionEquals(readRecipe.getParts(), updatedRecipe.getParts(), this::recipePartsEquals);
    }

    @Test
    @Order(6)
    public void updateRecipeParts() {
        RecipeDTO[] readRecipes = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                         .when()
                                         .get("/atom/v1/foods/recipes/")
                                         .then()
                                         .statusCode(Status.OK.getStatusCode())
                                         .extract()
                                         .as(RecipeDTO[].class);
        assertTrue(readRecipes.length > 0);
        RecipeDTO readRecipe = readRecipes[0];

        RecipePartDTO oldPart = readRecipe.getParts()
                                          .get(0);
        oldPart.setIngredient(ingredientFruit);
        RecipePartDTO newPart = new RecipePartDTO();
        newPart.setIngredient(ingredientFruit);
        newPart.setUnit(UnitDTO.LITRE);
        newPart.setAmount(202020202);
        readRecipe.setParts(List.of(oldPart, newPart));

        RecipeDTO updatedRecipe = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                         .with()
                                         .body(readRecipe)
                                         .contentType("application/json")
                                         .when()
                                         .put("/atom/v1/foods/recipes/{id}", readRecipe.getId())
                                         .then()
                                         .statusCode(Status.OK.getStatusCode())
                                         .extract()
                                         .as(RecipeDTO.class);

        assertEquals(readRecipe.getName(), updatedRecipe.getName());
        assertEquals(readRecipe.getDescription(), updatedRecipe.getDescription());
        assertCollectionEquals(readRecipe.getParts(), updatedRecipe.getParts(), this::recipePartsEquals);
    }

    @Test
    @Order(7)
    public void deleteRecipe() {
        RecipeDTO[] recipes = given().when()
                                     .get("/atom/v1/foods/recipes/")
                                     .then()
                                     .statusCode(Status.OK.getStatusCode())
                                     .extract()
                                     .as(RecipeDTO[].class);
        assertTrue(recipes.length > 0);

        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
               .when()
               .delete("/atom/v1/foods/recipes/{id}", recipes[0].getId())
               .then()
               .statusCode(Status.OK.getStatusCode());

        int numberOfRecipesOnServerAfterDelete = getNumberOfRecipesOnServer();
        assertEquals(recipes.length - 1, numberOfRecipesOnServerAfterDelete);

    }

    private int getNumberOfRecipesOnServer() {
        return given().when()
                      .get("/atom/v1/foods/recipes/")
                      .then()
                      .statusCode(Status.OK.getStatusCode())
                      .extract()
                      .as(RecipeDTO[].class).length;
    }

    private boolean recipePartsEquals(RecipePartDTO p1, RecipePartDTO p2) {
        if (p1 == null && p2 == null) {
            return true;
        } else if (p1 == null || p2 == null) {
            return false;
        }

        boolean ingredientEqual;
        if (p1.getIngredient() == null && p2.getIngredient() == null) {
            ingredientEqual = true;
        } else if (p1.getIngredient() == null || p2.getIngredient() == null) {
            ingredientEqual = false;
        } else {
            ingredientEqual = Objects.equals(p1.getIngredient()
                                               .getId(),
                                             p2.getIngredient()
                                               .getId());
        }

        //@formatter:off
        return ingredientEqual
            && Objects.equals(p1.getUnit(),   p2.getUnit())
            && Objects.equals(p1.getAmount(), p2.getAmount());
        //@formatter:on
    }
}
