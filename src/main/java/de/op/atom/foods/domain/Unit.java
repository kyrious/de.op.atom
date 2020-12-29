package de.op.atom.foods.domain;

public enum Unit {
    LITRE("l"),
    MILLILITRE("ml"),
    GRAM("g"),
    KILOGRAM("kg");
    
    final String stringRep;

    private Unit(String stringRep) {
        this.stringRep = stringRep;
    }
    
}
