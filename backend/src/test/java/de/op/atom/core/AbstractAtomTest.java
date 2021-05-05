package de.op.atom.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;
import java.util.function.BiFunction;

public abstract class AbstractAtomTest {

    public final <E, G> void assertCollectionEquality(Collection<E> expectedCollection,
                                                     Collection<G> givenCollection,
                                                     BiFunction<E, G, Boolean> b) {
        for (E expected : expectedCollection) {
            boolean foundMatch = false;
            for (G given : givenCollection) {
                if (b.apply(expected, given)) {
                    foundMatch = true;
                }
            }
            if (!foundMatch) {
                fail("Could not find any match for " + expected + " in {"+givenCollection+"}");
            }
        }
    }
}
