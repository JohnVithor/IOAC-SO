package mips;

/**
 * Represents an instruction in its MIPS assembly format.
 *
 * @author JohnVithor.
 */
public class Instruction {
    private final String instructionString;

    private final String[] parsed;

    private Opcodes opcode;

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public Instruction(final String instruction) {
        if (instruction == null) {
            this.instructionString = "0";
        } else {
            this.instructionString = instruction;
        }

        this.parsed = this.instructionString.split(" ");
        this.opcode = Opcodes.ofCode(this.parsed[0]);
    }

    /**
     * Getter for the String of the statement used to construct the object.
     *
     * @return String of the statement used to construct the object.
     */
    public final String getInstruction() {
        return this.instructionString;
    }

    @Override
    public String toString() {
        return "" + this.instructionString;
    }

    /**
     * Returns the parameter indicated by the passed index.
     *
     * @param i
     *            index of the instruction parameter.
     * @return parameter indicated by index i.
     */
    public final String getParsed(final Short i) {
        if (i < 0 || i > (short) this.parsed.length) {
            throw new IndexOutOfBoundsException();
        }
        return this.parsed[i];
    }

    /**
     * Inform the OPCODE of the instruction.
     *
     * @return OPCODE of the instruction.
     */
    public final Opcodes getOpcode() {
        return this.opcode;
    }

    /**
     * Getter for the parameter that indicates where the next instruction is.
     *
     * @return parameter that indicates where the next instruction is.
     */
    public String getLabel() {
        return "";
    }

    /**
     * Returns the dependencies of this Instruction.
     * 
     * @param list
     *            of previous instructions
     * @return the dependencies of this instruction
     */
    public Dependency getDependecies(final Instruction[] list) {
        return new Dependency(this);
    }
}
