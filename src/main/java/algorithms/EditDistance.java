package algorithms;

import metrics.Metrics;

public class EditDistance {

    public interface Algorithm {
        int distance(String a, String b, Metrics metrics);
    }
    public static class Recursive implements Algorithm {

        @Override
        public int distance(String a, String b, Metrics metrics) {
            return solve(a, b, a.length(), b.length(), metrics);
        }

        private int solve(String a, String b, int i, int j,
                          Metrics metrics) {
            metrics.incrementOperations();

            if (i == 0) return j;
            if (j == 0) return i;

            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                return solve(a, b, i - 1, j - 1, metrics);
            }

            int delete = solve(a, b, i - 1, j, metrics);
            int insert = solve(a, b, i, j - 1, metrics);
            int replace = solve(a, b, i - 1, j - 1, metrics);

            return 1 + Math.min(delete, Math.min(insert, replace));
        }
    }

    public static class Table implements Algorithm {

        @Override
        public int distance(String a, String b, Metrics metrics) {
            int n = a.length();
            int m = b.length();

            int[][] dp = new int[n + 1][m + 1];

            for (int i = 0; i <= n; i++) {
                dp[i][0] = i;
            }

            for (int j = 0; j <= m; j++) {
                dp[0][j] = j;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    metrics.incrementOperations();

                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + Math.min(
                                dp[i - 1][j],
                                Math.min(dp[i][j - 1],
                                        dp[i - 1][j - 1])
                        );
                    }
                }
            }

            return dp[n][m];
        }
    }

    public static class TwoRows implements Algorithm {

        @Override
        public int distance(String a, String b, Metrics metrics) {
            if (b.length() > a.length()) {
                String temp = a;
                a = b;
                b = temp;
            }

            int n = a.length();
            int m = b.length();

            int[] previous = new int[m + 1];
            int[] current = new int[m + 1];

            for (int j = 0; j <= m; j++) {
                previous[j] = j;
            }

            for (int i = 1; i <= n; i++) {
                current[0] = i;

                for (int j = 1; j <= m; j++) {
                    metrics.incrementOperations();

                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        current[j] = previous[j - 1];
                    } else {
                        current[j] = 1 + Math.min(
                                previous[j],
                                Math.min(current[j - 1],
                                        previous[j - 1])
                        );
                    }
                }

                int[] temp = previous;
                previous = current;
                current = temp;
            }

            return previous[m];
        }
    }
}
