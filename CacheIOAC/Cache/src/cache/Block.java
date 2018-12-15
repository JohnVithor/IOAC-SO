package cache;

/**
 * Classe que modela um bloco de palavras, neste caso as palavras são valores inteiros.
 *
 * @author JohnVithor
 */
public class Block {
    /**
     * Número do Bloco.
     */
    private final int number;
    /**
     * Dados armazenados no Bloco.
     */
    private final int[] data;

    /**
     * Construtor padrão de um Bloco.
     *
     * @param blockSize
     *            Numero de palavras armazenadas neste Bloco.
     * @param number
     *            Numero de identificação do Bloco
     * @throws IllegalArgumentException
     *             Caso o numero de endereços do bloco ou o seu identificador sejam invalidos.
     */
    public Block(final int blockSize, final int number) {
        if (blockSize <= 0) {
            String msg = "Um Bloco com quantidade nula ou negativa de endereços é inválido.";
            throw new IllegalArgumentException(msg);
        }
        if (number < 0) {
            throw new IllegalArgumentException("O numero do bloco não pode ser negativo.");
        }
        data = new int[blockSize];
        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
        }
        this.number = number;
    }

    /**
     * Altera o valor armazenado no endereço(posição) informado.
     *
     * @param adress
     *            Endereço a ser modificado. (Posição)
     * @param value
     *            Novo valor do endereço.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso o endereço informado ultrapasse os limites associados a este Bloco.
     */
    public void setAdress(final int adress, final int value) {
        data[adress] = value;
    }

    /**
     * Informa o conteudo do endereço solicitado.
     * 
     * @param adress
     *            Endereço a ser acessado. (Posição)
     * @return Valor armazenado no endereço indicado.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso o endereço informado ultrapasse os limites associados a este Bloco.
     */
    public int getAdress(final int adress) {
        return data[adress];
    }

    /**
     * Informa o numero de palavras armazenadas neste Bloco.
     *
     * @return Tamanho do Bloco.
     */
    public int getBlockSize() {
        return data.length;
    }

    /**
     * Informa o numero associado a este bloco.
     *
     * @return O numero do Bloco.
     */
    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        int adressCount = number * data.length;
        final StringBuilder result = new StringBuilder();
        for (final int element : data) {
            result.append(number + " - " + (adressCount++) + " - " + element + "\n");
        }
        return result.toString();
    }

    /**
     * Métodos toString modificado para mostrar a linha da Cache em que o bloco está posicionado.
     * 
     * @param line
     *            Linha da Cache.
     * @return String formatada para mostrar o conteudo do bloco.
     */
    public String toString(final int line) {
        int adressCount = number * data.length;
        final StringBuilder result = new StringBuilder();
        for (final int element : data) {
            result.append(line + " - " + number + " - " + (adressCount++) + " - " + element + "\n");
        }
        return result.toString();
    }
}
