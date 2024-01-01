public class CreateDirectoryException extends FileException {
    public CreateDirectoryException(String name) {
        super("'" + name + "': File exists");
    }
}
