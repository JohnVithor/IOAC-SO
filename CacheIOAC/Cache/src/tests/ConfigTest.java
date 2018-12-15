package tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cache.MapType;
import cache.SubsType;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import simulator.Config;

/**
 * Classe para testes da classe Config.
 * 
 * @author JohnVithor
 */
class ConfigTest {

    /**
     * Testa possivel exceção que o construtor pode lançar.
     */
    @Test
    final void testConfig() {
        assertThrows(FileNotFoundException.class, () -> new Config("tests/config4.txt"));
        assertAll(() -> new Config("tests/config0.txt"));
    }

    /**
     * Testa método de acesso da informação sobre o tamanho de cada bloco da memoria.
     */
    @Test
    final void testGetBlockSize() {
        assertAll(() -> {
            Config config0 = new Config("tests/config0.txt");
            int blockSize0 = config0.getBlockSize();
            assertEquals(4, blockSize0);
        });
    }

    /**
     * Testa método de acesso da informação sobre a capacidade da cache.
     */
    @Test
    final void testGetCacheLines() {

        assertAll(() -> {
            Config config0;
            config0 = new Config("tests/config0.txt");
            int cacheLines0 = config0.getCacheLines();
            assertEquals(8, cacheLines0);
        });

    }

    /**
     * Testa método de acesso da informação do numero de blocos na memoria.
     */
    @Test
    final void testGetBlockNumber() {
        assertAll(() -> {
            Config config0 = new Config("tests/config0.txt");
            int blockNumber0 = config0.getBlockNumber();
            assertEquals(16, blockNumber0);
        });

    }

    /**
     * Testa método de acesso da informação do tipo de mapeamento.
     */
    @Test
    final void testGetmType() {
        assertAll(() -> {
            Config config0 = new Config("tests/config0.txt");
            MapType cache0 = config0.getmType();
            assertEquals(MapType.DIRETO, cache0);

            Config config1 = new Config("tests/config1.txt");
            MapType cache1 = config1.getmType();
            assertEquals(MapType.ASSOCIATIVO_TOTAL, cache1);

            Config config2 = new Config("tests/config2.txt");
            MapType cache2 = config2.getmType();
            assertEquals(MapType.ASSOCIATIVO_PARCIAL, cache2);

            Config config3 = new Config("tests/config3.txt");
            MapType cache3 = config3.getmType();
            assertEquals(MapType.INVALIDO, cache3);
        });
    }

    /**
     * Testa método de acesso da informação sobre o numero de conjuntos do modo de mapeamento
     * parcialmente associativo.
     */
    @Test
    final void testGetSetsNumber() {
        assertAll(() -> {
            Config config2 = new Config("tests/config2.txt");
            int sets = config2.getSetsNumber();
            assertEquals(2, sets);
        });
    }

    /**
     * Testa método de acesso da informação do tipo de politica de substituição.
     */
    @Test
    final void testGetsType() {

        assertAll(() -> {
            Config config0 = new Config("tests/config0.txt");
            SubsType cache0 = config0.getsType();
            assertEquals(SubsType.LRU, cache0);

            Config config1 = new Config("tests/config1.txt");
            SubsType cache1 = config1.getsType();
            assertEquals(SubsType.LFU, cache1);

            Config config2 = new Config("tests/config2.txt");
            SubsType cache2 = config2.getsType();
            assertEquals(SubsType.FIFO, cache2);

            Config config3 = new Config("tests/config3.txt");
            SubsType cache3 = config3.getsType();
            assertEquals(SubsType.ALEATORIO, cache3);
        });
    }

    /**
     * Testa método de conversão para String.
     */
    @Test
    final void testToString() {
        assertAll(() -> {
            Config config3 = new Config("tests/config3.txt");
            String result = config3.toString();
            String expected = "Cada bloco contém 2 palavras\n" + "A cache tem 2 linhas\n"
                    + "A memória principal tem 4 blocos\n" + "Invalido\n"
                    + "Não significa NADA pois o mapeamento não é parcialmente associativo\n"
                    + "Política de substituição Aleatorio\n";
            System.out.println(result);
            assertEquals(expected, result);
        });
    }
}
