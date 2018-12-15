package mips;

/**
 * An enumerate type that represents the possible types of an instruction.
 *
 * @author JohnVithor.
 */
public enum InstructionType {
    /**
     * JUMP Type.
     */
    JTYPE,
    /**
     * MEMORY Type.
     */
    MTYPE,
    /**
     * ARITH Type.
     */
    ATYPE,
    /**
     * BRANCH Type.
     */
    BTYPE,
    /**
     * Unknown Type (an possible error).
     */
    UNKNOW;

    /**
     * Given the opcode of an instruction it is returned which type of it.
     *
     * @param opcode
     *            indicates the opcode to be tested.
     * @return instruction type.
     */
    public static InstructionType getType(Opcodes opcode) {
        if (opcode == null) {
            return InstructionType.UNKNOW;
        }
        switch (opcode) {
            case ADD:
                return InstructionType.ATYPE;
            case SUB:
                return InstructionType.ATYPE;
            case BEQ:
                return InstructionType.BTYPE;
            case BNE:
                return InstructionType.BTYPE;
            case LW:
                return InstructionType.MTYPE;
            case SW:
                return InstructionType.MTYPE;
            case JUMP:
                return InstructionType.JTYPE;
            default:
                return InstructionType.UNKNOW;
        }
    }
}
