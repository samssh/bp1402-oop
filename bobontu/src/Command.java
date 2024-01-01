public class Command {
    private final String command;

    private final String arg;

    public Command(String command,String arg) {
        this.command = command;
        this.arg = arg;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
