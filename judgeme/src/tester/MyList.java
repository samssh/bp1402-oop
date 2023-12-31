package tester;

import testable.List;

import java.util.ArrayList;

public class MyList implements List {
    final ArrayList<Integer> elements = new ArrayList<>();
    @Override
    public void add(int i, int v) {
        elements.add(i,v);
    }

    @Override
    public int get(int i) {
        return elements.get(i);
    }

    @Override
    public void emptyList() {
        elements.clear();
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
