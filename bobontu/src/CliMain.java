public class CliMain {
    public static void main(String[] args) {
        Cli cli = new Cli("test", "user");
        Command command0 = new Command("mkdir","test");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command0));

        Command command1 = new Command("mkdir","test");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command1));

        Command command2 = new Command("cd","test");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command2));

        Command command3 = new Command("mkdir","test");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command3));

        Command command4 = new Command("pwd","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command4));

        Command command5 = new Command("ls","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command5));

        Command command6 = new Command("mkdir","test2");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command6));

        Command command7 = new Command("ls","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command7));

        Command command8 = new Command("rm","test1");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command8));

        Command command9 = new Command("rmdir","test2");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command9));

        Command command10 = new Command("touch","test1");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command10));

        Command command11 = new Command("ls","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command11));

        Command command12 = new Command("rm","test");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command12));

        Command command13 = new Command("rm","test1");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command13));

        Command command14 = new Command("ls","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command14));

        Command command15 = new Command("cd","..");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command15));

        Command command16 = new Command("pwd","");
        System.out.println(cli.getHeader());
        System.out.println(cli.processCommand(command16));
    }
}