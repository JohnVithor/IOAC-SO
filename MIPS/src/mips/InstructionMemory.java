package mips;

import java.util.ArrayList;

/**
 * Represents an instruction in its MIPS assembly format of the memory access type.
 *
 * @author JohnVithor.
 */
public class InstructionMemory extends Instruction2 {

    /**
     * The default constructor of an instruction, receives a string with the statement in MIPS
     * assembly.
     *
     * @param instruction
     *            String with an instruction.
     */
    public InstructionMemory(final String instruction) {
        super(instruction);
    }

    /**
     * Getter for the parameter that indicates which register should store the loaded content / are
     * the content to be saved.
     *
     * @return parameter that indicates which register should store the loaded content / are the
     *         content to be saved.
     */
    public final String getReg() {
        return get1();
    }

    /**
     * Getter for the parameter that indicates the memory location to be accessed / modified.
     *
     * @return parameter that indicates the memory location to be accessed / modified.
     */
    public final String getMemo() {
        return get2();
    }

    /**
     * Getter for the parameter indicating the register containing part of the address of the memory
     * location to be accessed / modified.
     *
     * @return parameter indicating the register containing part of the address of the memory
     *         location to be accessed / modified
     */
    public final String getMemoReg() {
        String input = get2();
        input = input.substring(input.indexOf('(') + 1, input.lastIndexOf(')'));
        return input;

    }

    @Override
    public Dependency getDependecies(final Instruction[] list) {
        ArrayList<Integer> dependencies = new ArrayList<>();
        ArrayList<String> registers = new ArrayList<>();
        String op1 = this.getMemoReg();
        String needed = null;
        Instruction instruction = null;
        for (Integer i = list.length - 1; i >= 0; --i) {
            instruction = list[i];
            if (instruction != null) {
                if (instruction.getOpcode() == Opcodes.LW
                        || instruction instanceof InstructionArith) {
                    needed = ((Instruction2) instruction).get1();
                    if (op1.equals(needed)) {
                        dependencies.add(i + 1);
                        registers.add(needed);
                    }
                    if (this.getOpcode() == Opcodes.SW) {
                        if (this.getReg().equals(needed)) {
                            dependencies.add(i + 1);
                            registers.add(needed);
                        }
                    }
                }
                if (instruction.getOpcode() == Opcodes.SW && this.getOpcode() == Opcodes.LW) {
                    needed = ((InstructionMemory) instruction).getMemoReg();
                    op1 = this.getMemoReg();
                    if (op1.equals(needed)) {
                        dependencies.add(i + 1);
                        registers.add(needed);
                    }
                }
            }
        }
        return new Dependency(this, dependencies, registers);
    }
}
