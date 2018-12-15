package mips;

/**
 * Indicates the stages of the Pipeline.
 *
 * @author JohnVithor.
 */
public enum PipelineStage {
    /**
     * Search stage.
     */
    FIND, // (Fetch)
    /**
     * Decoding stage.
     */
    DECODE,
    /**
     * Execution stage.
     */
    EXECUTE,
    /**
     * Access to memory stage.
     */
    MEMORY,
    /**
     * Writing stage, either in memory or in the register bank.
     */
    WRITE;
}
