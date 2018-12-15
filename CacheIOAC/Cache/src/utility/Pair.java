package utility;

/**
 * Classe que modela um Par de elementos, onde o primeiro é a chave e o segundo o valor a ela
 * associado.
 *
 * @author JohnVithor
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Construtor padrão de um Pair.
     *
     * @param key
     *            Chave do par.
     * @param value
     *            Valor do par.
     */
    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Informa a chave do par.
     *
     * @return A chave do par.
     */
    public final K getKey() {
        return key;
    }

    /**
     * Altera a chave do par.
     *
     * @param key
     *            A nova chave do par.
     */
    public final void setKey(final K key) {
        this.key = key;
    }

    /**
     * Informa o valor do par.
     *
     * @return O valor do par.
     */
    public final V getValue() {
        return value;
    }

    /**
     * Altera o valor do par.
     *
     * @param value
     *            O novo valor do par.
     */
    public final void setValue(final V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<Key: " + key + " Value: " + value + ">";
    }
}
