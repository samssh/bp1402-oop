import java.util.Comparator;

public class Base implements Comparator<Base> {

    protected String name;

    public Base(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return "Base";
    }

    @Override
    public int compare(Base o1, Base o2) {
        return 0;
    }
}
