package mips;

/**
 * Class Main (initiate the program).
 *
 * @author JohnVithor.
 */
public class Main {

    /**
     * main method (initiate the program).
     *
     * @param args
     *            arguments of the program
     */
    public static void main(final String[] args) {
        Boolean boolMode = false;
        Boolean boolPath = false;
        Integer mode = 0;
        String path = "";
        String modes = ("0: Sem Redirecionamento ou Reordenamento\n"
                + "1: Com Redirecionamento\n2: Com Reordenamento (não funciona corretamente)\n"
                + "3: Com Redirecionamento e com Reordenamento (não funciona corretamente)");
        for (String arg : args) {
            if (arg.contains("M:")) {
                final String[] argMode = arg.split(":");
                mode = Integer.parseInt(argMode[1]);
                if (mode > 3 || mode < 0) {
                    System.out.println("Esse modo não existe.\nModos válidos:\n" + modes);
                    System.out.println("Encerrando programa...");
                    return;
                } else if (mode > 1) {
                    System.out.println(
                            "Esse modo não funciona corretamente, por favor escolha outro.");
                    System.out.println("Encerrando programa...");
                    return;
                } else {
                    boolMode = true;
                }

            } else if (arg.contains("PATH:")) {
                final String[] argPath = arg.split(":");
                path = argPath[1];
                boolPath = true;
            } else {
                System.out.println("O argumento '" + arg + "' não existe, "
                        + "verifique se digitou corretamente ou se este argumento existe");
                return;
            }
        }
        if (!boolMode) {
            System.out.println("Não foi informado o modo a ser utilizado. Utilizando modo padrão.");
            System.out.println("Caso queira informar um modo, utilize o parametro 'M:'"
                    + " indicando um dos seguintes modos:\n" + modes + "\n");
        }
        if (!boolPath) {
            System.out.println("Não foi informado o caminho do arquivo com as instruções.\n"
                    + "Por favor informe um caminho para o arquivo com as instruções.\n"
                    + "Utilize o parametro 'PATH:' e em seguida o caminho a ser utilizado.");
            return;
        }

        final String[] withMode = { "sem utilizar Redirecionamento ou Reordenamento",
                "utilizando Redirecionamento", "utilizando Reordenamento",
                "utilizando tanto Redirecionamento como Reordenamento" };
        final String instructionPath = path;
        final ProcessorMips mips = new ProcessorMips(instructionPath, mode);
        System.out.println("Utilizando as instruções do arquivo: " + path);
        System.out.println("------------------------");
        do {
            mips.process();
            System.out.println("Ciclo " + mips.getCycles());
            System.out.println(mips);
            mips.movePipeline();
            System.out.println("------------------------");
        } while (!mips.hasTerminate());
        System.out.println("Esse conjunto de instruções levou " + mips.getCycles()
                + " ciclos para ser executado " + withMode[mode] + ".\n");
        System.out.println("A tabela de dependencias utilizada foi:");
        System.out.println(mips.getDependencies());
        return;
    }
}
