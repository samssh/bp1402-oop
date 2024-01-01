public class NoSuchDirectoryException extends FileException {
    public NoSuchDirectoryException(String name) {
        super("'" + name + "': No such file or directory");
    }
}
