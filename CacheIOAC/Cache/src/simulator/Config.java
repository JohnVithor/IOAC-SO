package simulator;

import cache.MapType;
import cache.SubsType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe para armazenar as Configurações dos Blocos, da Memoria e da Cache lendo-as a partir de um
 * Arquivo.
 * 
 * @author JohnVithor
 */
public class Config {
    /**
     * Tamanho de cada Bloco (Numero de palavras por Bloco.)
     */
    private int blockSize;
    /**
     * Numero de linhas da Cache.
     */
    private int cacheLines;
    /**
     * Numero de Blocos na Memoria.
     */
    private int blockNumber;
    /**
     * Tipo do mapeamento da Cache.
     */
    private MapType mapType;
    /**
     * Numero de conjuntos da Cache, caso necessário.
     */
    private int setsNumber;
    /**
     * Tipo da politica de substituição da Cache.
     */
    private SubsType subsType;

    /**
     * Construtor básico de um Config, com valores padrões.
     */
    public Config() {
        blockSize = 4;
        cacheLines = 8;
        blockNumber = 16;
        mapType = MapType.ASSOCIATIVO_PARCIAL;
        setsNumber = 4;
        subsType = SubsType.ALEATORIO;
    }

    /**
     * Construtor padrão de um Config, informando o caminho para o arquivo a ser lido.
     *
     * @param path
     *            Caminho do arquivo a ser utilizado na criação do Config.
     * @throws FileNotFoundException
     *             Caso o caminho informado seja invalido.
     */
    public Config(final String path) throws FileNotFoundException {
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
            return;
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
                return;
            }
        }
        try {
            buffRead.close();
        } catch (final IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            blockSize = Integer.parseInt(data.get(0));
            cacheLines = Integer.parseInt(data.get(1));
            blockNumber = Integer.parseInt(data.get(2));
        } catch (NumberFormatException nfe) {
            System.err.println("Conteudo do arquivo informado para o Config não foi compreendido.");
            System.exit(1);
        }

        switch (Integer.parseInt(data.get(3))) {
            case 1:
                mapType = MapType.DIRETO;
                break;
            case 2:
                mapType = MapType.ASSOCIATIVO_TOTAL;
                break;
            case 3:
                mapType = MapType.ASSOCIATIVO_PARCIAL;
                break;
            default:
                mapType = MapType.INVALIDO;
                break;
        }
        setsNumber = Integer.parseInt(data.get(4));
        switch (Integer.parseInt(data.get(5))) {
            case 1:
                subsType = SubsType.ALEATORIO;
                break;
            case 2:
                subsType = SubsType.FIFO;
                break;
            case 3:
                subsType = SubsType.LFU;
                break;
            case 4:
                subsType = SubsType.LRU;
                break;
            default:
                subsType = SubsType.INVALIDO;
                break;
        }
    }

    /**
     * Informa o tamanho de cada Bloco da Memoria.
     *
     * @return O tamanho de cada Bloco da Memoria.
     */
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Informa o numero de linhas que a Cache suporta.
     *
     * @return O numero de linhas que a Cache suporta.
     */
    public int getCacheLines() {
        return cacheLines;
    }

    /**
     * Informa o numero de Blocos que a Memoria suporta.
     *
     * @return O numero de Blocos que a Memoria suporta.
     */
    public int getBlockNumber() {
        return blockNumber;
    }

    /**
     * Informa o tipo de Mapeamento.
     *
     * @return O tipo de Mapeamento.
     */
    public MapType getmType() {
        return mapType;
    }

    /**
     * Informa o numero de subdivisoes das linhas da Cache.
     *
     * @return O numero de subdivisoes das linhas da Cache.
     */
    public int getSetsNumber() {
        return setsNumber;
    }

    /**
     * Informa a politica de substituição.
     *
     * @return A politica de substituição.
     */
    public SubsType getsType() {
        return subsType;
    }

    @Override
    public String toString() {
        String mapTypeString = null;
        String subsTypeString = null;
        String conjuntos = "Não significa NADA pois o mapeamento não é parcialmente associativo";
        switch (subsType) {
            case ALEATORIO:
                subsTypeString = "Aleatorio";
                break;
            case FIFO:
                subsTypeString = "FIFO";
                break;
            case LFU:
                subsTypeString = "LFU";
                break;
            case LRU:
                subsTypeString = "LRU";
                break;
            default:
                subsTypeString = "Invalido";
                break;
        }
        String subsPolicy = "Política de substituição " + subsTypeString + "\n";
        switch (mapType) {
            case DIRETO:
                mapTypeString = "Mapeamento Direto";
                subsPolicy = "Mapeamento Direto não possui politica de substituição.";
                break;
            case ASSOCIATIVO_PARCIAL:
                mapTypeString = "Mapeamento Parcialmente Associativo";
                conjuntos = "Numero de conjuntos de linhas na Cache " + setsNumber;
                break;
            case ASSOCIATIVO_TOTAL:
                mapTypeString = "Mapeamento Totalmente Associativo";
                break;
            default:
                mapTypeString = "Invalido";
                break;
        }
        return "Cada bloco contém " + blockSize + " palavras\nA cache tem " + cacheLines
                        + " linhas\nA memória principal tem " + blockNumber + " blocos\n"
                        + mapTypeString + "\n" + conjuntos + "\n" + subsPolicy;
    }
}
