public class Command {
    private String command;

    private String arg;

    public Command(String command,String arg) {
        this.command = command;
        this.arg = arg;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getArgs() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
