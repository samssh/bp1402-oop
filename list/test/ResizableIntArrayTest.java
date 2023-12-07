import org.junit.Test;

import static org.junit.Assert.*;

public class ResizableIntArrayTest {

    @Test
    public void testAppend() {
        ResizableIntArray intArray = new ResizableIntArray(1);
        intArray.put(0, 45);
        intArray.append(34);
        intArray.append(23);
        assertEquals(3, intArray.length());
        assertArrayEquals(new int[]{45, 34, 23}, intArray.toArray());
    }

    @Test
    public void testChangeSize() {
        ResizableIntArray intArray = new ResizableIntArray(3);
        intArray.changeSize(4);
        assertEquals(7, intArray.length());
        intArray.changeSize(-7);
        assertEquals(0, intArray.length());
        intArray.changeSize(4);
        assertEquals(4, intArray.length());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> intArray.changeSize(-5));
    }

    @Test
    public void testPop() {
        ResizableIntArray intArray = new ResizableIntArray(3);
        intArray.put(0, 45);
        intArray.put(1, 34);
        intArray.put(2, 23);
        intArray.pop();
        assertEquals(2, intArray.length());
        assertArrayEquals(new int[]{45, 34}, intArray.toArray());
        intArray.pop();
        assertEquals(1, intArray.length());
        assertArrayEquals(new int[]{45}, intArray.toArray());
        intArray.pop();
        assertEquals(0, intArray.length());
        assertArrayEquals(new int[]{}, intArray.toArray());
        assertThrows(ArrayIndexOutOfBoundsException.class, intArray::pop);
    }

    public static <T extends Throwable> void assertThrows(Class<T> expectedThrowable,
                                                          Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable actualThrown) {
            if (expectedThrowable.isInstance(actualThrown)) {
                return;
            } else {
                String mismatchMessage = "unexpected exception type thrown;";
                throw new AssertionError(mismatchMessage, actualThrown);
            }
        }
        String notThrownMessage = String.format("expected %s to be thrown, but nothing was thrown",
                formatClass(expectedThrowable));
        throw new AssertionError(notThrownMessage);
    }

    private static String formatClass(Class<?> value) {
        String className = value.getCanonicalName();
        return className == null ? value.getName() : className;
    }

    @Test(timeout = 3000)
    public void testExtend() {
        int[] a = new int[10000000];
        a[7] = 123;
        ResizableIntArray ria = new ResizableIntArray(5);
        assertEquals(5, ria.length());
        ria.extend(a);
        assertEquals(10000005, ria.length());
        assertEquals(123, ria.get(12));
    }
}