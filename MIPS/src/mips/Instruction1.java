package mips;

/**
 * Represents an instruction that should only have ONE parameter after the OPCODE.
 *
 * @author JohnVithor.
 */
public class Instruction1 extends Instruction {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public Instruction1(final String instruction) {
        super(instruction);
    }

    /**
     * Returns the first parameter after the OPCODE of the instruction.
     *
     * @return first parameter after the OPCODE of the instruction.
     */
    public final String get1() {
        return getParsed((short) 1).replace(",", "");
    }

}
