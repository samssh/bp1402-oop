public class OthelloMain {
    public static void main(String[] args) {
        int n = 4;
        OthelloPlayer player1 = new OthelloPlayer("p1");
        OthelloPlayer player2 = new OthelloPlayer("p2");
        OthelloGame game = new OthelloGame(player1, player2, n);

        System.out.println(game.getResultOfGame());
        System.out.println(game.getBoard().toString());
        System.out.println(game.getBoard().toString());
        System.out.println(game.getBoard().getPervBoard());
        System.out.println(game.getBoard().isBlackPlayerToMove());

        // true
        System.out.println(game.isValidMove(true,0,0));
        System.out.println(game.isValidMove(true,0,1));
        System.out.println(game.isValidMove(true,0,1));
        // false
        System.out.println(game.isValidMove(true,1,1));
        // false
        System.out.println(game.isValidMove(true,2,0));
        // false (black player must move. all move from white player is invalid)
        System.out.println(game.isValidMove(true,0,0));
        System.out.println(game.getBoard());
        game.movePlayer(true, 0, 1);
        //* B * *
        //* B B *
        //* B W *
        //* * * *
        System.out.println(game.getBoard());
        System.out.println();
        game.movePlayer(false, 0, 0);
        //W B * *
        //* W B *
        //* B W *
        //* * * *
        System.out.println(game.getBoard());
        System.out.println();
        game.movePlayer(true, 1, 0);
        //W B * *
        //* W B *
        //* B W *
        //* * * *
        System.out.println(game.getBoard());
        System.out.println();
        //* B * *
        //* B B *
        //* B W *
        //* * * *
        System.out.println(game.getBoard().getPervBoard().getPervBoard());
    }
}
