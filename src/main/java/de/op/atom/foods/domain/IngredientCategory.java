package de.op.atom.foods.domain;

public enum IngredientCategory {
    VEGETABLE("VEGETABLE"),

    CEREAL("CEREAL"),

    FRUIT("FRUIT"),

    DAIRY("DAIRY"),

    MEAT("MEAT"),

    CONFECTION("CONFECTION");

    private String value;

    IngredientCategory(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}