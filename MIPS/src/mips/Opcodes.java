package mips;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumerate type which indicates the possible opcodes of an instruction.
 *
 * @author JohnVithor.
 */
public enum Opcodes {
    /**
     * OPCODE for sum.
     */
    ADD("add"),
    /**
     * OPCODE for subtract.
     */
    SUB("sub"),
    /**
     * OPCODE for branch if equal.
     */
    BEQ("beq"),
    /**
     * OPCODE for branch if not equal.
     */
    BNE("bne"),
    /**
     * OPCODE for load word.
     */
    LW("lw"),
    /**
     * OPCODE for save word.
     */
    SW("sw"),
    /**
     * OPCODE for jump.
     */
    JUMP("j"),
    /**
     * OPCODE for a unknown opcode.
     */
    UNKNOWN("?");

    private static Map<String, Opcodes> cache = new HashMap<>();
    static {
        for (Opcodes g : values()) {
            cache.put(g.getOpcode().toLowerCase(), g);
            cache.put(g.getOpcode().toUpperCase(), g);
        }
    }

    private String opcode;

    private Opcodes(final String opcode) {
        this.opcode = opcode;
    }

    /**
     * Returns the string representing this OPCODE.
     *
     * @return the opcode of that enum as a string.
     */
    public final String getOpcode() {
        return this.opcode;
    }

    /**
     * Given a string is returned the opcode related to it, returns UNKNOW if the string is null.
     *
     * @param opcode
     *            string that represents the opcode.
     * 
     * @return The opcode related to it, returns UNKNOW if the string is null.
     */
    public static Opcodes ofCode(String opcode) {
        if (opcode == null) {
            return Opcodes.UNKNOWN;
        }
        return cache.get(opcode);
    }
}
