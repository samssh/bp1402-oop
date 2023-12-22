import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CliTest {
    private final String user = "test";
    private final String computerName = "Test";

    private final String fileName = "File";

    private final String directoryName = "Dir";

    private final Command command = new Command(null, null);

    @Test
    public void testRandomCommand() {
        Cli cli = new Cli(computerName, user);
        command.setCommand("random");
        Command command = new Command("random", "");
        String result = cli.processCommand(command);
        assertEquals(result, "CMD: Command not found!");
    }

    @Test
    public void testHeader() {
        Cli cli = new Cli(computerName, user);
        String res = cli.getHeader();
        assertEquals(res, String.format("%s@%s:~$ ", user, computerName));
        command.setCommand("mkdir");
        command.setArg(directoryName);
        cli.processCommand(command);
        command.setCommand("cd");
        command.setArg(directoryName);
        cli.processCommand(command);
        res = cli.getHeader();
        assertEquals(res, String.format("%s@%s:~/%s/$ ", user, computerName, directoryName));
        command.setCommand("cd");
        command.setArg("..");
        cli.processCommand(command);
        res = cli.getHeader();
        assertEquals(res, String.format("%s@%s:~$ ", user, computerName));
    }

    @Test
    public void testChangeDirectory() {
        Cli cli = new Cli(computerName, user);
        command.setCommand("pwd");
        String res = cli.processCommand(command);
        command.setCommand("mkdir");
        command.setArg(directoryName);
        cli.processCommand(command);
        command.setCommand("cd");
        command.setArg(directoryName);
        cli.processCommand(command);
        command.setCommand("pwd");
        String second = cli.processCommand(command);
        assertEquals(second, res + directoryName + "/");
    }

    @Test
    public void testMakeDirectory() {
        Cli cli = new Cli(computerName, user);
        command.setCommand("mkdir");
        command.setArg(directoryName);
        String res = cli.processCommand(command);
        assertEquals(res, "");
        command.setCommand("mkdir");
        command.setArg(directoryName);
        res = cli.processCommand(command);
        assertEquals(res, String.format("mkdir: cannot create directory ‘%s’: File exists", directoryName));
    }

    @Test
    public void testTouchFile() {
        for (int j = 0; j < 2; j++) {
            Cli cli = new Cli(computerName, user);
            String fileName = "file";
            String folderName = "dir";
            String res = "";
            String currentRes = "";
            for (int i = 0; i < 5; i++) {
                if (i % 2 == 0) {
                    command.setCommand("mkdir");
                    command.setArg(folderName + i);
                    cli.processCommand(command);
                    command.setCommand("ls");
                    res = cli.processCommand(command);
                    if (!currentRes.isEmpty()) currentRes = currentRes + " " + folderName + i + "/";
                    else currentRes = currentRes + folderName + i + "/";
                } else {
                    command.setCommand("touch");
                    command.setArg(fileName + i);
                    cli.processCommand(command);
                    command.setCommand("ls");
                    res = cli.processCommand(command);
                    if (!currentRes.isEmpty()) currentRes = currentRes + " " + fileName + i;
                    else currentRes = currentRes + fileName + i;
                }
                assertEquals(res, currentRes);
            }
        }
    }
}
