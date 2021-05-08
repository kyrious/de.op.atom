package de.op.atom.foods.domain.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MakroNutrients {

    @Id
    private Integer sugar;
    private Integer carbohydrates;
    private Integer saturatedFat;
    private Integer unsaturatedFat;
    private Integer fiber;
    private Integer protein;
    private Integer calories;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MakroNutrients makroNutrients = (MakroNutrients) o;
        return Objects.equals(sugar, makroNutrients.sugar)
                && Objects.equals(carbohydrates, makroNutrients.carbohydrates)
                && Objects.equals(saturatedFat, makroNutrients.saturatedFat)
                && Objects.equals(unsaturatedFat, makroNutrients.unsaturatedFat)
                && Objects.equals(fiber, makroNutrients.fiber) && Objects.equals(protein, makroNutrients.protein)
                && Objects.equals(calories, makroNutrients.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sugar, carbohydrates, saturatedFat, unsaturatedFat, fiber, protein, calories);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MakroNutrients {\n");

        sb.append("    sugar: ")
          .append(toIndentedString(sugar))
          .append("\n");
        sb.append("    carbohydrates: ")
          .append(toIndentedString(carbohydrates))
          .append("\n");
        sb.append("    saturatedFat: ")
          .append(toIndentedString(saturatedFat))
          .append("\n");
        sb.append("    unsaturatedFat: ")
          .append(toIndentedString(unsaturatedFat))
          .append("\n");
        sb.append("    fiber: ")
          .append(toIndentedString(fiber))
          .append("\n");
        sb.append("    protein: ")
          .append(toIndentedString(protein))
          .append("\n");
        sb.append("    calories: ")
          .append(toIndentedString(calories))
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
