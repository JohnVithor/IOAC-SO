package mips;

import java.util.ArrayList;

/**
 * Represents an instruction that should only have THREE parameter after the OPCODE.
 *
 * @author JohnVithor.
 */
public abstract class Instruction3 extends Instruction2 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public Instruction3(final String instruction) {
        super(instruction);
    }

    /**
     * Returns the third parameter after the OPCODE of the instruction.
     *
     * @return the third parameter after the OPCODE of the instruction.
     */
    public final String get3() {
        return getParsed((short) 3).replace(",", "");
    }

    /**
     * Getter for the first operand of the instruction.
     *
     * @return first operand of the instruction.
     */
    public abstract String getOperand1();

    /**
     * Getter for the second operand of the instruction.
     *
     * @return second operand of the instruction.
     */
    public abstract String getOperand2();

    @Override
    public Dependency getDependecies(final Instruction[] list) {
        ArrayList<Integer> dependencies = new ArrayList<>();
        ArrayList<String> registers = new ArrayList<>();
        String op1 = this.getOperand1();
        String op2 = this.getOperand2();
        String needed = null;
        Instruction instruction = null;
        for (Integer i = list.length - 1; i >= 0; --i) {
            instruction = list[i];
            if (instruction != null) {
                if (instruction.getOpcode() == Opcodes.LW
                        || instruction instanceof InstructionArith) {
                    needed = ((Instruction2) instruction).get1();
                    if (op1.equals(needed) || op2.equals(needed)) {
                        dependencies.add(i + 1);
                        registers.add(needed);
                    }
                }
            }
        }
        return new Dependency(this, dependencies, registers);
    }
}
