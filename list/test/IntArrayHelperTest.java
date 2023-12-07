import org.junit.Test;

import static org.junit.Assert.*;

public class IntArrayHelperTest {

    @Test
    public void testSortInPlace() {
        IntArray intArray = new IntArray(5);
        intArray.put(0, 13);
        intArray.put(1, 1);
        intArray.put(2, 132);
        intArray.put(3, 4);
        intArray.put(4, -1);
        IntArrayHelper.sortInPlace(intArray);
        assertArrayEquals(new int[]{-1, 1, 4, 13, 132}, intArray.toArray());

        IntArray intArray1 = new ResizableIntArray(5);
        intArray1.put(0, -100);
        intArray1.put(1, 100000);
        intArray1.put(2, 132);
        intArray1.put(3, 40);
        intArray1.put(4, 444);
        IntArrayHelper.sortInPlace(intArray1);
        assertArrayEquals(new int[]{-100, 40, 132, 444, 100000}, intArray1.toArray());

    }

    @Test
    public void testIsSorted() {
        IntArray intArray = new IntArray(5);
        intArray.put(0, 13);
        intArray.put(1, 1);
        intArray.put(2, 132);
        intArray.put(3, 4);
        intArray.put(4, -1);
        assertFalse(IntArrayHelper.isSorted(intArray));
        intArray.put(3, 13);
        intArray.put(1, 1);
        intArray.put(4, 132);
        intArray.put(2, 4);
        intArray.put(0, -1);
        assertTrue(IntArrayHelper.isSorted(intArray));

        IntArray intArray1 = new ResizableIntArray(5);
        intArray1.put(0, 1);
        intArray1.put(1, 1);
        intArray1.put(2, 1);
        intArray1.put(3, 2);
        intArray1.put(4, 1);
        assertFalse(IntArrayHelper.isSorted(intArray1));

        IntArray intArray2 = new ResizableIntArray(0);
        assertTrue(IntArrayHelper.isSorted(intArray2));

        IntArray intArray3 = new ResizableIntArray(5);
        assertTrue(IntArrayHelper.isSorted(intArray3));

        IntArray intArray4 = new ResizableIntArray(5);
        intArray4.put(0, 2);
        intArray4.put(1, 1);
        intArray4.put(2, 1);
        intArray4.put(3, 2);
        intArray4.put(4, 3);
        assertFalse(IntArrayHelper.isSorted(intArray4));
    }

    @Test
    public void testFindInSorted() {
        IntArray intArray1 = new ResizableIntArray(5);
        intArray1.put(0, 1);
        intArray1.put(1, 1);
        intArray1.put(2, 1);
        intArray1.put(3, 2);
        intArray1.put(4, 3);
        Integer index1 = IntArrayHelper.findInSorted(intArray1, 2);
        assertNotNull(index1);
        assertEquals(3, index1.intValue());

        Integer index2 = IntArrayHelper.findInSorted(intArray1, 3);
        assertNotNull(index2);
        assertEquals(4, index2.intValue());


        IntArray intArray2 = new ResizableIntArray(6);
        intArray2.put(0, 1);
        intArray2.put(1, 2);
        intArray2.put(2, 3);
        intArray2.put(3, 4);
        intArray2.put(4, 5);
        intArray2.put(5, 6);

        Integer index3 = IntArrayHelper.findInSorted(intArray2, 1);
        assertNotNull(index3);
        assertEquals(0, index3.intValue());

        Integer index4 = IntArrayHelper.findInSorted(intArray2, 6);
        assertNotNull(index4);
        assertEquals(5, index4.intValue());

        IntArray intArray3 = new ResizableIntArray(5);
        assertNull(IntArrayHelper.findInSorted(intArray3, 1));
        Integer index5 = IntArrayHelper.findInSorted(intArray3, 0);
        assertNotNull(index5);
        assertTrue(0 <= index5 && index5 < 5);
    }
}