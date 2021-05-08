package de.op.atom.foods.domain.enums;

public enum Unit {
    LITRE("l"),
    MILLILITRE("ml"),
    GRAM("g"),
    KILOGRAM("kg");
    
    final String stringRep;

    Unit(String stringRep) {
        this.stringRep = stringRep;
    }
    
}
