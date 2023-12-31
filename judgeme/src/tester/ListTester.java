package tester;

import testable.List;

public class ListTester {
    public boolean testList(List list) {
        boolean a = testAddAndGet(list);
        boolean b = testException(list);
        boolean c = testEmptyList(list);
        return a && b && c;
    }

    public boolean testAddAndGet(List list) {
        try {
            list.emptyList();
            list.get(0);
        } catch (Exception e) {
            list.add(0, -6);
            for (int i = 0; i < 50; i++) {
                list.add(0, i * i - 3);
            }
            for (int i = 0; i < 50; i++) {
                if (list.get((49 - i)) != i * i - 3) {
                    return false;
                }
            }
            if (list.get(50) != -6) {
                return false;
            }
            list.add(5, 55);
            return list.get(5) == 55 && list.get(51) == -6 && list.get(0) == 49 * 49 - 3;
        }
        return false;
    }

    public boolean testException(List list) {
        for (int i = -1; i > -10; i--) {
            try {
                list.add(i, 3);
                return false;
            } catch (Exception exception) {
                try {
                    list.get(i);
                    return false;
                } catch (Exception ignored) {
                }
            }
        }
        return true;
    }

    public boolean testEmptyList(List list) {
        list.emptyList();
        try {
            list.get(0);
        } catch (Exception exception) {
            return true;
        }
        return false;
    }
}
