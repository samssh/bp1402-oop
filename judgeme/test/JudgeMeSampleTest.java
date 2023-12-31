import org.junit.Test;
import testable.List;
import testable.ListHelper;
import testable.Stack;
import tester.ListHelperTester;
import tester.ListTester;
import tester.StackTester;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JudgeMeSampleTest {
    @Test(timeout = 4999)
    public void listTesterTest(){
        class MyList implements List{
            int[] ints = new int[0];
            @Override
            public void add(int i, int v) {
                int[] newInts = new int[ints.length + 1];
                if (i >= 0) System.arraycopy(ints, 0, newInts, 0, i);
                newInts[i] = v;
                if (ints.length + 1 - (i + 1) >= 0)
                    System.arraycopy(ints, i + 1 - 1, newInts, i + 1, ints.length + 1 - (i + 1));
                ints = newInts;
            }
            @Override
            public int get(int i) {
                return ints[i];
            }
            @Override
            public void emptyList() {
                ints = new int[0];
            }
        }
        assertTrue(new ListTester().testList(new MyList()));

        class MyList2 implements List{
            @Override
            public void add(int i, int v) {
            }
            @Override
            public int get(int i) {
                return 0;
            }
            @Override
            public void emptyList() {
            }
        }

        assertFalse(new ListTester().testList(new MyList2()));

    }
    @Test(timeout = 4999)
    public void ListHelperTesterTest(){
        class TrueListHelper implements ListHelper {

            @Override
            public void sort(List list) {
            }

            @Override
            public int findMax(List list) {
                int max = Integer.MIN_VALUE;
                for (int i = 0; i < listSize(list) ; i++) {
                    if (list.get(i) > max) {
                        max = list.get(i);
                    }
                }
                return max;
            }
        }
        assertFalse(new ListHelperTester().testSort(new TrueListHelper()));
        assertTrue(new ListHelperTester().testFindMax(new TrueListHelper()));

    }
    @Test(timeout = 4999)
    public void stackTesterTest(){
        class FalseStack implements Stack{
            @Override
            public void push(int i) {

            }

            @Override
            public int pop() {
                return 0;
            }
        }
        assertFalse(new StackTester().testStack(new FalseStack()));
        class CorrectStack implements Stack{
            final ArrayList<Integer> elements = new ArrayList<>();

            @Override
            public void push(int i) {
                elements.add(i);
            }

            @Override
            public int pop() {
                int output = elements.get(elements.size() - 1);
                elements.remove(elements.size() - 1);
                return output;
            }
        }
        assertTrue(new StackTester().testStack(new CorrectStack()));
    }

    private static int listSize(List list){
        boolean hasNext = true;
        int length = 0;
        while (hasNext) {
            length++;
            try {
                list.get(length-1);
            }catch (Exception e) {
                length--;
                hasNext = false;
            }
        }
        return length;
    }
}
