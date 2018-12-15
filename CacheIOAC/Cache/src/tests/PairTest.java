package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utility.Pair;

class PairTest {

    @Test
    final void testPair() {
        Pair<Integer, Integer> pair = new Pair<>(0, 1);
        int key = pair.getKey();
        assertEquals(0, key);

        int value = pair.getValue();
        assertEquals(1, value);

        pair.setKey(5);
        key = pair.getKey();
        assertEquals(5, key);

        pair.setValue(50);
        value = pair.getValue();
        assertEquals(50, value);

        String string = pair.toString();
        String expected = "<Key: 5 Value: 50>";
        assertEquals(expected, string);
    }
}
