package algorithms;

import metrics.Metrics;
import java.util.ArrayList;
import java.util.List;

public class StringSearch {

    public interface Algorithm {
        List<Integer> search(String text, String pattern, Metrics metrics);
    }

    public static class Naive implements Algorithm {

        @Override
        public List<Integer> search(String text, String pattern,
                                    Metrics metrics) {
            if (pattern.isEmpty()) {
                throw new IllegalArgumentException(
                        "Pattern cannot be empty");
            }

            List<Integer> result = new ArrayList<>();

            int n = text.length();
            int m = pattern.length();

            if (m > n) return result;

            for (int i = 0; i <= n - m; i++) {
                int j = 0;

                while (j < m) {
                    metrics.incrementComparisons();

                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                    j++;
                }

                if (j == m) {
                    result.add(i);
                }
            }

            return result;
        }
    }

    public static class KMP implements Algorithm {

        public int[] lps(String pattern) {
            int m = pattern.length();
            int[] lps = new int[m];

            int len = 0;
            int i = 1;

            while (i < m) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }

            return lps;
        }

        @Override
        public List<Integer> search(String text, String pattern,
                                    Metrics metrics) {
            if (pattern.isEmpty()) {
                throw new IllegalArgumentException(
                        "Pattern cannot be empty");
            }

            List<Integer> result = new ArrayList<>();

            int n = text.length();
            int m = pattern.length();

            if (m > n) return result;

            int[] lps = lps(pattern);

            int i = 0;
            int j = 0;

            while (i < n) {
                metrics.incrementComparisons();

                if (text.charAt(i) == pattern.charAt(j)) {
                    i++;
                    j++;

                    if (j == m) {
                        result.add(i - j);
                        j = lps[j - 1];
                    }
                } else if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }

            return result;
        }
    }
}

