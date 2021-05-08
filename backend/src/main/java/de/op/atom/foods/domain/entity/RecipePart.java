package de.op.atom.foods.domain.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.op.atom.core.AbstractEntity;
import de.op.atom.foods.domain.enums.Unit;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class RecipePart extends AbstractEntity{
    @ManyToOne
    private Ingredient ingredient;
    private Integer amount;
    private Unit unit;

    /**
     **/

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("ingredient")
    @NotNull
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipePart recipePart = (RecipePart) o;
        return Objects.equals(ingredient, recipePart.ingredient) && Objects.equals(amount, recipePart.amount)
                && Objects.equals(unit, recipePart.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, amount, unit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecipePart {\n");

        sb.append("    ingredient: ")
          .append(toIndentedString(ingredient))
          .append("\n");
        sb.append("    amount: ")
          .append(toIndentedString(amount))
          .append("\n");
        sb.append("    unit: ")
          .append(toIndentedString(unit))
          .append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString()
                .replace("\n", "\n    ");
    }
}
