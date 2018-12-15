package mips;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a memory, either of instructions or of data, using text files.
 *
 * @author JohnVithor.
 */
public class Memory {
    private final String path;
    private final ArrayList<String> data;

    /**
     * Construct a memory from a path to a file.
     *
     * @param path
     *            The path of the file to be used.
     */
    public Memory(final String path) {
        this.path = path;
        this.data = new ArrayList<>();
        this.data.ensureCapacity(128);
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(path));
        } catch (final FileNotFoundException e) {
            System.out.println("Erro na leitura do arquivo, caminho não encontrado.\n"
                    + "Caminho utilizado: " + this.path);
            System.exit(1);
        }
        String line = null;
        try {
            line = buffRead.readLine();
        } catch (final IOException e1) {
            System.out.println("Erro na leitura do arquivo.");
            System.exit(1);
        }
        while (line != null) {
            this.data.add(line);
            try {
                line = buffRead.readLine();
            } catch (final IOException e) {
                System.out.println("Erro na leitura do arquivo.");
                System.exit(1);
            }
        }
        try {
            buffRead.close();
        } catch (final IOException e) {
            System.out.println("Erro ao fechar o arquivo.");
            System.exit(1);
        }
    }

    /**
     * Saves the contents of the memory.
     *
     */
    public void save() {
        BufferedWriter buffWrite = null;
        try {
            buffWrite = new BufferedWriter(new FileWriter(this.path));
        } catch (final IOException e) {
            System.out.println("Erro na escrita do arquivo");
            System.exit(1);
        }
        for (final String line : this.data) {
            try {
                buffWrite.append(line + "\n");
            } catch (final IOException e) {
                System.out.println("Erro na escrita do arquivo");
                System.exit(1);
            }
        }
        try {
            buffWrite.close();
        } catch (final IOException e) {
            System.out.println("Erro na escrita do arquivo");
            System.exit(1);
        }
    }

    /**
     * Retrieves the data located at the position indicated by i.
     *
     * @param id
     *            index of the position to be accessed.
     * @return content of the accessed position.
     */
    public final String getData(final Integer id) {
        if (id < this.data.size()) {
            return this.data.get(id);
        }
        return "end";
    }

    /**
     * Modifies the data located at the position indicated by i.
     *
     * @param i
     *            index of the position to be modified.
     * @param newData
     *            new data to occupy the position indicated by i.
     * @return true if the data has been modified, false if the position is not valid.
     */
    public final Boolean setData(final Integer i, final String newData) {
        if ((i < this.data.size()) && (i >= 0)) {
            this.data.set(i, newData);
            return true;
        }
        return false;
    }

    /**
     * Getter to the length of the memory.
     *
     * @return the length of the memory
     */
    public final Integer length() {
        return this.data.size();
    }
}
