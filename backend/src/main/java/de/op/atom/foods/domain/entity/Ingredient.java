package de.op.atom.foods.domain.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.op.atom.core.AbstractEntity;
import de.op.atom.foods.domain.enums.IngredientCategory;
import de.op.atom.foods.domain.enums.Unit;

@Entity
@NamedQueries(@NamedQuery(name = Ingredient.SELECT_ALL_INGREDIENTS, query= "select e from Ingredient e"))
public class Ingredient extends AbstractEntity {

    public static final String SELECT_ALL_INGREDIENTS = "Ingredient.SELECT_ALL_INGREDIENTS";
    
    private String name;
    private IngredientCategory category;
    private Unit defaultUnit;
    private Integer sugar;
    private Integer carbohydrates;
    private Integer saturatedFat;
    private Integer unsaturatedFat;
    private Integer fiber;
    private Integer protein;
    private Integer calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    public Integer getSugar() {
        return sugar;
    }

    public void setSugar(Integer sugar) {
        this.sugar = sugar;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Integer getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Integer saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Integer getUnsaturatedFat() {
        return unsaturatedFat;
    }

    public void setUnsaturatedFat(Integer unsaturatedFat) {
        this.unsaturatedFat = unsaturatedFat;
    }

    public Integer getFiber() {
        return fiber;
    }

    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
    
    public Unit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", category=" + category + ", defaultUnit=" + defaultUnit + ", sugar="
                + sugar + ", carbohydrates=" + carbohydrates + ", saturatedFat=" + saturatedFat + ", unsaturatedFat="
                + unsaturatedFat + ", fiber=" + fiber + ", protein=" + protein + ", calories=" + calories + ", getId()="
                + getId() + ", getVersion()=" + getVersion() + ", getCreateOn()=" + getCreateOn() + ", getCreatedBy()="
                + getCreatedBy() + ", getLastModifiedOn()=" + getLastModifiedOn() + ", getLastModifiedBy()="
                + getLastModifiedBy() + "]";
    }

}
