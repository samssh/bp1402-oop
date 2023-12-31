package tester;

import testable.Stack;

public class StackTester {
    public boolean testStack(Stack stack) {
        boolean a = testPopAndPush(stack);
        boolean b = testException(stack);
        return a & b;

    }
    public boolean testPopAndPush(Stack stack){
        int[] input = {1,2,3,4,5,6};
        try {
            for (int i = 0; i < input.length; i++) {
                stack.push(input[i]);
            }
        }catch (Exception e) {
            return false;
        }
        int j = 6;
        for (int i = 0 ; i < input.length; i++) {
            if (stack.pop() != j) {
                return false;
            }
            j--;
        }
        return true;
    }
    public boolean testException(Stack stack){
        try {
            stack.pop();
        }catch (Exception e) {
            return true;
        }
        return false;
    }
}
