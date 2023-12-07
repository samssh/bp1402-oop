import java.util.Arrays;

public class IntArrayHelper {
    private IntArrayHelper() {

    }

    static void sortInPlace(IntArray a) {
        Arrays.sort(a.array);
    }

    static boolean isSorted(IntArray a) {
        for (int i = 0; i < a.length() - 1; i++) {
            if (a.get(i) > a.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    static Integer findInSorted(IntArray a, int val) {
        int r = 0, l = a.length() - 1;
        while (r <= l) {
            int x = (r + l) >>> 1;

            if (a.get(x) < val)
                r = x + 1;
            else if (a.get(x) > val)
                l = x - 1;
            else
                return x;
        }
        return null;
    }

}
