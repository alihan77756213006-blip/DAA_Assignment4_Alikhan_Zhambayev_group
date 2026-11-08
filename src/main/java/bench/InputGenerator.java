package bench;

import java.util.Random;

public class InputGenerator {

    private static final long SEED = 42L;

    public static String randomString(int length, long seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(4)));
        }

        return sb.toString();
    }

    public static String randomString(int length) {
        return randomString(length, SEED);
    }

    public static String[] editRandom(int n) {
        return new String[]{
                randomString(n, SEED + n),
                randomString(n, SEED + n + 10000)
        };
    }


    public static String[] editDifferent(int n) {
        return new String[]{
                "a".repeat(n),
                "b".repeat(n)
        };
    }

    public static String[] searchRandom(int n) {
        String text = randomString(n, SEED + n);
        String pattern = randomString(
                Math.max(1, Math.min(16, n / 4)),
                SEED + n + 10000
        );

        return new String[]{text, pattern};
    }


    public static String[] searchWorstCase(int n) {
        int m = Math.max(2, Math.min(32, n / 4));

        String text = "a".repeat(n);
        String pattern = "a".repeat(m - 1) + "b";

        return new String[]{text, pattern};
    }
}
