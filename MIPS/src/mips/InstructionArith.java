package mips;

/**
 * Represents an instruction in its MIPS assembly format of the arithmetic type.
 *
 * @author JohnVithor.
 */
public class InstructionArith extends Instruction3 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public InstructionArith(final String instruction) {
        super(instruction);
    }

    /**
     * Getter for the parameter where the result should be stored.
     *
     * @return the parameter where the result should be stored.
     */
    public final String getResult() {
        return get1();
    }

    @Override
    public final String getOperand1() {
        return get2();
    }

    @Override
    public final String getOperand2() {
        return get3();
    }

}
