package mips;

import java.util.ArrayList;

/**
 * This class only stores an array of Dependencies.
 * 
 * @author JohnVithor
 *
 */
public class DependencyTable {
    private final ArrayList<Dependency> dependencies;

    /**
     * Default Constructor.
     */
    public DependencyTable() {
        this.dependencies = new ArrayList<>();
    }

    /**
     * Construct the DependencyTable with an initial size.
     * 
     * @param size
     *            Initial size of the DependencyTable array
     */
    public DependencyTable(final Integer size) {
        this.dependencies = new ArrayList<>(size);
    }

    /**
     * Change the capacity of the DependencyTable.
     *
     * @param size
     *            new size of the DependencyTable array
     */
    public final void setSize(final Integer size) {
        this.dependencies.ensureCapacity(size);
    }

    /**
     * Add a dependency at the position indicated.
     *
     * @param d
     *            dependency to be added
     * @param pos
     *            position of the dependency
     */
    public final void placeAt(final Dependency d, final Integer pos) {
        this.dependencies.add(pos, d);
    }

    /**
     * Get the dependency of the position indicated.
     *
     * @param id
     *            the index of the Dependency
     * @return the Dependency at index id
     */
    public final Dependency get(final Integer id) {
        Dependency dep;
        try {
            dep = this.dependencies.get(id);
        } catch (final IndexOutOfBoundsException e) {
            return new Dependency(null);
        }
        return dep;
    }

    /**
     * Erase all the dependencies.
     */
    public final void clear() {
        dependencies.clear();
    }
    
    @Override
    public final String toString() {
        String result = "";
        for (int id = 0; id < this.dependencies.size(); id++) {
            if (this.dependencies.get(id).getInstruction() != null) {
                result = result
                        + String.format("Line: %2d %15s", (id + 1), this.dependencies.get(id))
                        + "\n";
            }
        }
        return result;
    }
}
