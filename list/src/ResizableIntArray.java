import java.util.Arrays;

public class ResizableIntArray extends IntArray {
    public ResizableIntArray(int n) {
        super(n);
    }

    public void append(int val) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = val;
        this.array = newArray;
    }

    public void changeSize(int n) {
        if (array.length + n < 0) throw new ArrayIndexOutOfBoundsException();
        this.array = Arrays.copyOf(array, array.length + n);
    }

    public void pop() {
        if (this.array.length == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int[] newArray = new int[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, array.length - 1);
        this.array = newArray;
    }

    public void extend(int[] extend) {
        int[] newArray = new int[array.length + extend.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.arraycopy(extend, 0, newArray, array.length, extend.length);
        this.array = newArray;
    }
}
