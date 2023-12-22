import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your Computer Name: ");
        String computerName = scanner.nextLine();
        System.out.print("Enter Your Username: ");
        String user = scanner.nextLine();
        Cli cli = new Cli(computerName,user);
        while (true) {
            System.out.print(cli.getHeader());
            String input = scanner.nextLine();
            if (input.equals("exit"))
                break;
            String[] commandString = input.split(" ");
            String cmd = input.split(" ")[0];
            String arg = "";
            if (input.split(" ").length == 1) {
                arg = input.split(" ")[0];
            }
            Command command = new Command(cmd,arg);
            String result = cli.processCommand(command);
            System.out.println(result);
        }
    }
}