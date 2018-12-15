package mips;

/**
 * Represents an instruction that should only have TWO parameter after the OPCODE.
 *
 * @author JohnVithor.
 */
public class Instruction2 extends Instruction1 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public Instruction2(final String instruction) {
        super(instruction);
    }

    /**
     * Returns the second parameter after the OPCODE of the instruction.
     *
     * @return second parameter after the OPCODE of the instruction.
     */
    public final String get2() {
        return getParsed((short) 2).replace(",", "");
    }
}
