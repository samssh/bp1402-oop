public class OthelloBoard {
    private final OthelloDisk[][] disks;
    private final OthelloBoard pervBoard;
    private boolean blackPlayerToMove;

    public OthelloBoard(int n) {
        disks = new OthelloDisk[n][n];
        pervBoard = null;
        disks[n / 2 - 1][n / 2 - 1] = OthelloDisk.WHITE;
        disks[n / 2][n / 2 - 1] = OthelloDisk.BLACK;
        disks[n / 2 - 1][n / 2] = OthelloDisk.BLACK;
        disks[n / 2][n / 2] = OthelloDisk.WHITE;
        blackPlayerToMove = true;
    }


    public OthelloBoard(OthelloBoard pervBoard, boolean blackPlayerToMove) {
        disks = new OthelloDisk[pervBoard.disks.length][pervBoard.disks.length];
        for (int i = 0; i < disks.length; i++) {
            for (int j = 0; j < disks[i].length; j++) {
                disks[i][j] = pervBoard.getDisk(i, j);
            }
        }
        this.pervBoard = pervBoard;
        this.blackPlayerToMove = blackPlayerToMove;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (OthelloDisk[] rowDisks : disks) {
            for (OthelloDisk disk : rowDisks) {
                if (disk == null) stringBuilder.append("*");
                else stringBuilder.append(disk);
                stringBuilder.append(' ');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public OthelloDisk getDisk(int i, int j) {
        return disks[i][j];
    }

    public OthelloBoard getPervBoard() {
        return pervBoard;
    }

    public boolean isBlackPlayerToMove() {
        return blackPlayerToMove;
    }

    public void setBlackPlayerToMove(boolean blackPlayerToMove) {
        this.blackPlayerToMove = blackPlayerToMove;
    }

    public OthelloDisk[][] getDisks() {
        return disks;
    }
}
