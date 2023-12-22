public class Shell {

    private Directory activeDirectory;

    public Shell() {
        activeDirectory = new Directory("home", null);
    }


    public String listDirectories() {
        String result = "";
        for (Base ctx : activeDirectory.getContent()) {
            if (result.isEmpty())
                result = ctx.getName();
            else
                result = result + " " + ctx.getName();
        }
        return result;
    }

    public String printWorkingDirectory() {
        Directory dummy = activeDirectory;
        StringBuilder location = new StringBuilder();
        if (!activeDirectory.getName().equals("home/"))
            location = new StringBuilder(activeDirectory.getName());
        while (dummy.getParentDirectory() != null && !dummy.getParentDirectory().getName().equals("home/")) {
            location.insert(0, "/" + dummy.getParentDirectory().getName());
            dummy = dummy.getParentDirectory();
        }
        return location.toString();
    }

    public void changeDirectory(String name) throws NoSuchDirectoryException {
        if (name.equals("..")) {
            if (activeDirectory.getParentDirectory() != null) {
                activeDirectory = activeDirectory.getParentDirectory();
            }
        }
        Directory newActiveDirectory = activeDirectory.getDirectory(name);
        if (newActiveDirectory != null) {
            activeDirectory = newActiveDirectory;
        } else {
            throw new NoSuchDirectoryException("CMD: No Such Directory");
        }
    }

    public void createDirectory(String name) throws CreateDirectoryException {
        Directory dir = activeDirectory.getDirectory(name);
        if (dir != null)
            throw new CreateDirectoryException(String.format("mkdir: cannot create directory ‘%s’: File exists", name));
        dir = new Directory(name, activeDirectory);
        activeDirectory.addContent(dir);
    }


    // Usage: rm -rf [Folder_Name]
    public boolean removeDirectory(String name) {
        return false;
    }

    public void sortDirectories(String type) {

    }

    public void createFile(String value) {
        File file = new File(value);
        activeDirectory.addContent(file);
    }
}
