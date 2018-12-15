package mips;

/**
 * Represents an instruction in its MIPS assembly format of the jump type.
 *
 * @author JohnVithor.
 */
public class InstructionJump extends Instruction1 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public InstructionJump(final String instruction) {
        super(instruction);
    }

    @Override
    public final String getLabel() {
        return get1();
    }

    @Override
    public Dependency getDependecies(final Instruction[] list) {
        return new Dependency(this);
    }
}
