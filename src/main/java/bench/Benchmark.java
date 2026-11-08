package bench;

import algorithms.EditDistance;
import algorithms.StringSearch;
import metrics.CsvWriter;
import metrics.Metrics;
import metrics.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Benchmark {

    private static final int RUNS = 5;

    private static final int[] RECURSIVE_SIZES = {
            2, 4, 6, 8, 10
    };

    private static final int[] DP_SIZES = {
            100, 200, 400, 800, 1600
    };

    private static final int[] SEARCH_SIZES = {
            1000, 4000, 16000, 64000, 256000
    };

    private static final List<Result> results = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        System.out.println("Starting Assignment 4 benchmarks...");

        runEditDistance();
        runStringSearch();

        CsvWriter.write("results/results.csv", results);

        System.out.println();
        System.out.println("Benchmark completed.");
        System.out.println("Results saved to results/results.csv");
        System.out.println("Total records: " + results.size());
    }

    private static void runEditDistance() {

        EditDistance.Algorithm[] algorithms = {
                new EditDistance.Recursive(),
                new EditDistance.Table(),
                new EditDistance.TwoRows()
        };

        String[] names = {
                "Recursive",
                "Table",
                "TwoRows"
        };

        String[] inputTypes = {
                "random",
                "different"
        };

        for (int a = 0; a < algorithms.length; a++) {

            int[] sizes = (a == 0)
                    ? RECURSIVE_SIZES
                    : DP_SIZES;

            for (String inputType : inputTypes) {
                for (int n : sizes) {

                    String[] input = inputType.equals("random")
                            ? InputGenerator.editRandom(n)
                            : InputGenerator.editDifferent(n);

                    benchmarkEdit(
                            names[a],
                            algorithms[a],
                            inputType,
                            n,
                            input[0],
                            input[1]
                    );
                }
            }
        }
    }

    private static void benchmarkEdit(
            String name,
            EditDistance.Algorithm algorithm,
            String inputType,
            int n,
            String a,
            String b) {

        double[] times = new double[RUNS];
        long[] operations = new long[RUNS];
        int[] answers = new int[RUNS];

        for (int run = 0; run < RUNS; run++) {
            Metrics metrics = new Metrics();

            long start = System.nanoTime();
            int answer = algorithm.distance(a, b, metrics);
            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;
            operations[run] = metrics.getOperations();
            answers[run] = answer;
        }

        double median = median(times);

        for (int run = 0; run < RUNS; run++) {
            results.add(new Result(
                    name,
                    inputType,
                    n,
                    b.length(),
                    times[run],
                    operations[run],
                    0,
                    answers[run]
            ));
        }

        System.out.printf(
                "Edit %-10s %-10s n=%-7d median=%.4f ms%n",
                name, inputType, n, median
        );
    }

    private static void runStringSearch() {

        StringSearch.Algorithm[] algorithms = {
                new StringSearch.Naive(),
                new StringSearch.KMP()
        };

        String[] names = {
                "Naive",
                "KMP"
        };

        String[] inputTypes = {
                "random",
                "worst"
        };

        for (int a = 0; a < algorithms.length; a++) {
            for (String inputType : inputTypes) {
                for (int n : SEARCH_SIZES) {

                    String[] input = inputType.equals("random")
                            ? InputGenerator.searchRandom(n)
                            : InputGenerator.searchWorstCase(n);

                    benchmarkSearch(
                            names[a],
                            algorithms[a],
                            inputType,
                            n,
                            input[0],
                            input[1]
                    );
                }
            }
        }
    }

    private static void benchmarkSearch(
            String name,
            StringSearch.Algorithm algorithm,
            String inputType,
            int n,
            String text,
            String pattern) {

        double[] times = new double[RUNS];
        long[] comparisons = new long[RUNS];
        int[] answers = new int[RUNS];

        for (int run = 0; run < RUNS; run++) {
            Metrics metrics = new Metrics();

            long start = System.nanoTime();
            List<Integer> matches =
                    algorithm.search(text, pattern, metrics);
            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;
            comparisons[run] = metrics.getComparisons();
            answers[run] = matches.size();
        }

        double median = median(times);

        for (int run = 0; run < RUNS; run++) {
            results.add(new Result(
                    name,
                    inputType,
                    n,
                    pattern.length(),
                    times[run],
                    0,
                    comparisons[run],
                    answers[run]
            ));
        }

        System.out.printf(
                "Search %-10s %-10s n=%-7d median=%.4f ms%n",
                name, inputType, n, median
        );
    }

    private static double median(double[] values) {
        double[] copy = values.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }
}
