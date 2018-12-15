package tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cache.Block;

import org.junit.jupiter.api.Test;

/**
 * Classe para testes da classe Block.
 * 
 * @author JohnVithor
 */
class BlockTest {

    /**
     * Como a classe possui poucos métodos, os testes abaixo foram julgados serem suficientes.
     */
    @Test
    final void testBlock() {
        assertThrows(IllegalArgumentException.class, () -> new Block(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Block(2, -4));
        assertAll(() -> new Block(4, 1));

        Block block = new Block(4, 1);

        int size = block.getBlockSize();
        assertEquals(4, size);

        int number = block.getNumber();
        assertEquals(1, number);

        int adress0 = block.getAdress(0);
        assertEquals(0, adress0);
        block.setAdress(1, 1);
        int adress1 = block.getAdress(1);
        assertEquals(1, adress1);
        block.setAdress(2, 2);
        int adress2 = block.getAdress(2);
        assertEquals(2, adress2);
        block.setAdress(3, 3);
        int adress3 = block.getAdress(3);
        assertEquals(3, adress3);

        String result = block.toString();
        System.out.println(result);
        String expected = "1 - 4 - 0\n1 - 5 - 1\n1 - 6 - 2\n1 - 7 - 3\n";
        assertEquals(expected, result);

        result = block.toString(0);
        System.out.println(result);
        expected = "0 - 1 - 4 - 0\n0 - 1 - 5 - 1\n0 - 1 - 6 - 2\n0 - 1 - 7 - 3\n";
        assertEquals(expected, result);
    }
}
