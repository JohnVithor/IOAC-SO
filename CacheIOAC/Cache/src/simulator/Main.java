package simulator;

import cache.Cache;
import cache.MapType;
import cache.Memory;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe onde o programa é iniciado.
 *
 * @author JohnVithor
 */
public class Main {
    private static Boolean boolConfig = false;
    private static Boolean boolData = false;
    private static String configpath = "";
    private static String datapath = "";

    /**
     * Função inicial do programa.
     *
     * @param args
     *            Argumentos para o inicio do programa.
     */
    public static void main(final String[] args) {
        getArgs(args);

        final Config config = createConfig(boolConfig, configpath);
        final Memory memory = createMemory(config, boolData, datapath);
        final Cache cache = createCache(config, memory);

        Command command = null;
        final Scanner in = new Scanner(System.in);
        do {
            command = processCommands(config, memory, cache, command, in);
        } while ((command == null) || (command.getcType() != CommandType.EXIT));
        in.close();
        System.out.println("Programa finalizado.");
    }

    /**
     * Extrai o caminho dos arquivos passados nos argumentos da inicialização do programa.
     * 
     * @param args
     *            Argumentos da inicialização do programa.
     */
    private static void getArgs(final String[] args) {
        for (final String arg : args) {
            if (arg.contains("C:") || arg.contains("c:")) {
                final String[] argConfig = arg.split(":");
                if (argConfig.length != 2) {
                    System.err.println("O argumento para a configuração não foi compreendido.");
                } else {
                    configpath = argConfig[1];
                    boolConfig = true;
                }

            } else if (arg.contains("D:") || arg.contains("d:")) {
                final String[] argData = arg.split(":");
                if (argData.length != 2) {
                    String msg = "O argumento para os dados da memoria não foi compreendido.";
                    System.err.println(msg);
                } else {
                    datapath = argData[1];
                    boolData = true;
                }

            } else {
                System.err.println(arg + "' não existe, verifique se digitou corretamente.");
                System.exit(1);
            }
        }
    }

    /**
     * Lê e processa os comandos recebidos pelo Scanner, realizando as operações solicitadas.
     *
     * @param config
     *            Arquivo com as configurações utilizadas.
     * @param memory
     *            Memoria associada com a Cache
     * @param cache
     *            Cache utilizada
     * @param command
     *            Comando a ser lido e executado
     * @param in
     *            Scanner associado a entrada dos comandos.
     * @return Comando lido e talvez executado.
     */
    private static Command processCommands(final Config config, final Memory memory,
                    final Cache cache, Command command, final Scanner in) {
        System.out.print("Command> ");
        final String line = in.nextLine();
        try {
            command = new Command(line);
        } catch (final IllegalArgumentException iae) {
            System.err.print(iae.getMessage() + "\n");
            return command;
        }
        System.out.print(command + " -> ");
        switch (command.getcType()) {
            case READ:
                commandRead(cache, command);
                break;
            case WRITE:
                commandWrite(cache, command);
                break;
            case HIT:
                commandHit(cache);
                break;
            case MISS:
                commandMiss(cache);
                break;
            case SHOW:
                System.out.println();
                System.out.print(cache);
                System.out.print(memory);
                break;
            case CONFIG:
                System.out.println();
                System.out.print(config);
                break;
            default:
                break;
        }
        return command;
    }

    /**
     * Processa o comando para mostrar a porcentagem de erros da Cache (miss).
     *
     * @param cache
     *            Cache a ser verificada.
     */
    private static void commandMiss(final Cache cache) {
        try {
            final double miss = cache.missRate();
            System.out.println(miss + "%");
        } catch (final ArithmeticException ae) {
            System.err.print(ae.getMessage() + "\n");
        }
    }

    /**
     * Processa o comando para mostrar a porcentagem de acertos da Cache (hit).
     *
     * @param cache
     *            Cache a ser verificada.
     */
    private static void commandHit(final Cache cache) {
        try {
            final double hit = cache.hitRate();
            System.out.println(hit + "%");
        } catch (final ArithmeticException ae) {
            System.err.print(ae.getMessage() + "\n");
        }
    }

    /**
     * Processa o comando para escrever na Cache e tambem na Memoria.
     *
     * @param cache
     *            Cache onde o dado será escrito.
     * @param command
     *            Comando com o endereço e com o valor a ser escrito.
     */
    private static void commandWrite(final Cache cache, final Command command) {
        try {
            cache.setAdress(command.getAdress(), command.getValue());
            System.out.println();
        } catch (final RuntimeException re) {
            System.err.print(re.getMessage() + "\n");
        }
    }

    /**
     * Processa o comando para ler da Cache.
     *
     * @param cache
     *            Cache de onde dado será lido.
     * @param command
     *            Comando com o endereço a ser lido.
     */
    private static void commandRead(final Cache cache, final Command command) {
        int value;
        try {
            value = cache.getAdress(command.getAdress());
            System.out.println(" -> " + command.getAdress() + " = " + value);
        } catch (final RuntimeException re) {
            System.err.print(re.getMessage() + "\n");
        }
    }

    /**
     * Cria uma Cache a partir do arquivo de configuração e de uma memoria.
     *
     * @param config
     *            Configurações da Cache.
     * @param memory
     *            Memoria a ser associada a Cache.
     * @return Cache criada.
     */
    private static Cache createCache(final Config config, final Memory memory) {
        Cache cache = null;
        switch (config.getmType()) {
            case DIRETO:
                cache = new Cache(config.getCacheLines(), MapType.DIRETO, memory);
                break;
            case ASSOCIATIVO_TOTAL:
                cache = new Cache(config.getCacheLines(), MapType.ASSOCIATIVO_TOTAL,
                                config.getsType(), memory);
                break;
            case ASSOCIATIVO_PARCIAL:
                cache = new Cache(config.getCacheLines(), MapType.ASSOCIATIVO_PARCIAL,
                                config.getSetsNumber(),
                                config.getsType(), memory);
                break;
            default:
                System.err.println("Não foi compreendido o Mapeamento desejado.");
                System.exit(1);
        }
        return cache;
    }

    /**
     * Cria uma Memory a partir do arquivo de configuração e caso, tenha sido informado, carrega os
     * dados iniciais da memoria a partir de uma arquivo.
     *
     * @param config
     *            Configuração da Memory a ser criada.
     * @param boolData
     *            Informa se é necessário carregar os dados da Memoria de um arquivo.
     * @param datapath
     *            Caminho para o arquivo com os dados da memoria
     * @return Memoria criada.
     */
    private static Memory createMemory(final Config config, final Boolean boolData,
                    final String datapath) {
        Memory memory;
        if (!boolData) {
            System.err.println("Arquivo com os dados iniciais não informado.\n"
                            + "Todas as posições de memoria serão iniciadas com '0'");
            System.err.println("Utilize 'D:caminho_do_arquivo' para iniciar a memoria.");
            memory = new Memory(config.getBlockSize(), config.getBlockNumber());
        } else {
            try {
                memory = new Memory(config.getBlockSize(), config.getBlockNumber(), datapath);
            } catch (final FileNotFoundException fnfe) {
                System.err.println("Ocorreu um erro durante a leitura dos dados.");
                System.err.println(fnfe.getMessage());
                System.err.println("Todas as posições de memoria serão iniciadas com '0'.");
                memory = new Memory(config.getBlockSize(), config.getBlockNumber());
            }

        }
        return memory;
    }

    /**
     * Cria um Config, caso seja informado um arquivo com as configurações utiliza os dados dele,
     * caso contrário, utiliza a configuraçõ padrão.
     *
     * @param boolConfig
     *            Informa se foi indicado um caminho para o arquivo com as configurações.
     * @param configpath
     *            Caminho para o arquivo com as configurações.
     * @return Config criada.
     */
    private static Config createConfig(final Boolean boolConfig, final String configpath) {
        Config config;
        if (!boolConfig) {
            System.err.println("Utilizando configuração padrão.");
            System.err.println("Utilize 'C:caminho_do_arquivo' para usar outra configuração.");
            config = new Config();
        } else {
            try {
                config = new Config(configpath);
            } catch (final FileNotFoundException fnfe) {
                System.err.println("Ocorreu um erro durante a leitura da configuração.");
                System.err.println(fnfe.getMessage() + " Utilizando configuração padrão.");
                config = new Config();
            }

        }
        return config;
    }
}
