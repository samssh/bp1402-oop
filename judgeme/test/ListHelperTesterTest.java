import org.junit.Test;
import testable.List;
import testable.ListHelper;
import tester.ListHelperTester;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListHelperTesterTest {
    @Test(timeout = 18999)
    public void test1(){
        class TrueListHelper implements ListHelper{

            @Override
            public void sort(List list) {
                ListHelperTesterTest.sort(list);
            }

            @Override
            public int findMax(List list) {
                return ListHelperTesterTest.findMax(list);
            }
        }
        assertTrue(new ListHelperTester().testSort(new TrueListHelper()));
        assertTrue(new ListHelperTester().testFindMax(new TrueListHelper()));

        class FalseListHelper implements ListHelper{

            @Override
            public void sort(List list) {
            }

            @Override
            public int findMax(List list) {
                return 12;
            }
        }
        assertFalse(new ListHelperTester().testSort(new FalseListHelper()));
        assertFalse(new ListHelperTester().testFindMax(new FalseListHelper()));

    }
    @Test(timeout = 18999)
    public void test2(){
        class FalseListHelper implements ListHelper{

            @Override
            public void sort(List list) {
                ListHelperTesterTest.sort(list);
                list.add(listSize(list),0);
            }

            @Override
            public int findMax(List list) {
                if (listSize(list) == 0) {
                    return 0;
                }
                return ListHelperTesterTest.findMax(list);
            }
        }
        assertFalse(new ListHelperTester().testSort(new FalseListHelper()));
        assertFalse(new ListHelperTester().testFindMax(new FalseListHelper()));

        test1();
    }

    private static int findMax(List list){
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < listSize(list) ; i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }
    private static void sort(List list){
        ArrayList<Integer> helperList = new ArrayList();
        for (int i = 0 ; i < listSize(list);i++){
            helperList.add(list.get(i));
        }
        helperList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.compare((int)o1, (int)o2);
            }
        });
        list.emptyList();
        for (int i = 0 ; i < helperList.size() ; i++) {
            list.add(i,helperList.get(i));
        }
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
