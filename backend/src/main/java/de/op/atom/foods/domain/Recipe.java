package de.op.atom.foods.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.op.atom.core.AbstractEntity;

@Entity
@NamedQueries(@NamedQuery(name = Recipe.SELECT_ALL_RECIPES, query= "select e from Recipe e"))
public class Recipe extends AbstractEntity {

    public static final String SELECT_ALL_RECIPES = "Recipe.SELECT_ALL_RECIPES";
    
    @OneToMany(cascade = CascadeType.ALL)
    private java.util.List<RecipePart> parts;
    private String name;
    private String description;

    public java.util.List<RecipePart> getParts() {
        return parts;
    }

    public void setParts(java.util.List<RecipePart> parts) {
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipe [parts=" + parts + ", name=" + name + ", description=" + description + ", getId()=" + getId()
                + ", getVersion()=" + getVersion() + ", getCreateOn()=" + getCreateOn() + ", getCreatedBy()="
                + getCreatedBy() + ", getLastModifiedOn()=" + getLastModifiedOn() + ", getLastModifiedBy()="
                + getLastModifiedBy() + "]";
    }

}
