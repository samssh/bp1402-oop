import java.util.Arrays;

public class IntArray {
    int[] array;

    public IntArray(int n) {
        array = new int[n];
    }

    public int get(int i) {
        if (i < 0 || array.length <= i) throw new ArrayIndexOutOfBoundsException();
        return array[i];
    }

    public void put(int i, int val) {
        if (i < 0 || array.length <= i) throw new ArrayIndexOutOfBoundsException();
        array[i] = val;
    }

    public int length() {
        return array.length;
    }

    public int[] toArray() {
        return Arrays.copyOf(array, array.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntArray intArray = (IntArray) o;

        return Arrays.equals(array, intArray.array);
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
