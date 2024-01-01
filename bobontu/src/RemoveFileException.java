public class RemoveFileException extends FileException {
    public RemoveFileException(String name) {
        super("'" + name + "/': Is a directory");
    }
}
