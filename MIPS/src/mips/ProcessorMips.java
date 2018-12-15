package mips;

import java.util.ArrayList;

/**
 * Represents an Processor based in the MIPS processor, but with fewer instructions.
 *
 * @author JohnVithor.
 *
 */
public class ProcessorMips {
    private Integer pc;
    private Integer cycles;
    private Integer count;
    private Memory instructions;
    private DependencyTable dependencies;
    private InstructionList instructionsList;

    private enum PipelineStages {
        FIND, TODECODE, DECODE, TOEXECUTE, EXECUTE, TOMEMORY, MEMORY, TOWRITE, WRITE;
    }

    private final Instruction[] pipelineStages;

    /**
     * Default Constructor.
     *
     * @param instructionsPath
     *            The path to the instruction set
     * @param mode
     *            Indicate the mode to resolve the hazards
     */
    public ProcessorMips(final String instructionsPath, final Integer mode) {
        this.pipelineStages = new Instruction[10];
        this.newIntructions(instructionsPath, mode);
    }

    /**
     * Checks if the loaded program has terminated.
     *
     * @return true if the loaded program has terminated, false otherwise.
     */
    public final Boolean hasTerminate() {
        Instruction instruction = null;
        for (Integer i = PipelineStages.FIND.ordinal(); i <= PipelineStages.TOWRITE
                .ordinal(); ++i) {
            instruction = this.pipelineStages[i];
            if (instruction != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Change the path to the file with the instructions.
     *
     * @param instructionsPath
     *            path to the instruction memory.
     * @param mode
     *            mode to resolve the conflicts
     */
    public final void newIntructions(final String instructionsPath, final Integer mode) {
        this.pc = 0;
        this.cycles = 0;
        this.count = 0;
        this.instructions = new Memory(instructionsPath);
        this.instructionsList = new InstructionList(this.instructions);
        this.dependencies = new DependencyTable(this.instructionsList.size());
        this.checkDependencies();
        switch (mode) {
            case 0:
                this.resolveHazards();
                break;
            case 1:
                this.resolveHazardsForwarding();
                break;
            case 2:
                this.resolveHazardsReordering();
                this.checkDependencies();
                this.resolveHazards();
                break;
            case 3:
                this.resolveHazardsReordering();
                this.checkDependencies();
                this.resolveHazardsForwarding();
                break;
            default:
                this.resolveHazards();
                break;
        }
    }

    /**
     * process the instructions using a pipeline.
     */
    public final void process() {
        this.find();
        this.decode();
        this.execute();
        this.memory();
        this.write();
        ++this.cycles;
    }

    /**
     * Move the instructions in each pipeline stage to the respective next stage.
     */
    public final void movePipeline() {
        this.pipelineStages[PipelineStages.TOWRITE
                .ordinal()] = this.pipelineStages[PipelineStages.MEMORY.ordinal()];
        this.pipelineStages[PipelineStages.MEMORY.ordinal()] = null;
        this.pipelineStages[PipelineStages.TOMEMORY
                .ordinal()] = this.pipelineStages[PipelineStages.EXECUTE.ordinal()];
        this.pipelineStages[PipelineStages.EXECUTE.ordinal()] = null;
        this.pipelineStages[PipelineStages.TOEXECUTE
                .ordinal()] = this.pipelineStages[PipelineStages.DECODE.ordinal()];
        this.pipelineStages[PipelineStages.DECODE.ordinal()] = null;
        this.pipelineStages[PipelineStages.TODECODE
                .ordinal()] = this.pipelineStages[PipelineStages.FIND.ordinal()];
        this.pipelineStages[PipelineStages.FIND.ordinal()] = null;
    }

    private void find() {
        if (this.count == 0) {
            Instruction i = this.instructionsList.get(this.pc);
            while (i == null) {
                ++this.pc;
                i = this.instructionsList.get(this.pc);
            }
            this.pipelineStages[PipelineStages.FIND.ordinal()] = i;
            ++this.pc;
            this.count = this.instructionsList.getCycle(this.pc);
        }
        --this.count;
    }

    private void decode() {
        final Instruction i = this.pipelineStages[PipelineStages.TODECODE.ordinal()];
        this.pipelineStages[PipelineStages.DECODE.ordinal()] = i;
    }

    private void execute() {
        final Instruction i = this.pipelineStages[PipelineStages.TOEXECUTE.ordinal()];
        this.pipelineStages[PipelineStages.EXECUTE.ordinal()] = i;
    }

    private void memory() {
        final Instruction i = this.pipelineStages[PipelineStages.TOMEMORY.ordinal()];
        this.pipelineStages[PipelineStages.MEMORY.ordinal()] = i;
    }

    private void write() {
        final Instruction i = this.pipelineStages[PipelineStages.TOWRITE.ordinal()];
        this.pipelineStages[PipelineStages.WRITE.ordinal()] = i;
    }

    @Override
    public final String toString() {
        return "IF:  " + this.pipelineStages[PipelineStages.FIND.ordinal()] + "\n" + "ID:  "
                + this.pipelineStages[PipelineStages.DECODE.ordinal()] + "\n" + "EXE: "
                + this.pipelineStages[PipelineStages.EXECUTE.ordinal()] + "\n" + "MEM: "
                + this.pipelineStages[PipelineStages.MEMORY.ordinal()] + "\n" + "WB:  "
                + this.pipelineStages[PipelineStages.WRITE.ordinal()];
    }

    /**
     * Getter to the number of cycles.
     *
     * @return the number of cycles that the processor has utilized.
     */
    public Integer getCycles() {
        return this.cycles;
    }

    private void checkDependencies() {
        this.dependencies.clear();
        for (int line = 0; line < this.instructionsList.size(); ++line) {
            final Instruction i = this.instructionsList.get(line);
            Dependency d = new Dependency(null);
            if (i != null) {
                d = i.getDependecies(this.instructionsList.before(line));
            }
            this.dependencies.placeAt(d, line);
        }
    }

    private void resolveHazards() {
        Integer cycle = 0;
        ArrayList<Integer> dependencies = null;
        for (int id = 0; id < this.instructionsList.size(); ++id) {
            final Dependency dep = this.dependencies.get(id);
            dependencies = dep.getDependencies();
            if (dependencies.isEmpty()) {
                cycle = 1;
            } else {
                cycle = resolveMultipleHazards(id, dependencies);
            }
            this.instructionsList.setCycle(id, cycle);
        }
    }

    /**
     * Fail...
     */
    private void resolveHazardsReordering() {
        ArrayList<Integer> independents = new ArrayList<>();
        for (int id = 1; id < this.instructionsList.size(); id++) {
            final ArrayList<Integer> dependencies = this.dependencies.get(id).getDependencies();
            Instruction i = instructionsList.get(id);
            Boolean isJump = i.getOpcode() == Opcodes.JUMP;
            Boolean isBranch = i instanceof InstructionBranch; 
            if (dependencies.isEmpty() && !isJump && !isBranch) {
                independents.add(id);
            }
        }
        if (independents.isEmpty()) {
            return;
        }
        int j = 0;
        int indie = independents.get(j);
        for (int id = 1; id < this.instructionsList.size(); id++) {
            final ArrayList<Integer> dependencies = this.dependencies.get(id).getDependencies();
            if (!dependencies.isEmpty()) {
                this.instructionsList.transfer(indie, id);
                ++j;
                try {
                    independents.get(j);
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }
    }
    
    private void resolveHazardsForwarding() {
        Integer cycle = 4;
        Instruction i = null;
        String lastDependency = null;
        String registerDependent = null;
        for (int id = 0; id < this.instructionsList.size(); id++) {
            lastDependency = null;
            registerDependent = null;
            final ArrayList<Integer> dependencies = this.dependencies.get(id).getDependencies();
            if (dependencies.isEmpty()) {
                cycle = 1;
            } else {
                for (int j = 0; j < dependencies.size(); j++) {
                    i = this.instructionsList.get(dependencies.get(j) - 1);
                    registerDependent = this.dependencies.get(id).getRegisters().get(j);
                    if (registerDependent.equals(lastDependency)) {
                        continue;
                    }
                    lastDependency = registerDependent;
                    if (i instanceof InstructionArith) {
                        if (this.instructionsList.get(id - 1) == i) {
                            if (this.instructionsList.get(id).getOpcode() == Opcodes.SW) {
                                cycle = 2;
                            } else {
                                cycle = 1;
                            }
                        } else {
                            cycle = resolveMultipleHazards(id, dependencies);
                        }
                    } else if (i.getOpcode() == Opcodes.LW) {
                        if (this.instructionsList.get(id - 1) == i) {
                            cycle = 2;
                        } else {
                            cycle = resolveMultipleHazards(id, dependencies);
                        }
                    }
                }
            }
            this.instructionsList.setCycle(id, cycle);
        }
    }

    /**
     * Return the correct number of cycles that the instruction indicated by id should wait before
     * can enter in the pipeline.
     * 
     * @param id
     *            indicate the line of the instruction
     * @param dependencies
     *            the list of dependencies of the instruction
     * @return
     */
    private Integer resolveMultipleHazards(final Integer id,
            final ArrayList<Integer> dependencies) {
        Integer cycle = 1;
        int a = 1;
        int tempCycle = 0;
        for (int d = 0; d < dependencies.size(); d++) {
            for (int k = instructionsList.before(id).length - 1; k > dependencies.get(d) - 1; k--) {
                if (a >= 5) {
                    break;
                }
                a += instructionsList.getCycle(k);
            }
            if (a >= 5) {
                tempCycle = 1;
            } else {
                tempCycle = 5 - a;
            }
            if (tempCycle > cycle) {
                cycle = tempCycle;
            }
        }
        return cycle;
    }

    /**
     * Getter to the DependencyTable.
     *
     * @return the list of dependencies in this group of instructions
     */
    public final DependencyTable getDependencies() {
        return this.dependencies;
    }
}
