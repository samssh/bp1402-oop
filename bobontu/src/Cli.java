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
                    shell.changeDirectory(command.getArg());
                    return "";
                } catch (FileException e) {
                    return "cd: " + e.getMessage();
                }
            case "ls":
                return shell.listStatus();
            case "mkdir":
                try {
                    shell.createDirectory(command.getArg());
                    return "";
                } catch (FileException e) {
                    return "mkdir: cannot create directory " + e.getMessage();
                }
            case "touch":
                shell.createFile(command.getArg());
                return "";
            case "pwd":
                return shell.printWorkingDirectory();
            case "rmdir":
                try {
                    shell.removeDirectory(command.getArg());
                    return "";
                } catch (FileException e) {
                    return "rmdir: failed to remove " + e.getMessage();
                }
            case "rm":
                try {
                    shell.removeFile(command.getArg());
                    return "";
                } catch (FileException e) {
                    return "rm: cannot remove " + e.getMessage();
                }
            default:
                return command.getCommand() + ": command not found";
        }
    }


    public String getHeader() {
        Directory dummy = shell.getActiveDirectory();
        StringBuilder location = new StringBuilder();
        while (dummy.getParentDirectory() != null) {
            location.insert(0, "/" + dummy.getName());
            dummy = dummy.getParentDirectory();
        }
        return String.format("%s@%s:~%s$", user, computerName, location);
    }
}
