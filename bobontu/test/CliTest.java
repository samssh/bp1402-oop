import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CliTest {
    private final String user = "test";
    private final String computerName = "Test";

    private final String directoryName = "Dir";

    @Test
    public void testRandomCommand() {
        Cli cli = new Cli(computerName, user);
        Command command = new Command("random", "");
        String result = cli.processCommand(command);
        assertEquals("random: command not found", result);
    }

    @Test
    public void testHeader() {
        Cli cli = new Cli(computerName, user);
        String res = cli.getHeader();
        assertEquals(String.format("%s@%s:~$", user, computerName), res);
        Command command1 = new Command("mkdir", directoryName);
        cli.processCommand(command1);
        Command command2 = new Command("cd", directoryName);
        cli.processCommand(command2);
        res = cli.getHeader();
        assertEquals(String.format("%s@%s:~/%s$", user, computerName, directoryName), res);
        Command command3 = new Command("cd", "..");
        cli.processCommand(command3);
        res = cli.getHeader();
        assertEquals(String.format("%s@%s:~$", user, computerName), res);
    }

    @Test
    public void testChangeDirectory() {
        Cli cli = new Cli(computerName, user);
        Command command1 = new Command("pwd", "");
        assertEquals("/home", cli.processCommand(command1));
        Command command2 = new Command("mkdir", directoryName);
        cli.processCommand(command2);
        Command command3 = new Command("cd", directoryName);
        cli.processCommand(command3);
        assertEquals("/home/Dir", cli.processCommand(command1));
        Command command4 = new Command("cd", "..");
        cli.processCommand(command4);
        assertEquals("/home", cli.processCommand(command1));
        Command command5 = new Command("cd", "lknvfd");
        assertEquals("cd: 'lknvfd': No such file or directory", cli.processCommand(command5));
    }

    @Test
    public void testMakeDirectory() {
        Cli cli = new Cli(computerName, user);
        Command command1 = new Command("mkdir", directoryName);
        String res = cli.processCommand(command1);
        assertEquals("", res);
        res = cli.processCommand(command1);
        assertEquals(String.format("mkdir: cannot create directory '%s': File exists", directoryName), res);
    }

    @Test
    public void testTouchMkdir() {
        for (int j = 0; j < 2; j++) {
            Cli cli = new Cli(computerName, user);
            String fileName = "file";
            String folderName = "dir";
            String res;
            String currentRes = "";
            for (int i = 0; i < 5; i++) {
                if (i % 2 == 0) {
                    Command command1 = new Command("mkdir", folderName + i);
                    cli.processCommand(command1);
                    Command command2 = new Command("ls", "");
                    res = cli.processCommand(command2);
                    if (!currentRes.isEmpty()) currentRes = currentRes + "\n" + folderName + i + "/";
                    else currentRes = currentRes + folderName + i + "/";
                } else {
                    Command command1 = new Command("touch", fileName + i);
                    cli.processCommand(command1);
                    Command command2 = new Command("ls", "");
                    res = cli.processCommand(command2);
                    if (!currentRes.isEmpty()) currentRes = currentRes + "\n" + fileName + i;
                    else currentRes = currentRes + fileName + i;
                }
                assertEquals(currentRes, res);
            }
        }
    }

    @Test
    public void testSample() {
        Cli cli = new Cli("test", "user");
        Command command0 = new Command("mkdir", "test");
        assertEquals("user@test:~$", cli.getHeader());
        assertEquals("", cli.processCommand(command0));

        Command command1 = new Command("mkdir", "test");
        assertEquals("user@test:~$", cli.getHeader());
        assertEquals("mkdir: cannot create directory 'test': File exists", cli.processCommand(command1));

        Command command2 = new Command("cd", "test");
        assertEquals("user@test:~$", cli.getHeader());
        assertEquals("", cli.processCommand(command2));

        Command command3 = new Command("mkdir", "test");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command3));

        Command command4 = new Command("pwd", "");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("/home/test", cli.processCommand(command4));

        Command command5 = new Command("ls", "");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("test/", cli.processCommand(command5));

        Command command6 = new Command("mkdir", "test2");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command6));

        Command command7 = new Command("ls", "");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("test/\ntest2/", cli.processCommand(command7));

        Command command8 = new Command("rm", "test1");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("rm: cannot remove 'test1': No such file or directory", cli.processCommand(command8));

        Command command9 = new Command("rmdir", "test2");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command9));

        Command command10 = new Command("touch", "test1");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command10));

        Command command91 = new Command("rmdir", "test1");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("rmdir: failed to remove 'test1': No such file or directory", cli.processCommand(command91));


        Command command11 = new Command("ls", "");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("test/\ntest1", cli.processCommand(command11));

        Command command12 = new Command("rm", "test");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("rm: cannot remove 'test/': Is a directory", cli.processCommand(command12));

        Command command13 = new Command("rm", "test1");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command13));

        Command command14 = new Command("ls", "");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("test/", cli.processCommand(command14));

        Command command15 = new Command("cd", "..");
        assertEquals("user@test:~/test$", cli.getHeader());
        assertEquals("", cli.processCommand(command15));

        Command command16 = new Command("pwd", "");
        assertEquals("user@test:~$", cli.getHeader());
        assertEquals("/home", cli.processCommand(command16));
    }
}
