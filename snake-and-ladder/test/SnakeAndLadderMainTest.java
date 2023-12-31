import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SnakeAndLadderMainTest {
    int[] goToCell = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 41, 10, 11, 22, 13, 14, 40, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 99, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 79, 45, 46, 47, 48, 49, 60, 51, 43, 53, 54, 55, 56, 57, 58,
            59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 60, 71, 72, 73, 74, 75, 76, 77,
            78, 79, 58, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 63, 92, 93, 94, 95, 80,
            49, 98, 99, 100
    };

    @Test
    public void playerTest1() {
        Player player = new Player("Sadat");
        assertEquals("Sadat", player.getName());
        player.setPosition(10);
        assertEquals(10, player.getPosition());
    }

    @Test
    public void playerTest2() {
        Player player = new Player("Tofighi");
        assertEquals("Tofighi", player.getName());
        int x = player.dice();
        player.move(x, 100);
        assertEquals(x + 1, player.getPosition());
    }

    @Test
    public void test1() {
        GameManager gameManager = new GameManager(goToCell, 100);
        Player player1 = new Player("Sadat");
        gameManager.addPlayer(player1);
        Player player2 = new Player("Tofighi");
        gameManager.addPlayer(player2);

        assertEquals(2, gameManager.getPlayers().size());
    }

    @Test
    public void test2() {
        GameManager gameManager = new GameManager(goToCell, 100);
        Player player1 = new PlayerSpy("Sadat", 5);
        Player player2 = new PlayerSpy("Tofighi", 2);
        gameManager.addPlayer(player1);
        gameManager.addPlayer(player2);
        player1.setPosition(10);
        player2.setPosition(79);

        gameManager.initiateNextTurn();
        assertEquals(40, player1.getPosition());
    }

    @Test
    public void test3() {
        GameManager gameManager = new GameManager(goToCell, 100);
        Player player1 = new PlayerSpy("Sadat", 5);
        Player player2 = new PlayerSpy("Tofighi", 1);
        gameManager.addPlayer(player1);
        gameManager.addPlayer(player2);
        player1.setPosition(10);
        player2.setPosition(79);

        gameManager.initiateNextTurn();
        assertEquals(40, player1.getPosition());

        gameManager.initiateNextTurn();
        assertEquals(58, player2.getPosition());
    }

    @Test
    public void test4() {
        GameManager gameManager = new GameManager(goToCell, 100);
        Player player1 = new PlayerSpy("Sadat", 5);
        Player player2 = new PlayerSpy("Tofighi", 1);
        gameManager.addPlayer(player1);
        gameManager.addPlayer(player2);
        player1.setPosition(99);
        player2.setPosition(99);

        gameManager.initiateNextTurn();
        assertEquals(99, player1.getPosition());

        gameManager.initiateNextTurn();
        assertEquals(100, player2.getPosition());

        assertTrue(gameManager.isFinished());
    }

    static class PlayerSpy extends Player {
        int defaultDiceResult;

        public PlayerSpy(String name, int defaultDiceResult) {
            super(name);
            this.defaultDiceResult = defaultDiceResult;
        }

        @Override
        public int dice() {
            return this.defaultDiceResult;
        }
    }
}