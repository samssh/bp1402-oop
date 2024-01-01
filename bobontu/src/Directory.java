import java.util.ArrayList;

public class Directory extends Status {

    private final Directory parentDirectory;

    private final ArrayList<Status> contents = new ArrayList<>();

    public Directory(String name, Directory parentDirectory) {
        super(name);
        this.parentDirectory = parentDirectory;
    }

    public void addContent(Status ctx) {
        contents.add(ctx);
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public ArrayList<Status> getContent() {
        return contents;
    }

    public Directory getDirectory(String name) {
        for (Status status : contents) {
            if (status.getName().equals(name) && status instanceof Directory)
                return (Directory) status;
        }
        return null;
    }

}
