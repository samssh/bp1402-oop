import org.junit.Test;
import testable.Stack;
import tester.StackTester;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StackTesterTest {
    @Test(timeout = 4999)
    public void test5(){
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
        class WrongStack implements Stack{
            final ArrayList<Integer> elements = new ArrayList<>();

            @Override
            public void push(int i) {
                elements.add(i);
            }

            @Override
            public int pop() {
                return elements.get(elements.size() - 1);
            }

        }
        assertFalse(new StackTester().testStack(new WrongStack()));
        class FalseStack implements Stack{
            final ArrayList<Integer> elements = new ArrayList<>();

            @Override
            public void push(int i) {
                elements.add(0,i);
            }

            @Override
            public int pop(){
                int output = elements.get(elements.size() - 1);
                elements.remove(elements.size() - 1);
                return output;
            }
        }
        assertFalse(new StackTester().testStack(new FalseStack()));
    }
    //this test checking if code handled exceptions
    @Test(timeout = 4999)
    public void test6(){
        class ExceptionStack implements Stack {
            final ArrayList<Integer> elements = new ArrayList<>();

            @Override
            public void push(int i) {
                elements.add( i);
            }

            @Override
            public int pop() {
                int output = 0;
                try {
                    output = elements.get(elements.size() - 1);
                    elements.remove(elements.size() - 1);
                } catch (Exception ignored) {
                }
                return output;
            }
        }
        assertFalse(new StackTester().testStack(new ExceptionStack()));
        test5();
    }
}
