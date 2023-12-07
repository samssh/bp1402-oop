import org.junit.Test;

import static org.junit.Assert.*;

public class IntArrayTest {

    @Test
    public void testPutAndGet() {
        IntArray a = new IntArray(3);
        a.put(0, 1);
        a.put(1, 13);
        a.put(2, 14);
        assertEquals(1, a.get(0));
        assertEquals(13, a.get(1));
        assertEquals(14, a.get(2));
        a.put(1, 9);
        assertEquals(9, a.get(1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.get(3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.get(4));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.put(-1, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.put(3, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.put(4, 0));
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


    @Test
    public void testLength() {
        IntArray a = new IntArray(6);
        assertEquals(6, a.length());
    }

    @Test
    public void testToArray() {
        IntArray a = new IntArray(6);
        a.put(0, 1);
        a.put(1, 13);
        a.put(2, 14);
        a.put(3, 15);
        a.put(4, 1);
        a.put(5, 20);
        int[] aa = a.toArray();
        assertArrayEquals(new int[]{1, 13, 14, 15, 1, 20}, aa);
        aa[0] = 2;
        assertEquals(1, a.get(0));
    }

    @Test
    public void testEquals() {
        IntArray a = new IntArray(6);
        a.put(0, 1);
        a.put(1, 13);
        a.put(2, 14);
        a.put(3, 15);
        a.put(4, 1);
        a.put(5, 20);

        IntArray aa = new IntArray(6);
        aa.put(0, 1);
        aa.put(1, 13);
        aa.put(2, 14);
        aa.put(3, 15);
        aa.put(4, 1);
        aa.put(5, 20);

        assertEquals("wrong equals", a, aa);
        assertEquals("wrong equals", aa, a);
        assertEquals("wrong equals", a, a);
        assertEquals("wrong equals", aa, aa);
        aa.put(0, 12);
        assertNotEquals("wrong equals", a, aa);
        assertNotEquals("wrong equals", aa, a);
    }

    @Test()
    public void testToString() {
        IntArray a = new IntArray(6);
        a.put(0, 1);
        a.put(1, 13);
        a.put(2, 14);
        a.put(3, 15);
        a.put(4, 1);
        a.put(5, 20);
        assertEquals("wrong to string", "[1, 13, 14, 15, 1, 20]", a.toString());
        IntArray a1 = new IntArray(0);
        assertEquals("wrong to string", "[]", a1.toString());
    }
}