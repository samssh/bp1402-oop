import org.junit.Test;
import testable.List;
import tester.ListTester;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListTesterTest {

    @Test(timeout = 4999)
    public void test3() {
        class CorrectImpl implements List {
            ArrayList<Integer> array = new ArrayList<>();
            @Override
            public void add(int i,int v) {
                array.add(i,v);
            }

            @Override
            public int get(int i) {
                return array.get(i);
            }

            @Override
            public void emptyList() {
                array.clear();
            }
        }
        assertTrue(new ListTester().testList(new CorrectImpl()));
        class Wrong implements List {
            ArrayList<Integer> array = new ArrayList<>();
            @Override
            public void add(int i,int v) {
                array.add(i,v);
            }

            @Override
            public int get(int i) {
                return array.get(i);
            }

            @Override
            public void emptyList() {
            }
        }
        assertFalse(new ListTester().testList(new Wrong()));
        class WrongImpl implements List {
            ArrayList<Integer> array = new ArrayList<>();
                @Override
                public void add(int i,int v) {
                    array.add(i,v);
                }

                @Override
                public int get(int i) {
                    if (i >= 5){
                        return 0;
                    }
                    return array.get(i);
                }

            @Override
            public void emptyList() {
                array.clear();
            }
        }
        assertFalse(new ListTester().testList(new WrongImpl()));
    }

    @Test(timeout = 4999)
    public void test4(){
        class ExceptionList implements List {
            ArrayList<Integer> array = new ArrayList();
            @Override
            public void add(int i,int v) {
                try {
                    array.add(i, v);
                }catch (Exception e){}
            }

            @Override
            public int get(int i) {
                try {
                    return array.get(i);
                }catch (Exception e) {
                    return 0;
                }

            }

            @Override
            public void emptyList() {
                array.clear();
            }
        }
        assertFalse(new ListTester().testList(new ExceptionList()));
        test3();
    }
}
