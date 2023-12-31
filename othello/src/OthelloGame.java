public class OthelloGame {
    private final OthelloPlayer player1, player2;
    private final int n;
    private OthelloBoard board;
    private boolean running;

    public OthelloGame(OthelloPlayer player1, OthelloPlayer player2, int n) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new OthelloBoard(n);
        this.running = true;
        this.n = n;
    }

    public boolean isValidMove(boolean blackPlayer, int i, int j) {
        if (board.isBlackPlayerToMove() != blackPlayer)
            return false;
        return Utils.isValidMove(blackPlayer, board.getDisks(), i, j);
    }

    public void movePlayer(boolean blackPlayer, int i, int j) {
        if (board.isBlackPlayerToMove() != blackPlayer)
            return;
        if (isValidMove(blackPlayer, i, j)) {
            board = new OthelloBoard(board, blackPlayer);
            OthelloDisk disk = OthelloDisk.getDisk(blackPlayer);
            Utils.putDisk(disk, board.getDisks(), i, j);
            if (Utils.canPlayerMove(!blackPlayer, board.getDisks())) {
                board.setBlackPlayerToMove(!blackPlayer);
            } else if (Utils.canPlayerMove(blackPlayer, board.getDisks())) {
                board.setBlackPlayerToMove(blackPlayer);
            } else {
                running = false;
            }
        }
    }

    public void undo() {
        if (board.getPervBoard() != null) {
            board = board.getPervBoard();
        }
    }

    public String getResultOfGame() {
        if (running) {
            return "game still running";
        }
        int blackDisks = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board.getDisk(i, j) != null && board.getDisk(i, j).isBlack()) blackDisks++;
            }
        }
        int whiteDisks = n * n - blackDisks;
        if (blackDisks > whiteDisks) return player1.getName();
        else if (blackDisks == whiteDisks) return "draw";
        else return player2.getName();
    }

    public OthelloBoard getBoard() {
        return board;
    }
}
