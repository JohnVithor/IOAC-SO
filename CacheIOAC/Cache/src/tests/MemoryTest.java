package tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cache.Memory;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Classe para os testes da classe Memory.
 * 
 * @author JohnVithor
 */
class MemoryTest {

    /**
     * Construtor sem inicialização dos valores do bloco e getters.
     */
    @Test
    final void testMemory() {
        Memory memory = new Memory(4, 4);

        int numberBlocks = memory.getNumberBlocks();
        assertEquals(4, numberBlocks);

        int blockSize = memory.getBlockSize();
        assertEquals(4, blockSize);

        String result = memory.toString();
        System.out.println(result);
        String expected = "MEMORIA PRINCIPAL\n" + "Bloco - Endereço - Conteúdo\n"
                + "0 - 0 - 0\n" + "0 - 1 - 0\n" + "0 - 2 - 0\n" + "0 - 3 - 0\n"
                + "1 - 4 - 0\n" + "1 - 5 - 0\n" + "1 - 6 - 0\n" + "1 - 7 - 0\n"
                + "2 - 8 - 0\n" + "2 - 9 - 0\n" + "2 - 10 - 0\n" + "2 - 11 - 0\n"
                + "3 - 12 - 0\n" + "3 - 13 - 0\n" + "3 - 14 - 0\n" + "3 - 15 - 0\n";
        assertEquals(expected, result);

        assertThrows(IllegalArgumentException.class, () -> new Memory(4, -4, ""));
        assertAll(() -> new Memory(4, 4).getBlock(0));
    }

    /**
     * Construtor com inicialização dos valores do bloco.
     */
    @Test
    final void testMemoryData() {

        // Dados usado no arquivo 'tests/dataTest.txt':
        // 10 20 30 40
        // 50 60 70 80
        // 11 22 33 44
        // 55 66 77 88

        assertThrows(FileNotFoundException.class, () -> new Memory(4, 4, ""));
        assertAll(() -> new Memory(4, 4, "tests/dataTest.txt"));

        assertAll(() -> {
            Memory memory = new Memory(4, 4, "tests/dataTest.txt");
            String result = memory.toString();
            System.out.println(result);
            String expected = "MEMORIA PRINCIPAL\n" + "Bloco - Endereço - Conteúdo\n" 
                    + "0 - 0 - 10\n" + "0 - 1 - 20\n" + "0 - 2 - 30\n" + "0 - 3 - 40\n"
                    + "1 - 4 - 50\n" + "1 - 5 - 60\n" + "1 - 6 - 70\n" + "1 - 7 - 80\n"
                    + "2 - 8 - 11\n" + "2 - 9 - 22\n" + "2 - 10 - 33\n" + "2 - 11 - 44\n"
                    + "3 - 12 - 55\n" + "3 - 13 - 66\n" + "3 - 14 - 77\n" + "3 - 15 - 88\n";
            assertEquals(expected, result);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> new Memory(4, 4, "tests/over.txt"));
    }
}
