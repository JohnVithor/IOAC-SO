package cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que modela uma memoria com seus Blocos.
 *
 * @author JohnVithor
 */
public class Memory {
    private final Block[] blocks;
    private final int blockSize;

    /**
     * Dado o caminho para o arquivo necessario, carrega os numeros nele contidos para os blocos da
     * memoria.
     *
     * @param path
     *            Caminho para o arquivo com os dados.
     * @return ArrayList com as linhas do arquivo.
     * @throws FileNotFoundException
     *             Caso o caminho especificado seja invalido.
     */
    private ArrayList<String> loadData(final String path) throws FileNotFoundException {
        final ArrayList<String> data = new ArrayList<>();
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(path));
        } catch (final FileNotFoundException e) {
            throw new FileNotFoundException("Erro arquivo: " + path + " não encontrado.");
        }
        String line = null;
        try {
            line = buffRead.readLine();
        } catch (final IOException e1) {
            System.err.println("IOException capturada durante a leitura.");
            try {
                buffRead.close();
            } catch (final IOException e) {
                System.err.println(e.getMessage());
            }
            return new ArrayList<>();
        }
        while (line != null) {
            data.add(line);
            try {
                line = buffRead.readLine();
            } catch (final IOException e) {
                System.err.println("Erro durante a leitura.");
                try {
                    buffRead.close();
                } catch (final IOException ioe) {
                    System.err.println(ioe.getMessage());
                }
                return new ArrayList<>();
            }
        }
        try {
            buffRead.close();
        } catch (final IOException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }

    /**
     * Construtor com inicialização dos blocos a partir de um arquivo.
     *
     * @param blockSize
     *            Tamanho de cada bloco.
     * @param blockNumber
     *            Numero de blocos.
     * @param path
     *            Caminho para o arquivo com os dados.
     * @throws FileNotFoundException
     *             Caso o caminho especificado seja invalido.
     * @throws IllegalArgumentException
     *             Caso o numero de blocos ou o tamanho do bloco seja nulo ou negativo.
     */
    public Memory(final int blockSize, final int blockNumber, final String path)
        throws FileNotFoundException {
        this(blockSize, blockNumber);
        final ArrayList<String> data = loadData(path);

        int block = 0;
        int pos = 0;

        for (final String string : data) {
            final String[] parsed = string.split(" ");
            for (final String string2 : parsed) {
                final int value = Integer.parseInt(string2);
                if (block >= blockNumber) {
                    throw new IndexOutOfBoundsException(
                    "O numero de dados lidos excede a capacidade solicitada para esta memoria.");
                }
                blocks[block].setAdress(pos++, value);
                if (pos >= blockSize) {
                    pos = 0;
                    ++block;
                }
            }
        }
    }

    /**
     * Construtor padrão.
     *
     * @param blockSize
     *            Tamanho de cada bloco.
     * @param blockNumber
     *            Numero de blocos.
     * @throws IllegalArgumentException
     *             Caso o numero de blocos ou o tamanho do bloco seja nulo ou negativo.
     */
    public Memory(final int blockSize, final int blockNumber) {
        if (blockNumber <= 0) {
            throw new IllegalArgumentException(
                            "Uma Memory com um numero nulo ou negativo de blocos é Inválida.");
        }
        blocks = new Block[blockNumber];
        this.blockSize = blockSize;
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(blockSize, i);
        }
    }

    /**
     * Informa o numero de blocos na memoria.
     *
     * @return O numero de blocos na memoria.
     */
    public int getNumberBlocks() {
        return blocks.length;
    }

    /**
     * Getter para o bloco na posição solicitada.
     *
     * @param number
     *            posição e numero do bloco solicitado.
     * @return Bloco solicitado.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso o numero informado seja inválido.
     */
    public Block getBlock(final int number) {
        return blocks[number];
    }

    /**
     * Informa o tamanho dos blocos da memoria.
     *
     * @return O tamanho dos blocos da memoria.
     */
    public final int getBlockSize() {
        return blockSize;
    }

    @Override
    public String toString() {
        final String head = "MEMORIA PRINCIPAL\nBloco - Endereço - Conteúdo\n";
        final StringBuilder body = new StringBuilder();
        for (final Block block : blocks) {
            body.append(block.toString());
        }
        return head + body.toString();
    }
}
