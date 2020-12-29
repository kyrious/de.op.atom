package de.op.atom.foods.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.op.atom.foods.domain.Recipe;
import de.op.atom.foods.domain.RecipePart;

@ApplicationScoped
public class RecipeService {

    @PersistenceContext
    private EntityManager em;

    public Recipe getRecipeWithId(long id) {
        return em.find(Recipe.class, id);
    }

    public List<Recipe> getAllRecipes() {
        return em.createNamedQuery(Recipe.SELECT_ALL_RECIPES, Recipe.class)
                 .getResultList();
    }

    public long updateOrCreate(Recipe recipe) {
        Long postedId = recipe.getId();
        Recipe alyreadyExistent = null;
        if (postedId != null) {
            alyreadyExistent = em.find(Recipe.class, postedId);
        }
        if (alyreadyExistent != null) {
            recipe.getParts()
                  .forEach(this::updateOrCreate);
            em.merge(recipe);
        } else {
            em.persist(recipe);
        }
        return recipe.getId();
    }

    private void updateOrCreate(RecipePart part) {
        Long postedId = part.getId();
        RecipePart alyreadyExistent = null;
        if (postedId != null) {
            alyreadyExistent = em.find(RecipePart.class, postedId);
        }
        if (alyreadyExistent != null) {
            em.merge(part);
        } else {
            em.persist(part);
        }
    }

    public void deleteRecipeWithId(Long id) {
        Recipe r = em.find(Recipe.class, id);
        if(r == null) {
            throw new NoSuchElementException();
        }
        em.remove(r);
    }
}
