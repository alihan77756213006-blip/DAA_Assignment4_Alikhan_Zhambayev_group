package metrics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class CsvWriter {

    public static void write(String filePath, List<Result> results)
            throws IOException {

        Path path = Path.of(filePath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path, StandardCharsets.UTF_8)) {

            writer.write(
                    "algorithm,input,n,m,time_ms,operations,comparisons,answer"
            );
            writer.newLine();

            for (Result result : results) {
                String line = String.format(
                        Locale.US,
                        "%s,%s,%d,%d,%.6f,%d,%d,%d",
                        result.getAlgorithm(),
                        result.getInput(),
                        result.getN(),
                        result.getM(),
                        result.getTimeMs(),
                        result.getOperations(),
                        result.getComparisons(),
                        result.getAnswer()
                );

                writer.write(line);
                writer.newLine();
            }
        }
    }
}
