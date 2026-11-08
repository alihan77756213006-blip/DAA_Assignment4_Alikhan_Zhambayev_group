package metrics;

public class Metrics {

    private long operations;
    private long comparisons;

    public void incrementOperations() {
        operations++;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public long getOperations() {
        return operations;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void reset() {
        operations = 0;
        comparisons = 0;
    }
}
