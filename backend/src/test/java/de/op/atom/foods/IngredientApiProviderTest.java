package de.op.atom.foods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.op.atom.core.DefaultAtomProfile;
import de.op.atom.gen.api.FoodsApi;
import de.op.atom.gen.foods.model.IngredientDTO;
import de.op.atom.gen.foods.model.IngredientDTO.CategoryEnum;
import de.op.atom.gen.foods.model.MakroNutrientsDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.filter.log.ResponseLoggingFilter;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(FoodsApi.class)
@TestProfile(DefaultAtomProfile.class)
public class IngredientApiProviderTest {

    private IngredientDTO ingredientDto;

    @BeforeEach
    void initTestDto() {
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

    @Test
    @Order(1)
    public void postNewIngredient() {
        int numberOfIngredientsOnServerBeforePost = getNumberOfIngredientsOnServer();

        IngredientDTO readIngredient = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                              .with()
                                              .body(ingredientDto)
                                              .contentType("application/json")
                                              .when()
                                              .post("/ingredients")
                                              .then()
                                              .statusCode(Status.OK.getStatusCode())
                                              .extract()
                                              .as(IngredientDTO.class);

        int numberOfIngredientsOnServerAfterPost = getNumberOfIngredientsOnServer();
        assertEquals(numberOfIngredientsOnServerBeforePost + 1, numberOfIngredientsOnServerAfterPost);

        assertEquals(ingredientDto.getName(), readIngredient.getName());
        assertEquals(ingredientDto.getCategory(), readIngredient.getCategory());
        assertEquals(ingredientDto.getMakroNutrients(), readIngredient.getMakroNutrients());
    }

    @Test
    @Order(2)
    public void getNewlyPostedIngredient() {
        IngredientDTO[] readIngredients = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .when()
                                                 .get("/ingredients")
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO[].class);
        assertTrue(readIngredients.length > 0);
        IngredientDTO readIngredient = readIngredients[0];

        assertEquals(ingredientDto.getName(), readIngredient.getName());
        assertEquals(ingredientDto.getCategory(), readIngredient.getCategory());
        assertEquals(ingredientDto.getMakroNutrients(), readIngredient.getMakroNutrients());
    }

    @Test
    @Order(3)
    public void updateIngredientMetadata() {
        IngredientDTO[] readIngredients = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .when()
                                                 .get("/ingredients")
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO[].class);
        assertTrue(readIngredients.length > 0);
        IngredientDTO readIngredient = readIngredients[0];

        String newName = "updateIngredientMetadata";
        readIngredient.setName(newName);

        IngredientDTO updatedIngredient = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .with()
                                                 .body(readIngredient)
                                                 .contentType("application/json")
                                                 .when()
                                                 .put("/ingredients/{id}", readIngredient.getId())
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO.class);

        assertEquals(newName, updatedIngredient.getName());
        assertEquals(readIngredient.getCategory(), updatedIngredient.getCategory());
        assertEquals(readIngredient.getMakroNutrients(), updatedIngredient.getMakroNutrients());
    }

    @Test
    @Order(4)
    public void updateIngredientMakroNutrients() {
        IngredientDTO[] readIngredients = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .when()
                                                 .get("/ingredients")
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO[].class);
        assertTrue(readIngredients.length > 0);
        IngredientDTO readIngredient = readIngredients[0];

        int newCalories = 1;
        readIngredient.getMakroNutrients()
                      .setCalories(newCalories);

        IngredientDTO updatedIngredient = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .with()
                                                 .body(readIngredient)
                                                 .contentType("application/json")
                                                 .when()
                                                 .put("/ingredients/{id}", readIngredient.getId())
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO.class);

        assertEquals(readIngredient.getName(), updatedIngredient.getName());
        assertEquals(readIngredient.getCategory(), updatedIngredient.getCategory());
        assertEquals(readIngredient.getMakroNutrients(), updatedIngredient.getMakroNutrients());
        assertEquals(newCalories,
                     updatedIngredient.getMakroNutrients()
                                      .getCalories());
    }

    @Test
    @Order(5)
    public void updateIngredientWithWrongVersion() {
        IngredientDTO[] readIngredients = given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
                                                 .when()
                                                 .get("/ingredients")
                                                 .then()
                                                 .statusCode(Status.OK.getStatusCode())
                                                 .extract()
                                                 .as(IngredientDTO[].class);
        assertTrue(readIngredients.length > 0);
        IngredientDTO readIngredient = readIngredients[0];

        int newCalories = 1;
        readIngredient.getMakroNutrients()
                      .setCalories(newCalories);

        // Post the first time
        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
               .with()
               .body(readIngredient)
               .contentType("application/json")
               .when()
               .put("/ingredients/{id}", readIngredient.getId())
               .then()
               .statusCode(Status.OK.getStatusCode())
               .extract()
               .as(IngredientDTO.class);

        // Post a second time with the same version, thus there should be a version conflict
        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.CONFLICT.getStatusCode())))
               .with()
               .body(readIngredient)
               .contentType("application/json")
               .when()
               .put("/ingredients/{id}", readIngredient.getId())
               .then()
               .statusCode(Status.CONFLICT.getStatusCode());

    }

    @Test
    @Order(6)
    public void deleteIngredient() {
        IngredientDTO[] ingredients = given().when()
                                             .get("/ingredients")
                                             .then()
                                             .statusCode(Status.OK.getStatusCode())
                                             .extract()
                                             .as(IngredientDTO[].class);
        assertTrue(ingredients.length > 0);

        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeMatches(not(Status.OK.getStatusCode())))
               .when()
               .delete("/ingredients/{id}", ingredients[0].getId())
               .then()
               .statusCode(Status.OK.getStatusCode());

        int numberOfIngredientsOnServerAfterDelete = getNumberOfIngredientsOnServer();
        assertEquals(ingredients.length - 1, numberOfIngredientsOnServerAfterDelete);

    }

    private int getNumberOfIngredientsOnServer() {
        return given().when()
                      .get("/ingredients")
                      .then()
                      .statusCode(Status.OK.getStatusCode())
                      .extract()
                      .as(IngredientDTO[].class).length;
    }
}
