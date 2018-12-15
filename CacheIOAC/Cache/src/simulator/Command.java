package simulator;

/**
 * Classe que modela um Comando para uma operação na Cache.
 * 
 * @author JohnVithor
 */
public class Command {
    /**
     * Tipo do comando.
     */
    private CommandType commandType;
    /**
     * String utilizada na construção do comando.
     */
    private String commandString;
    /**
     * Endereço para o comando solicitado, caso exista.
     */
    private Integer adress;
    /**
     * Valor para o comando solicitado, caso exista.
     */
    private Integer value;

    /**
     * Construtor padrão de um comando.
     *
     * @param commandString
     *            String que contem o conteudo do comando.
     * @throws NullPointerException
     *             Caso a String passada seja null.
     * @throws IllegalArgumentException
     *             Caso o comando passado esteja na forma errada ou o comando seja invalido.
     * @throws NumberFormatException
     *             Caso o endereço ou o valor a ser escrito não seja compreendido.
     */
    public Command(final String commandString) {
        if (commandString == null) {
            throw new NullPointerException("Um comando não pode ser null!");
        }
        this.commandString = commandString;
        adress = null;
        value = null;

        final String[] parsed = commandString.split(" ");
        switch (parsed.length) {
            case 1:
                verifyType1(parsed[0]);
                break;
            case 2:
                if (parsed[0].equals("Read") || parsed[0].equals("read") || parsed[0].equals("r")) {
                    commandType = CommandType.READ;
                    adress = Integer.parseInt(parsed[1]);
                    return;
                }
                throw new IllegalArgumentException("Apenas o comando Read tem um único argumento!");
            case 3:
                if (parsed[0].equals("Write") || parsed[0].equals("write")
                                || parsed[0].equals("w")) {
                    commandType = CommandType.WRITE;
                    adress = Integer.parseInt(parsed[1]);
                    value = Integer.parseInt(parsed[2]);
                    return;
                }
                throw new IllegalArgumentException("Apenas o comando Write tem dois argumentos!");
            default:
                throw new IllegalArgumentException("Comando não foi compreendido!");
        }
    }

    /**
     * Verifica se a string lida é um dos comandos sem parâmetros.
     * 
     * @param parsed
     *            Primeiro e unico parametro do potencial comando.
     */
    private void verifyType1(final String first) {
        if (first.equals("Show") || first.equals("show") || first.equals("s")) {
            commandType = CommandType.SHOW;
            return;
        }
        if (first.equals("Exit") || first.equals("exit") || first.equals("e")) {
            commandType = CommandType.EXIT;
            return;
        }
        if (first.equals("Config") || first.equals("config") || first.equals("c")) {
            commandType = CommandType.CONFIG;
            return;
        }
        if (first.equals("Hit") || first.equals("hit") || first.equals("h")) {
            commandType = CommandType.HIT;
            return;
        }
        if (first.equals("Miss") || first.equals("miss") || first.equals("m")) {
            commandType = CommandType.MISS;
            return;
        }
        if (first.equals("Write") || first.equals("write") || first.equals("w")
                        || first.equals("Read") || first.equals("read") || first.equals("r")) {
            throw new IllegalArgumentException(
                            "Os comandos Read e Write possuem argumentos!");
        } else {
            throw new IllegalArgumentException("Comando não foi compreendido!");
        }
    }

    /**
     * Informa o tipo do comando.
     *
     * @return O tipo do comando.
     */
    public CommandType getcType() {
        return commandType;
    }

    /**
     * Informa a String utilizada para criar o comando.
     *
     * @return A String utilizada para criar o comando.
     */
    public String getCommand() {
        return commandString;
    }

    /**
     * Informa o endereço associado a este comando.
     *
     * @return O endereço associado a este comando.
     * @throws UnsupportedOperationException
     *             Caso o tipo do Comando seja diferente de READ ou WRITE.
     */
    public Integer getAdress() {
        if (commandType == CommandType.READ || commandType == CommandType.WRITE) {
            return adress;
        }
        throw new UnsupportedOperationException("Esse tipo de comando não possui endereço.");
    }

    /**
     * Informa o valor associado a este comando.
     *
     * @return O valor associado a este comando.
     * @throws UnsupportedOperationException
     *             Caso o tipo do Comando seja diferente de WRITE.
     */
    public Integer getValue() {
        if (commandType == CommandType.WRITE) {
            return value;
        }
        throw new UnsupportedOperationException("Esse tipo de comando não possui um valor.");
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        switch (commandType) {
            case READ:
                result.append("Read " + adress);
                break;
            case WRITE:
                result.append("Write " + adress + " " + value);
                break;
            case SHOW:
                result.append("Show");
                break;
            case EXIT:
                result.append("Exit");
                break;
            case CONFIG:
                result.append("Config");
                break;
            case HIT:
                result.append("Hit");
                break;
            case MISS:
                result.append("Miss");
                break;
            default:
                return "Comando invalido!";
        }
        return result.toString();
    }
}
