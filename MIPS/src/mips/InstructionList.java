package mips;

import java.util.Arrays;

/**
 * This class indicates in which cycle each instruction should be started, in addition to storing
 * the instructions verifying which OPCODE it has.
 * 
 * @author JohnVithor
 *
 */
public class InstructionList {
    private Integer size;
    private Instruction[] instructions;
    private Integer[] instructionsCycle;

    /**
     * Construct the InstructionList using a Memory with the instructions.
     * 
     * @param instructions
     *            Memory with instructions in String
     */
    public InstructionList(final Memory instructions) {
        this.size = instructions.length();
        this.instructions = new Instruction[size];
        this.instructionsCycle = new Integer[size];
        for (int line = 0; line < size; ++line) {
            Instruction i = new Instruction(instructions.getData(line));
            InstructionType type = InstructionType.getType(i.getOpcode());
            switch (type) {
                case ATYPE:
                    this.instructions[line] = new InstructionArith(i.getInstruction());
                    break;
                case BTYPE:
                    this.instructions[line] = new InstructionBranch(i.getInstruction());
                    break;
                case JTYPE:
                    this.instructions[line] = new InstructionJump(i.getInstruction());
                    break;
                case MTYPE:
                    this.instructions[line] = new InstructionMemory(i.getInstruction());
                    break;
                case UNKNOW:
                    this.instructions[line] = null;
                    break;
                default:
                    this.instructions[line] = null;
                    break;
            }
        }
        resolveJumps();
    }
    
    /**
     * transfer instruction at oldPos to the newPos, moving all the instructions between that
     * indexes in one position
     * 
     * @param oldPos
     *            previous position of the instruction
     * 
     * @param newPos
     *            new position of the instruction
     * 
     */
    public void transfer(Integer oldPos, Integer newPos) {
        Instruction instr = this.instructions[oldPos];
        if (oldPos > newPos) {
            for (int i = oldPos-1; i >= newPos; i--) {
                instructions[i+1] = instructions[i];
            }
        } if (oldPos < newPos) {
            for (int i = oldPos+1; i < newPos; i++) {
                instructions[i-1] = instructions[i];
            }
        }
        instructions[newPos] = instr;
    }

    private void resolveJumps() {
        for (int i = 0; i < instructions.length; i++) {
            Instruction inst = instructions[i];
            if (inst == null) {
                continue;
            }
            if (inst instanceof InstructionBranch || inst instanceof InstructionJump) {
                int jump = Integer.parseInt(inst.getLabel()) - 1;
                for (int j = i + 1; j < jump; j++) {
                    instructions[j] = null;
                }
            }
        }
    }
    
    /**
     * Return the size of the array of instructions.
     * @return the size
     */
    public final Integer size() {
        return this.size;
    }
    
    /**
     * Returns an Array with the instructions before the indicated position.
     * 
     * @param id
     *            index of the first instruction after the last to be added in the return
     * @return Array with the instructions before the indicated by id
     */
    public final Instruction[] before(final Integer id) {
        Instruction[] temp = Arrays.copyOfRange(instructions, 0, id);
        return temp;
    }

    /**
     * Get the instruction indicated by id.
     * 
     * @param id
     *            number that indicate the instruction to be returned
     * @return instruction indicated by id
     */
    public final Instruction get(final Integer id) {
        return instructions[id];
    }

    /**
     * Change the cycle that the instruction indicated by id should start.
     * 
     * @param id
     *            number that indicate the instruction that the cycle will be modified
     * @param cycle
     *            cycle that the instruction should be placed in the pipeline
     */
    public final void setCycle(final Integer id, final Integer cycle) {
        instructionsCycle[id] = cycle;
    }

    /**
     * Get the cycle that the instruction indicated should be start.
     * 
     * @param id
     *            number that indicate the instruction to be checked
     * @return the cycle that the instruction indicated should be placed in the pipeline
     */
    public final Integer getCycle(final Integer id) {
        if (id < instructionsCycle.length && id >= 0) {
            return instructionsCycle[id];
        }
        return -1;
    }
}
