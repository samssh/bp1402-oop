public class SnakeAndLadderMain {
    public static void main(String[] args) {
        int[] goToCell = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 41, 10, 11, 22, 13, 14, 40, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 99, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
                40, 41, 42, 43, 79, 45, 46, 47, 48, 49, 60, 51, 43, 53, 54, 55, 56, 57, 58,
                59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 60, 71, 72, 73, 74, 75, 76, 77,
                78, 79, 9, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 63, 92, 93, 94, 95, 80,
                49, 98, 99, 100
        };
        GameManager gameManager = new GameManager(goToCell, 100);
        Player player1 = new Player("Sadat");
        Player player2 = new Player("Tofighi");
        gameManager.addPlayer(player1);
        gameManager.addPlayer(player2);
        player1.setPosition(10);
        player2.setPosition(79);
        gameManager.initiateNextTurn();
        // 40 if dice of player 1 is 5 else 10 + x
        System.out.println(player1.getPosition());
        // 79
        System.out.println(player2.getPosition());
        gameManager.initiateNextTurn();
        // same as first printed value
        System.out.println(player1.getPosition());
        // 41 if dice of player 2 is 1 else 79 + x
        System.out.println(player2.getPosition());

    }
}
