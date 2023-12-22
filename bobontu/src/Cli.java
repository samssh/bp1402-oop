public class Cli {

    private final Shell shell;

    private final String computerName;
    private final String user;

    public Cli(String computerName, String user) {
        shell = new Shell();
        this.computerName = computerName;
        this.user = user;
    }

    public String processCommand(Command command) {
        switch (command.getCommand()) {
            case "cd":
                try {
                    shell.changeDirectory(command.getArgs());
                } catch (NoSuchDirectoryException e) {
                    return e.getMessage();
                }
                break;
            case "ls":
                String result = shell.listDirectories();
                if (!result.isEmpty())
                    return result;
                break;
            case "mkdir":
                try {
                    shell.createDirectory(command.getArgs());
                } catch (CreateDirectoryException e) {
                    return e.getMessage();
                }
                break;
            case "touch":
                shell.createFile(command.getArgs());
                break;
            case "pwd":
                String location = shell.printWorkingDirectory();
                return String.format("home/%s/%s", user, location);
            case "rmdir":
                break;
            case "rm":
                break;
            case "sort":
                break;
            case "":
                break;
            default:
                return "CMD: Command not found!";
        }
        return "";
    }


    public String getHeader() {
        if (shell.printWorkingDirectory().isEmpty())
            return String.format("%s@%s:~%s$ ", user, computerName, shell.printWorkingDirectory());
        else
            return String.format("%s@%s:~/%s$ ", user, computerName, shell.printWorkingDirectory());
    }
}
