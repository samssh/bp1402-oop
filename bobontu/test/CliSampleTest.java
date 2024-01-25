import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CliSampleTest {

    @Test
    public void testSample() {
        Cli cli = new Cli("test", "user");
        Command command0 = new Command("mkdir","test");
        assertEquals("user@test:~$",cli.getHeader());
        assertEquals("",cli.processCommand(command0));

        Command command1 = new Command("mkdir","test");
        assertEquals("user@test:~$",cli.getHeader());
        assertEquals("mkdir: cannot create directory 'test': File exists",cli.processCommand(command1));

        Command command2 = new Command("cd","test");
        assertEquals("user@test:~$",cli.getHeader());
        assertEquals("",cli.processCommand(command2));

        Command command3 = new Command("mkdir","test");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command3));

        Command command4 = new Command("pwd","");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("/home/test",cli.processCommand(command4));

        Command command5 = new Command("ls","");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("test/",cli.processCommand(command5));

        Command command6 = new Command("mkdir","test2");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command6));

        Command command7 = new Command("ls","");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("test/\ntest2/",cli.processCommand(command7));

        Command command8 = new Command("rm","test1");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("rm: cannot remove 'test1': No such file or directory",cli.processCommand(command8));

        Command command9 = new Command("rmdir","test2");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command9));

        Command command10 = new Command("touch","test1");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command10));

        Command command11 = new Command("ls","");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("test/\ntest1",cli.processCommand(command11));

        Command command12 = new Command("rm","test");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("rm: cannot remove 'test/': Is a directory",cli.processCommand(command12));

        Command command13 = new Command("rm","test1");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command13));

        Command command14 = new Command("ls","");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("test/",cli.processCommand(command14));

        Command command15 = new Command("cd","..");
        assertEquals("user@test:~/test$",cli.getHeader());
        assertEquals("",cli.processCommand(command15));

        Command command16 = new Command("pwd","");
        assertEquals("user@test:~$",cli.getHeader());
        assertEquals("/home",cli.processCommand(command16));
    }
}
