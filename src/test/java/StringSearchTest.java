package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    private final StringSearch.Naive naive = new StringSearch.Naive();
    private final StringSearch.KMP kmp = new StringSearch.KMP();

    private void checkBoth(String text, String pattern,
                           List<Integer> expected) {
        assertEquals(expected,
                naive.search(text, pattern, new Metrics()));

        assertEquals(expected,
                kmp.search(text, pattern, new Metrics()));
    }

    @Test
    void referenceMatches() {
        checkBoth(
                "ABABDABACDABABCABAB",
                "ABABCABAB",
                Collections.singletonList(10)
        );

        checkBoth(
                "AAAAA",
                "AA",
                Arrays.asList(0, 1, 2, 3)
        );

        checkBoth(
                "ABABABA",
                "ABA",
                Arrays.asList(0, 2, 4)
        );
    }

    @Test
    void lpsReferenceValues() {
        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void emptyPatternThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> naive.search("abc", "", new Metrics()));

        assertThrows(IllegalArgumentException.class,
                () -> kmp.search("abc", "", new Metrics()));
    }

    @Test
    void patternLongerThanText() {
        checkBoth("ab", "abcd", Collections.emptyList());
        checkBoth("", "a", Collections.emptyList());
    }

    @Test
    void noMatches() {
        checkBoth("abcdef", "xyz", Collections.emptyList());
    }

    @Test
    void randomAgreement() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            String text = randomString(random, 30);
            String pattern = randomString(random, 6);

            if (pattern.isEmpty()) {
                pattern = "a";
            }

            List<Integer> naiveResult =
                    naive.search(text, pattern, new Metrics());

            List<Integer> kmpResult =
                    kmp.search(text, pattern, new Metrics());

            assertEquals(naiveResult, kmpResult,
                    "Mismatch for text=" + text
                            + ", pattern=" + pattern);
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

