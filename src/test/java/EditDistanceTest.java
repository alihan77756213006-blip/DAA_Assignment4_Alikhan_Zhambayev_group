package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private final EditDistance.Algorithm[] algorithms = {
            new EditDistance.Recursive(),
            new EditDistance.Table(),
            new EditDistance.TwoRows()
    };

    private void checkAll(String a, String b, int expected) {
        for (EditDistance.Algorithm algorithm : algorithms) {
            assertEquals(expected,
                    algorithm.distance(a, b, new Metrics()),
                    algorithm.getClass().getSimpleName());
        }
    }

    @Test
    void referenceValues() {
        checkAll("kitten", "sitting", 3);
        checkAll("flaw", "lawn", 2);
        checkAll("intention", "execution", 5);
    }

    @Test
    void emptyStrings() {
        checkAll("", "", 0);
        checkAll("", "abc", 3);
        checkAll("abc", "", 3);
        checkAll("abc", "abc", 0);
    }

    @Test
    void simpleCases() {
        checkAll("a", "b", 1);
        checkAll("abc", "abd", 1);
        checkAll("abc", "ab", 1);
        checkAll("ab", "abc", 1);
    }

    @Test
    void randomAgreement() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            String a = randomString(random, 5);
            String b = randomString(random, 5);

            int recursive = algorithms[0].distance(
                    a, b, new Metrics());

            int table = algorithms[1].distance(
                    a, b, new Metrics());

            int twoRows = algorithms[2].distance(
                    a, b, new Metrics());

            assertEquals(recursive, table);
            assertEquals(table, twoRows);
        }
    }

    private String randomString(Random random, int maxLength) {
        int length = random.nextInt(maxLength + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(3)));
        }

        return sb.toString();
    }
}

