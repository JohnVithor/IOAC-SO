package cache;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import utility.Pair;

/**
 * Classe que modela uma Cache.
 *
 * @author JohnVithor
 */
public class Cache {
    /**
     * Linhas da Cache, onde os blocos trazidos da memoria associada são posicionados.
     */
    private final Block[] lines;
    /**
     * Contador de hits.
     */
    private int hits;
    /**
     * Contador de misses.
     */
    private int misses;
    /**
     * Tipo de mapeamento utilizado.
     */
    private final MapType mappingType;
    /**
     * Numero de conjuntos separadores para as linhas da Cache, caso necessário.
     */
    private final int setsNumber;
    /**
     * Tipo da politica de substituição utilizada.
     */
    private final SubsType subPolicy;
    /**
     * Geradador de numeros aleatorios para a politica de substituição randômica.
     */
    private Random random;
    /**
     * Listas com as frequencias de acesso de cada linha. Apenas para LFU.
     */
    private ArrayList<ArrayList<Pair<Integer, Integer>>> arrayLfuList;
    /**
     * Deques onde o primeiro elemento é o que está a mais tempo sem ser utilizado. Apenas para LRU.
     */
    private ArrayList<ArrayDeque<Integer>> arrayLruDeque;
    /**
     * Filas para ordem de saida da Cache. Apenas para FIFO.
     */
    private ArrayList<Queue<Integer>> arrayFifoQueue;
    /**
     * Memoria associada a esta Cache.
     */
    private final Memory memory;

    /**
     * Construtor para uma Cache com Mapeamento Direto.
     *
     * @param blockNumber
     *            Numero de Linhas na Cache.
     * @param mappingType
     *            Tipo de Mapeamento
     * @param memory
     *            Memória associada a esta Cache.
     */
    public Cache(final int blockNumber, final MapType mappingType, final Memory memory) {
        if (mappingType != MapType.DIRETO) {
            throw new IllegalArgumentException("Não foram fornecidas as outras informações.");
        }
        lines = new Block[blockNumber];
        this.mappingType = mappingType;
        setsNumber = 1;
        subPolicy = SubsType.INVALIDO;
        random = null;
        arrayLfuList = null;
        arrayLruDeque = null;
        arrayFifoQueue = null;
        this.memory = memory;
        hits = 0;
        misses = 0;
    }

    /**
     * Construtor para uma Cache com Mapeamento Totalmente Associativo.
     *
     * @param blockNumber
     *            Numero de Linhas na Cache.
     * @param mappingType
     *            Tipo de Mapeamento
     * @param subPolicy
     *            Politica de Substituição
     * @param memory
     *            Memória associada a esta Cache.
     */
    public Cache(final int blockNumber, final MapType mappingType, final SubsType subPolicy,
                    final Memory memory) {
        if (mappingType != MapType.ASSOCIATIVO_TOTAL) {
            if (mappingType == MapType.DIRETO) {
                throw new IllegalArgumentException(
                                "No mapeamento direto não aceitamos politicas de substituição.");
            }
            throw new IllegalArgumentException(
                            "No mapeamento parcialmente associativo é necessário informar o numero de conjuntos.");
        }
        lines = new Block[blockNumber];
        this.mappingType = mappingType;
        setsNumber = 1;
        this.subPolicy = subPolicy;
        this.memory = memory;
        hits = 0;
        misses = 0;
        setSubsPolicy();
    }

    /**
     * Construtor para uma Cache com Mapeamento Parcialmente Associativo.
     *
     * @param blockNumber
     *            Numero de Linhas na Cache.
     * @param mappingType
     *            Tipo de Mapeamento
     * @param setsNumber
     *            Numero de Conjuntos nas linhas Cache.
     * @param subPolicy
     *            Politica de Substituição
     * @param memory
     *            Memória associada a esta Cache.
     */
    public Cache(final int blockNumber, final MapType mappingType, final int setsNumber,
                    final SubsType subPolicy, final Memory memory) {
        if (mappingType != MapType.ASSOCIATIVO_PARCIAL) {
            throw new IllegalArgumentException(
                            "Não é necessário informar o numero de conjuntos.");
        }
        if ((setsNumber > blockNumber) || (setsNumber <= 0)) {
            throw new IllegalArgumentException("Numero de conjuntos informado é inválido.");
        }
        lines = new Block[blockNumber];
        this.mappingType = mappingType;
        this.setsNumber = setsNumber;
        this.subPolicy = subPolicy;
        this.memory = memory;
        hits = 0;
        misses = 0;
        setSubsPolicy();
    }

    /**
     * Cria o quer quer seja necessario para a utilização da politica de substituição escolhida.
     */
    private void setSubsPolicy() {
        random = null;
        arrayLfuList = null;
        arrayLruDeque = null;
        arrayFifoQueue = null;
        switch (subPolicy) {
            case ALEATORIO:
                random = new Random();
                return;
            case LFU:
                arrayLfuList = new ArrayList<>();
                for (int i = 0; i < setsNumber; ++i) {
                    arrayLfuList.add(new ArrayList<>(lines.length / setsNumber));
                }
                return;
            case LRU:
                arrayLruDeque = new ArrayList<>();
                for (int i = 0; i < setsNumber; ++i) {
                    arrayLruDeque.add(new ArrayDeque<>(lines.length / setsNumber));
                }
                return;
            case FIFO:
                arrayFifoQueue = new ArrayList<>();
                for (int i = 0; i < setsNumber; ++i) {
                    arrayFifoQueue.add(new ArrayDeque<>(lines.length / setsNumber));
                }
                return;
            default:
        }
    }

    /**
     * Obtem aleatoriamente uma posição para ser removida.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria a substituição.
     * @return um numero aleatorio entre 0 e o numero de linhas da Cache.
     */
    private int aleatorio(int set) {
        return random.nextInt(set * (lines.length / setsNumber) + (lines.length / setsNumber));
    }

    /**
     * O próximo elemento na fila de entrada dos blocos na Cache.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria a substituição.
     * @return O primeiro elemento na Fila.
     */
    private int fifo(int set) {
        final Integer poll = arrayFifoQueue.get(set).poll();
        if (poll != null) {
            return poll;
        }
        return -1;
    }

    /**
     * O bloco que está a mais tempo sem ser utilizado.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria a substituição.
     * @return O primeiro elemento no Deque de Blocos.
     */
    private int lru(int set) {
        final Integer last = arrayLruDeque.get(set).peek();
        if (last != null) {
            return last;
        }
        return -1;
    }

    /**
     * O elemento que tem a menor contagem de acessos atualmente na Cache.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria a substituição.
     * @return O menor elemento na Lista.
     */
    private int lfu(int set) {
        Pair<Integer, Integer> min = arrayLfuList.get(set).get(0);
        for (final Pair<Integer, Integer> pair : arrayLfuList.get(set)) {
            if (min.getValue() > pair.getValue()) {
                min = pair;
            }
        }
        arrayLfuList.get(set).remove(min);
        return min.getKey();
    }

    /**
     * Atualiza as estruturas de dados responsaveis pela politica de substituição da Cache.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria, ou não, a substituição.
     * @param index
     *            Indice da linha onde ocorreu um acesso.
     */
    private void updateSubsPolicy(int set, final int index) {
        switch (subPolicy) {
            case LFU:
                for (final Pair<Integer, Integer> pair : arrayLfuList.get(set)) {
                    if (pair.getKey().equals(index)) {
                        pair.setValue(pair.getValue() + 1);
                        return;
                    }
                }
                arrayLfuList.get(set).add(new Pair<>(index, 1));
                break;
            case LRU:
                for (final Integer integer : arrayLruDeque.get(set)) {
                    if (integer.equals(index)) {
                        arrayLruDeque.get(set).remove(integer);
                        break;
                    }
                }
                arrayLruDeque.get(set).addLast(index);
                break;
            case FIFO:
                arrayFifoQueue.get(set).add(index);
                break;
            default:
                break;
        }
    }

    /**
     * De acordo com a politica de substituição da Cache indica a linha a ser substituida.
     * 
     * @param set
     *            Indica o conjunto onde é necessaria a substituição.
     * @return Indice da linha a ser substituida.
     */
    private int posToSubstitute(int set) {
        switch (subPolicy) {
            case ALEATORIO:
                return aleatorio(set);
            case FIFO:
                return fifo(set);
            case LFU:
                return lfu(set);
            case LRU:
                return lru(set);
            default:
                return -1;
        }
    }

    /**
     * Indica se existe uma posição livre no Conjunto indicado.
     *
     * @param set
     *            Conjunto onde se busca uma posição livre.
     * @return O indice da posição livre, se encontrada e -1 caso não haja posição livre.
     */
    private int nextFree(final int set) {
        final int setSize = lines.length / setsNumber;
        for (int i = set * setSize; i < ((set + 1) * setSize); i++) {
            if (lines[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna o conteudo do endereço fornecido, atualizando as linhas da Cache caso necessário.
     * Utiliza uma busca de endereço numa Cache associativa, parcial ou total.
     *
     * @param adress
     *            Endereço a ser buscado.
     * @return Par contendo tanto o bloco onde o conteudo se encontra como o endereço dentro do
     *         bloco.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso o endereço informado seja superior ao maior endereço da memoria associada.
     */
    private Pair<Block, Integer> getAdressAssociative(final int adress) {
        final StringBuilder result = new StringBuilder();
        final int blockNumber = adress / memory.getBlockSize();
        if (blockNumber >= memory.getNumberBlocks()) {
            throw new ArrayIndexOutOfBoundsException(
                            "Bloco que contem o endereço " + adress + " não foi encontrado");
        }
        final int blockSet = blockNumber % setsNumber;
        final int setSize = lines.length / setsNumber;
        boolean found = false;
        Pair<Block, Integer> pair = null;
        for (int i = blockSet * setSize; i < ((blockSet + 1) * setSize); i++) {
            if ((lines[i] != null) && (lines[i].getNumber() == blockNumber)) {
                pair = new Pair<>(lines[i], adress % lines[i].getBlockSize());
                updateSubsPolicy(blockSet, i);
                result.append("HIT linha " + i);
                ++hits;
                found = true;
                break;
            }
        }
        if (!found) {
            int pos = nextFree(blockSet);
            final String missed = "MISS -> alocado na linha ";
            if (pos != -1) {
                result.append(missed + pos);
            } else {
                pos = posToSubstitute(blockSet);
                result.append(missed + pos);
                result.append(" -> bloco " + lines[pos].getNumber() + " substituido");
            }
            updateSubsPolicy(blockSet, pos);
            lines[pos] = memory.getBlock(blockNumber);
            ++misses;
            pair = new Pair<>(lines[pos], adress % lines[pos].getBlockSize());
        }

        System.out.print(result);
        return pair;
    }

    /**
     * Retorna o conteudo do endereço fornecido, atualizando as linhas da Cache caso necessário.
     * Utiliza uma busca de endereço numa Cache com Mapeamento Direto.
     *
     * @param adress
     *            Endereço a ser buscado.
     * @return Par contendo tanto o bloco onde o conteudo se encontra como o endereço dentro do
     *         bloco.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso o endereço informado seja superior ao maior endereço da memoria associada
     */
    private Pair<Block, Integer> getAdressDirect(final int adress) {
        final StringBuilder result = new StringBuilder();
        final int blockNumber = adress / memory.getBlockSize();
        if (blockNumber >= memory.getNumberBlocks()) {
            throw new ArrayIndexOutOfBoundsException(
                            "Bloco que contem o endereço " + adress
                                            + " não foi encontrado na memoria associada a esta Cache.");
        }
        final int pos = blockNumber % lines.length;
        Pair<Block, Integer> pair = null;
        final Block block = lines[pos];
        if ((block != null) && (block.getNumber() == blockNumber)) {
            result.append("HIT linha " + pos);
            ++hits;
            pair = new Pair<>(block, adress % block.getBlockSize());
        } else {
            lines[pos] = memory.getBlock(blockNumber);
            result.append("MISS -> alocado na linha " + pos);
            ++misses;
            if (block != null) {
                result.append(" -> bloco " + block.getNumber() + " substituido");
            }
            pair = new Pair<>(lines[pos], adress % lines[pos].getBlockSize());
        }
        System.out.print(result);
        return pair;
    }

    /**
     * Retorna o conteudo do endereço fornecido, atualizando as linhas da Cache caso necessário.
     *
     * @param adress
     *            Endereço a ser buscado.
     * @return Conteúdo do endereço informado.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso ocorra um erro durante a busca do bloco que contem o endereço fornecido.
     * @throws IllegalArgumentException
     *             Caso o endereço informado seja negativo.
     */
    public int getAdress(final int adress) {
        if (adress < 0) {
            throw new IllegalArgumentException("Endereços negativos não são válidos");
        }
        Pair<Block, Integer> pair = null;
        if (mappingType == MapType.DIRETO) {
            pair = getAdressDirect(adress);
            return pair.getKey().getAdress(pair.getValue());
        }
        pair = getAdressAssociative(adress);
        return pair.getKey().getAdress(pair.getValue());
    }

    /**
     * Altera o conteudo do endereço fornecido, atualizando as linhas da Cache caso necessário.
     *
     * @param adress
     *            Endereço a ser buscado.
     * @param value
     *            Novo conteúdo do endereço informado.
     * @throws ArrayIndexOutOfBoundsException
     *             Caso ocorra um erro durante a busca do bloco que contem o endereço fornecido.
     * @throws IllegalArgumentException
     *             Caso o endereço informado seja negativo.
     */
    public void setAdress(final int adress, final int value) {
        if (adress < 0) {
            throw new IllegalArgumentException("Endereços negativos não são válidos");
        }
        Pair<Block, Integer> pair = null;
        if (mappingType == MapType.DIRETO) {
            pair = getAdressDirect(adress);
            pair.getKey().setAdress(pair.getValue(), value);
        } else {
            pair = getAdressAssociative(adress);
            pair.getKey().setAdress(pair.getValue(), value);
        }
        System.out.print(" -> novo valor do endereço " + adress + " = " + value);
    }

    /**
     * Retorna a taxa de acerto da Cache.
     *
     * @return Taxa de acerto da Cache.
     * @throws ArithmeticException
     *             Caso ainda não tenha sido computado nenhum hit ou miss.
     */
    public double hitRate() {
        final double total = hits + (double) misses;
        if (total == 0) {
            throw new ArithmeticException(
                            "Ainda não foi contabilizado nenhum hit ou miss nesta cache");
        }
        return 100.0 * (hits / total);
    }

    /**
     * Retorna a taxa de erro da Cache.
     *
     * @return Taxa de erro da Cache.
     * @throws ArithmeticException
     *             Caso ainda não tenha sido computado nenhum hit ou miss.
     */
    public double missRate() {
        final double total = hits + (double) misses;
        if (total == 0) {
            throw new ArithmeticException(
                            "Ainda não foi contabilizado nenhum hit ou miss nesta cache");
        }
        return 100.0 * (misses / total);
    }

    @Override
    public String toString() {
        final String head = "CACHE L1\nLinha - Bloco - Endereço - Conteúdo\n";
        final StringBuilder body = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                body.append(i + " LIVRE\n");
            } else {
                body.append(lines[i].toString(i));
            }

        }
        return head + body;
    }
}
