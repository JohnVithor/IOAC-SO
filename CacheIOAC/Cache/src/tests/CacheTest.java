package tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cache.Cache;
import cache.MapType;
import cache.Memory;
import cache.SubsType;

import org.junit.jupiter.api.Test;

/**
 * Classe para testes da classe Cache.
 * 
 * @author JohnVithor
 */
@SuppressWarnings("unused")
class CacheTest {

    /**
     * Testando o construtor com Mapeamento Direto.
     */
    @Test
    void testCacheIntMapTypeMemory() {
        assertAll(() -> new Cache(4, MapType.DIRETO, new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.ASSOCIATIVO_PARCIAL, new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.ASSOCIATIVO_TOTAL, new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.INVALIDO, new Memory(4, 8)));
    }

    /**
     * Testando o construtor com Mapeamento Totalmente Associativo.
     */
    @Test
    void testCacheIntMapTypeSubsTypeMemory() {
        assertAll(() -> new Cache(4, MapType.ASSOCIATIVO_TOTAL, SubsType.ALEATORIO,
                        new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.DIRETO, SubsType.FIFO, new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.ASSOCIATIVO_PARCIAL, SubsType.LFU,
                                     new Memory(4, 8)));

    }

    /**
     * Testando o construtor com Mapeamento Parcialmente Associativo.
     */
    @Test
    void testCacheIntMapTypeIntSubsTypeMemory() {
        assertAll(() -> new Cache(4, MapType.ASSOCIATIVO_PARCIAL, 2, SubsType.ALEATORIO,
                        new Memory(4, 8)));

        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.DIRETO, 2, SubsType.FIFO, new Memory(4, 8)));

        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.ASSOCIATIVO_TOTAL, 0, SubsType.LFU,
                                     new Memory(4, 8)));
        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.INVALIDO, 45, SubsType.LRU, new Memory(4, 8)));

        assertThrows(IllegalArgumentException.class,
            () -> new Cache(4, MapType.ASSOCIATIVO_PARCIAL, 0, SubsType.LFU,
                                     new Memory(4, 8)));
    }

    /**
     * Testando os métodos de acesso e modificação de um endereço (palavra).
     */
    @Test
    void testGetAdressAndTestSetAdress() {
        {
            Cache cache = new Cache(4, MapType.DIRETO, new Memory(4, 8));
            assertAll(() -> {
                cache.setAdress(0, 5);
                int i = cache.getAdress(0);
                assertEquals(5, i);
                i = cache.getAdress(2);
            });
            assertThrows(IllegalArgumentException.class, () -> cache.getAdress(-1));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> cache.getAdress(80));

        }
        {
            Cache cache = new Cache(4, MapType.ASSOCIATIVO_PARCIAL, 2, SubsType.ALEATORIO,
                            new Memory(4, 8));
            assertAll(() -> {
                cache.setAdress(0, 5);
                int i = cache.getAdress(0);
                assertEquals(5, i);
                i = cache.getAdress(2);
            });
            assertThrows(IllegalArgumentException.class, () -> cache.getAdress(-1));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> cache.getAdress(80));
            assertThrows(IllegalArgumentException.class, () -> cache.setAdress(-80, 0));
        }
    }

    /**
     * Testando calculo da taxa de hit.
     */
    @Test
    void testHitRate() {
        Cache cache = new Cache(4, MapType.DIRETO, new Memory(4, 8));
        double d;
        assertThrows(ArithmeticException.class, () -> cache.hitRate());
        int i = cache.getAdress(0);
        d = cache.hitRate();
        assertEquals(0, d);
        i = cache.getAdress(0);
        d = cache.hitRate();
        assertEquals(50.0, d);
        i = cache.getAdress(0);
        i = cache.getAdress(0);
        d = cache.hitRate();
        assertEquals(75.0, d);
    }

    /**
     * Testando calculo da taxa de miss.
     */
    @Test
    void testMissRate() {
        Cache cache = new Cache(4, MapType.DIRETO, new Memory(4, 8));
        double d;
        assertThrows(ArithmeticException.class, () -> cache.missRate());
        cache.getAdress(0);
        d = cache.missRate();
        assertEquals(100.0, d);
        cache.getAdress(0);
        d = cache.missRate();
        assertEquals(50.0, d);
        cache.getAdress(0);
        cache.getAdress(0);
        d = cache.missRate();
        assertEquals(25.0, d);
    }

    @Test
    void testMapDir() {
        Cache cache = new Cache(4, MapType.DIRETO, new Memory(4, 8));
        cache.getAdress(0);
        cache.getAdress(4);
        cache.getAdress(8);
        cache.getAdress(12);

        double d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(16); // miss at 0
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(0); // miss at 0
        d = cache.missRate();
        assertEquals(100.0, d);
    }

    /**
     * Testando Politica de substituição FIFO.
     */
    @Test
    void testSubFifo() {
        Cache cache = new Cache(4, MapType.ASSOCIATIVO_TOTAL, SubsType.FIFO, new Memory(4, 8));
        cache.getAdress(0); // miss at 0
        cache.getAdress(4); // miss at 1
        cache.getAdress(8); // miss at 2
        cache.getAdress(12); // miss at 3

        double d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(16); // miss at 0
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(0); // miss at 1
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(4); // miss at 2
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(8); // miss at 3
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(12); // miss at 0
        d = cache.missRate();
        assertEquals(100.0, d);

        cache.getAdress(16); // miss at 1
        d = cache.missRate();
        assertEquals(100.0, d);
    }

    /**
     * Testando Politica de substituição LFU.
     */
    @Test
    void testSubLfu() {
        Cache cache = new Cache(4, MapType.ASSOCIATIVO_TOTAL, SubsType.LFU, new Memory(4, 8));
        cache.getAdress(0); // 1 -> indica o numero de acessos.
        cache.getAdress(4); // 1
        cache.getAdress(8); // 1
        cache.getAdress(12); // 1

        double rate = cache.missRate(); // carregando a cache
        assertEquals(100.0, rate);

        cache.getAdress(0); // 2
        cache.getAdress(0); // 3
        cache.getAdress(0); // 4
        cache.getAdress(0); // 5

        rate = cache.hitRate(); // 8 acessos e 4 hits
        double expected = 100.0 * (4.0 / 8.0);
        assertEquals(expected, rate);

        cache.getAdress(4); // 2
        cache.getAdress(4); // 3
        cache.getAdress(4); // 4

        rate = cache.hitRate(); // 11 acessos e 7 hits
        expected = 100.0 * (7.0 / 11.0);
        assertEquals(expected, rate);

        cache.getAdress(8); // 2
        cache.getAdress(8); // 3

        rate = cache.hitRate(); // 13 acessos e 9 hits
        expected = 100.0 * (9.0 / 13.0);
        assertEquals(expected, rate);

        cache.getAdress(12); // 2

        rate = cache.hitRate(); // 14 acessos e 10 hits
        expected = 100.0 * (10.0 / 14.0);
        assertEquals(expected, rate);

        // Cache carregada e com diferentes contadores de uso em cada linha

        // substitui o bloco com o endereço 12
        cache.getAdress(16); // 1

        rate = cache.hitRate(); // 15 acessos e 10 hits
        expected = 100.0 * (10.0 / 15.0);
        assertEquals(expected, rate);

        cache.getAdress(16); // 2
        cache.getAdress(16); // 3
        cache.getAdress(16); // 4

        rate = cache.hitRate(); // 18 acessos e 13 hits
        expected = 100.0 * (13.0 / 18.0);
        assertEquals(expected, rate);

        // substitui o bloco com o endereço 8
        cache.getAdress(12); // 1

        rate = cache.hitRate(); // 19 acessos e 13 hits
        expected = 100.0 * (13.0 / 19.0);
        assertEquals(expected, rate);

        // substitui o bloco com o endereço 12
        cache.getAdress(20); // 1

        rate = cache.hitRate(); // 20 acessos e 13 hits
        expected = 100.0 * (13.0 / 20.0);
        assertEquals(expected, rate);

    }

    /**
     * Testando Politica de substituição LRU.
     */
    @Test
    void testSubLru() {
        Cache cache = new Cache(4, MapType.ASSOCIATIVO_TOTAL, SubsType.LRU, new Memory(4, 8));
        cache.getAdress(0); // 0 -> indica o numero da linha em que o bloco foi posicionado.
        cache.getAdress(4); // 1
        cache.getAdress(8); // 2
        cache.getAdress(12); // 3

        double rate = cache.missRate(); // carregando a cache - 4 acessos e 0 hits
        assertEquals(100.0, rate);

        cache.getAdress(16); // 0
        rate = cache.hitRate(); // 5 acessos e 0 hits
        double expected = 100.0 * (0.0 / 5.0);
        assertEquals(expected, rate);

        cache.getAdress(4); // 1
        rate = cache.hitRate(); // 6 acessos e 1 hits
        expected = 100.0 * (1.0 / 6.0);
        assertEquals(expected, rate);

        cache.getAdress(0); // 2
        rate = cache.hitRate(); // 7 acessos e 1 hits
        expected = 100.0 * (1.0 / 7.0);
        assertEquals(expected, rate);

        cache.getAdress(8); // 3
        rate = cache.hitRate(); // 8 acessos e 1 hits
        expected = 100.0 * (1.0 / 8.0);
        assertEquals(expected, rate);

    }
    // Politica de substituição aleatoria não foi testada, pois é aleatoria.

    @Test
    void testToString() {
        Cache cache = new Cache(4, MapType.DIRETO, new Memory(4, 8));
        cache.getAdress(0);
        String result = cache.toString();
        String expected = "CACHE L1\nLinha - Bloco - Endereço - Conteúdo\n0 - 0 - 0 - 0\n0 - 0 - 1 - 0\n0 - 0 - 2 - 0\n0 - 0 - 3 - 0\n1 LIVRE\n2 LIVRE\n3 LIVRE\n";
        assertEquals(expected, result);
    }
}
