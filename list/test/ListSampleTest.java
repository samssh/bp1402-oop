import org.junit.Test;

import static org.junit.Assert.*;

public class ListSampleTest {
    @Test
    public void test1() {
        IntArray b = new IntArray(5);
        b.put(2, 7);
        int[] a = b.toArray();
        a[2] = 4; // it won't affect on b
        // b.get(2) must be 7
        assertEquals(7, b.get(2));
    }

    @Test
    public void test2() {
        IntArray arr = new IntArray(3);
        arr.put(0, 1);
        arr.put(1, 2);
        arr.put(2, 30);
        assertEquals("[1, 2, 30]", arr.toString());
    }

    @Test
    public void test3() {
        ResizableIntArray ria = new ResizableIntArray(5);
        // ria.length() is 5
        assertEquals(5, ria.length());
        ria.append(25);
        // now ria.length() is 6
        assertEquals(6, ria.length());
        // ria.get(ria.length()-1) is 25
        assertEquals(25, ria.get(ria.length() - 1));
    }

    @Test
    public void test4() {
        ResizableIntArray ria = new ResizableIntArray(5);
        // ria.length() is 5
        assertEquals(5, ria.length());
        ria.pop();
        // now ria.length() is 4
        assertEquals(4, ria.length());
    }

    @Test(timeout = 1000)
    public void test5() {
        int[] a = new int[10000000];
        a[7] = 123;
        ResizableIntArray ria = new ResizableIntArray(3);
        // ria.length() is 3
        assertEquals(3, ria.length());
        ria.extend(a); // it should take only few milliseconds
        // ria.length() is 10000003
        assertEquals(10000003, ria.length());
        // ria.get(10) is 123
        assertEquals(123, ria.get(10));
    }

    @Test
    public void test6() {
        ResizableIntArray ria = new ResizableIntArray(3);
        // ria.length() is 3
        assertEquals(3, ria.length());
        ria.changeSize(100);
        // ria.length() is 103
        assertEquals(103, ria.length());
        ria.changeSize(-10);
        // ria.length() is 93
        assertEquals(93, ria.length());
    }

    @Test
    public void test7() {
        IntArray a = new IntArray(3);
        a.put(0, 3);
        a.put(1, 2);
        a.put(2, 1);
        IntArrayHelper.sortInPlace(a);
        // a.get(0) is 1
        assertEquals(1, a.get(0));
        assertEquals(2, a.get(1));
        assertEquals(3, a.get(2));
    }

    @Test(timeout = 1000)
    public void test8() {
        int n = 10000000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        ResizableIntArray ria = new ResizableIntArray(0);
        ria.extend(a);
        // IntArrayHelper.isSorted(ria) is true
        assertTrue(IntArrayHelper.isSorted(ria));
        for (int i = 0; i < 10000; i++) {
            Integer index = IntArrayHelper.findInSorted(ria, i * i);
            if (i * i < n && i != 0) {
                assertNotNull(index);
                assertEquals(i * i - 1, index.intValue());
            } else {
                assertNull(index);
            }
        }
    }
}
