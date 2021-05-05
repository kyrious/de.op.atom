package de.op.atom.foods.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

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
            Set<Long> parts = recipe.getParts()
                                    .stream()
                                    .map(this::updateOrCreate)
                                    .map(p -> p.getId())
                                    .collect(Collectors.toSet());
            alyreadyExistent.getParts()
                            .stream()
                            .filter(p -> !parts.contains(p.getId()))
                            .forEach(this::deleteRecipepart);

            em.merge(recipe);
        } else {
            em.persist(recipe);
        }
        return recipe.getId();
    }

    private RecipePart updateOrCreate(RecipePart part) {
        Long postedId = part.getId();
        RecipePart alyreadyExistent = null;
        if (postedId != null) {
            alyreadyExistent = em.find(RecipePart.class, postedId);
        }
        if (alyreadyExistent != null) {
            return em.merge(part);
        } else {
            em.persist(part);
            return part;
        }
    }

    private void deleteRecipepart(RecipePart p) {
        RecipePart r = em.find(RecipePart.class, p.getId());
        if (r == null) {
            throw new NoSuchElementException();
        }
        em.remove(r);
    }

    public void deleteRecipeWithId(Long id) {
        Recipe r = em.find(Recipe.class, id);
        if (r == null) {
            throw new NoSuchElementException();
        }
        em.remove(r);
    }
}
