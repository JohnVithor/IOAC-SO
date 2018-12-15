package mips;

import java.util.ArrayList;

/**
 * This class stores the dependencies of a Instruction, indicating the line of the instructions that
 * this Instruction depends on and by which register the dependency occurs.
 *
 * @author JohnVithor
 *
 */
public class Dependency {
    private final Instruction instruction;
    private final ArrayList<Integer> dependencies;
    private final ArrayList<String> registers;

    /**
     * Default Constructor.
     *
     * @param instruction
     *            instruction that has that dependencies
     */
    public Dependency(final Instruction instruction) {
        this.instruction = instruction;
        this.dependencies = new ArrayList<>();
        this.registers = new ArrayList<>();
    }

    /**
     * Parameterized Constructor.
     *
     * @param instruction
     *            the instruction
     * @param dependecies
     *            the lines of the dependencies
     * @param registers
     *            the registers that are needed
     */
    public Dependency(final Instruction instruction, final ArrayList<Integer> dependecies,
            final ArrayList<String> registers) {
        this.instruction = instruction;
        this.dependencies = dependecies;
        this.registers = registers;
    }

    /**
     * Getter for the instruction with this dependencies.
     *
     * @return the line of the instruction.
     */
    public final Instruction getInstruction() {
        return this.instruction;
    }

    /**
     * Getter for the ArrayList with the lines of the dependencies.
     *
     * @return the array of dependencies.
     */
    public final ArrayList<Integer> getDependencies() {
        return this.dependencies;
    }

    /**
     * Getter for the ArrayList with the registers of the dependencies.
     *
     * @return the array of registers with dependency.
     */
    public final ArrayList<String> getRegisters() {
        return this.registers;
    }

    @Override
    public final String toString() {
        return String.format("%-20s%-15s%-15s", this.instruction.getInstruction(),
                this.dependencies.toString(), this.registers.toString());

    }
}
