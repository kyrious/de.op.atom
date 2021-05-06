package de.op.atom.foods.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.op.atom.foods.domain.entity.Ingredient;

@ApplicationScoped
public class IngredientService {

    @PersistenceContext
    private EntityManager em;

    public Ingredient getIngredientWithId(long id) {
        return em.find(Ingredient.class, id);
    }

    public Ingredient getIngredientWithIdForReferences(long id) {
        return em.getReference(Ingredient.class, id);
    }

    public List<Ingredient> getAllIngredients() {
        return em.createNamedQuery(Ingredient.SELECT_ALL_INGREDIENTS, Ingredient.class)
                 .getResultList();
    }

    public long updateOrCreate(Ingredient ing) {
        Long postedId = ing.getId();
        Ingredient alreadyExistent = null;
        if (postedId != null) {
            alreadyExistent = em.find(Ingredient.class, postedId);
        }
        if (alreadyExistent != null) {
            em.merge(ing);
        } else {
            em.persist(ing);
        }
        return ing.getId();
    }

    public void deleteIngredientWithId(Long id) {
        Ingredient i = em.find(Ingredient.class, id);
        if(i == null) {
            throw new NoSuchElementException();
        }
        em.remove(i);
    }
}
