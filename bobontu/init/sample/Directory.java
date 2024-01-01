package sample;


import java.util.ArrayList;

public class Directory extends Status {
    private Directory parentDirectory;

    private final ArrayList<Status> contents = new ArrayList<>();

    public Directory(String name, Directory parentDirectory) {
        super(name);
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
        return null;
    }
}
