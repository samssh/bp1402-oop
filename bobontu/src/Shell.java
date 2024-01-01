import java.util.StringJoiner;

public class Shell {

    private Directory activeDirectory;

    public Shell() {
        activeDirectory = new Directory("home", null);
    }


    public String listStatus() {
        StringJoiner joiner = new StringJoiner("\n");
        for (Status status : activeDirectory.getContent()) {
            String name = status.getName();
            if (status instanceof Directory)
                name = name + "/";
            joiner.add(name);
        }
        return joiner.toString();
    }

    public String printWorkingDirectory() {
        Directory dummy = activeDirectory;
        StringBuilder location = new StringBuilder();
        while (dummy != null) {
            location.insert(0, "/" + dummy.getName());
            dummy = dummy.getParentDirectory();
        }
        return location.toString();
    }

    public void changeDirectory(String name) throws NoSuchDirectoryException {
        if (name.equals("..")) {
            if (activeDirectory.getParentDirectory() != null) {
                activeDirectory = activeDirectory.getParentDirectory();
            }
            return;
        }
        Directory newActiveDirectory = activeDirectory.getDirectory(name);
        if (newActiveDirectory != null) {
            activeDirectory = newActiveDirectory;
        } else {
            throw new NoSuchDirectoryException(name);
        }
    }

    public void createDirectory(String name) throws CreateDirectoryException {
        Directory dir = activeDirectory.getDirectory(name);
        if (dir != null)
            throw new CreateDirectoryException(name);
        dir = new Directory(name, activeDirectory);
        activeDirectory.addContent(dir);
    }


    public void removeDirectory(String name) throws NoSuchDirectoryException {
        Status directory = null;
        for (Status status : activeDirectory.getContent()) {
            if (status.getName().equals(name) && status instanceof Directory) {
                directory = status;
            }
        }
        if (directory == null) {
            throw new NoSuchDirectoryException(name);
        }
        activeDirectory.getContent().remove(directory);
    }

    public void removeFile(String name) throws NoSuchDirectoryException, RemoveFileException {
        Status file = null;
        for (Status status : activeDirectory.getContent()) {
            if (status.getName().equals(name)) {
                file = status;
            }
        }
        if (file == null) {
            throw new NoSuchDirectoryException(name);
        }
        if (file instanceof Directory) {
            throw new RemoveFileException(name);
        }
        activeDirectory.getContent().remove(file);
    }

    public void createFile(String value) {
        for (Status status : activeDirectory.getContent()) {
            if (status.getName().equals(value)) return;
        }
        File file = new File(value);
        activeDirectory.addContent(file);
    }

    public Directory getActiveDirectory() {
        return activeDirectory;
    }
}
