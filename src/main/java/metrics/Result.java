package metrics;

public class Result {

    private final String algorithm;
    private final String input;
    private final int n;
    private final int m;
    private final double timeMs;
    private final long operations;
    private final long comparisons;
    private final int answer;

    public Result(String algorithm, String input, int n, int m,
                  double timeMs, long operations,
                  long comparisons, int answer) {
        this.algorithm = algorithm;
        this.input = input;
        this.n = n;
        this.m = m;
        this.timeMs = timeMs;
        this.operations = operations;
        this.comparisons = comparisons;
        this.answer = answer;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getInput() {
        return input;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public double getTimeMs() {
        return timeMs;
    }

    public long getOperations() {
        return operations;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getAnswer() {
        return answer;
    }
}
