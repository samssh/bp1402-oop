import java.util.ArrayList;

public class Directory extends Base {

    private final Directory parentDirectory;

    private final ArrayList<Base> contents = new ArrayList<>();

    public Directory(String name, Directory parentDirectory) {
        super(name);
        this.parentDirectory = parentDirectory;
    }

    public void addContent(Base ctx) {
        contents.add(ctx);
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public ArrayList<Base> getContent() {
        return contents;
    }

    @Override
    public String getName() {
        return this.name + "/";
    }

    public Directory getDirectory(String name) {
        for (Base base : contents) {
            if (base.getName().equals(name + "/"))
                return (Directory) base;
        }
        return null;
    }

}
