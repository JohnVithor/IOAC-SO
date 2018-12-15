package mips;

/**
 * Represents an instruction in its MIPS assembly format of the branch type.
 *
 * @author JohnVithor.
 */
public class InstructionBranch extends Instruction3 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public InstructionBranch(final String instruction) {
        super(instruction);
    }

    @Override
    public final String getOperand1() {
        return get1();
    }

    @Override
    public final String getOperand2() {
        return get2();
    }

    @Override
    public final String getLabel() {
        return get3();
    }

}
