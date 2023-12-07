import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.nio.file.Files;


import static org.junit.Assert.*;

public class OthelloTest {
    private enum OthelloDiskTest {
        BLACK(true),
        WHITE(false);
        private final boolean black;

        OthelloDiskTest(boolean black) {
            this.black = black;
        }

        public boolean isBlack() {
            return black;
        }

        @Override
        public String toString() {
            return black ? "B" : "W";
        }
    }

    private enum MoveResult {
        BLACK_TO_MOVE, WHITE_TO_MOVE, DRAW, BLACK_WIN, WHITE_WIN
    }

    final String still = "game still running";

    @Test
    public void test4x4() {
        int n = 4;
        OthelloPlayer player1 = new OthelloPlayer("p1");
        OthelloPlayer player2 = new OthelloPlayer("p2");
        OthelloGame game = new OthelloGame(player1, player2, n);
        assertEquals(still, game.getResultOfGame());
        OthelloDiskTest[][] board = createBoard(n);
        boolean playerToMove = true;
        validateState(game, board, null, playerToMove, n);
        MoveResult result = MoveResult.BLACK_TO_MOVE;
        result = move(game, board, result, n, 0, 1);
        result = move(game, board, result, n, 0, 0);
        result = move(game, board, result, n, 1, 0);
        result = move(game, board, result, n, 2, 0);
        result = move(game, board, result, n, 3, 0);
        result = move(game, board, result, n, 3, 2);
        result = move(game, board, result, n, 3, 1);
        result = move(game, board, result, n, 1, 3);
        result = move(game, board, result, n, 2, 3);
        result = move(game, board, result, n, 0, 2);
        result = move(game, board, result, n, 3, 3);
        move(game, board, result, n, 0, 3);
    }

    @Test
    public void test4x4undo() {
        int n = 4;
        OthelloPlayer player1 = new OthelloPlayer("p1");
        OthelloPlayer player2 = new OthelloPlayer("p2");
        OthelloGame game = new OthelloGame(player1, player2, n);
        assertEquals(still, game.getResultOfGame());
        OthelloDiskTest[][] board = createBoard(n);
        boolean playerToMove = true;
        validateState(game, board, null, playerToMove, n);
        MoveResult result = MoveResult.BLACK_TO_MOVE;
        OthelloDiskTest[][] board1 = cloneBoard(board);
        move(game, board1, result, n, 1, 0);
        game.undo();
        result = move(game, board, result, n, 0, 1);
        result = move(game, board, result, n, 0, 0);
        result = move(game, board, result, n, 1, 0);
        result = move(game, board, result, n, 2, 0);
        result = move(game, board, result, n, 3, 0);
        result = move(game, board, result, n, 3, 2);
        result = move(game, board, result, n, 3, 1);
        result = move(game, board, result, n, 1, 3);
        result = move(game, board, result, n, 2, 3);
        board1 = cloneBoard(board);
        MoveResult result1 = move(game, board1, result, n, 3, 3);
        move(game, board1, result1, n, 0, 2);
        game.undo();
        game.undo();
        result = move(game, board, result, n, 0, 2);
        result = move(game, board, result, n, 3, 3);
        move(game, board, result, n, 0, 3);
    }

    @Test
    public void test6x6rand0() {
        random(6, 0);
    }

    @Test
    public void test8x8rand0() {
        random(8, 0);
    }

    @Test
    public void test8x8rand1() {
        random(8, 2);
    }

    @Test
    public void test8x8randDraw() {
        random(8, 12);
    }

    private void random(int n, int q) {
        OthelloPlayer player1 = new OthelloPlayer("p1");
        OthelloPlayer player2 = new OthelloPlayer("p2");
        OthelloGame game = new OthelloGame(player1, player2, n);
        assertEquals(still, game.getResultOfGame());
        OthelloDiskTest[][] board = createBoard(n);
        validateState(game, board, null, true, n);
        MoveResult result = MoveResult.BLACK_TO_MOVE;
        Random random = new Random(q);
        while (result != MoveResult.DRAW && result != MoveResult.BLACK_WIN && result != MoveResult.WHITE_WIN) {
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            result = move(game, board, result, n, i, j);
        }
    }


    private OthelloDiskTest[][] createBoard(int n) {
        OthelloDiskTest[][] board = new OthelloDiskTest[n][n];
        board[n / 2 - 1][n / 2 - 1] = OthelloDiskTest.WHITE;
        board[n / 2 - 1][n / 2] = OthelloDiskTest.BLACK;
        board[n / 2][n / 2 - 1] = OthelloDiskTest.BLACK;
        board[n / 2][n / 2] = OthelloDiskTest.WHITE;
        return board;
    }

    private OthelloDiskTest[][] cloneBoard(OthelloDiskTest[][] board) {
        OthelloDiskTest[][] result = new OthelloDiskTest[board.length][board.length];
        for (int i = 0; i < result.length; i++) {
            System.arraycopy(board[i], 0, result[i], 0, result[i].length);
        }
        return result;
    }

    private void validateState(OthelloGame game, OthelloDiskTest[][] board, OthelloDiskTest[][] board1, boolean playerToMove, int n) {
        assertEquals("board to string is wrong", toStringBoard(board), game.getBoard().toString());
        if (board1 != null) {
            assertEquals("perv board to string is wrong", toStringBoard(board1), game.getBoard().getPervBoard().toString());
        } else {
            assertNull(game.getBoard().getPervBoard());
        }
        assertEquals("player to move is wrong", playerToMove, game.getBoard().isBlackPlayerToMove());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals("wrong valid move result", isValidMove(playerToMove, board, i, j), game.isValidMove(playerToMove, i, j));
                assertFalse("wrong valid move result", game.isValidMove(!playerToMove, i, j));
            }
        }
    }

    private MoveResult move(OthelloGame game, OthelloDiskTest[][] board, MoveResult resultBefore, int n, int x, int y) {
        boolean playerToMove;
        if (resultBefore == MoveResult.BLACK_TO_MOVE) playerToMove = true;
        else if (resultBefore == MoveResult.WHITE_TO_MOVE) playerToMove = false;
        else throw new RuntimeException();
        if (!isValidMove(playerToMove, board, x, y)) {
            return resultBefore;
        }
        game.movePlayer(playerToMove, x, y);
        OthelloDiskTest disk = playerToMove ? OthelloDiskTest.BLACK : OthelloDiskTest.WHITE;
        OthelloDiskTest[][] board1 = cloneBoard(board);
        putDisk(disk, board, x, y);
        MoveResult result;
        if (canPlayerMove(!playerToMove, board)) {
            result = playerToMove ? MoveResult.WHITE_TO_MOVE : MoveResult.BLACK_TO_MOVE;
        } else if (canPlayerMove(playerToMove, board)) {
            result = playerToMove ? MoveResult.BLACK_TO_MOVE : MoveResult.WHITE_TO_MOVE;
        } else {
            int blackDisks = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] != null && board[i][j].isBlack()) blackDisks++;
                }
            }
            int whiteDisks = n * n - blackDisks;
            if (blackDisks > whiteDisks) result = MoveResult.BLACK_WIN;
            else if (blackDisks == whiteDisks) result = MoveResult.DRAW;
            else result = MoveResult.WHITE_WIN;
        }
        if (result == MoveResult.BLACK_TO_MOVE) {
            assertEquals(still, game.getResultOfGame());
            validateState(game, board, board1, true, n);
        } else if (result == MoveResult.WHITE_TO_MOVE) {
            assertEquals(still, game.getResultOfGame());
            validateState(game, board, board1, false, n);
        } else {
            assertEquals("board to string is wrong", toStringBoard(board), game.getBoard().toString());
            String resultOfGame = null;
            switch (result) {
                case BLACK_WIN:
                    resultOfGame = "p1";
                    break;
                case WHITE_WIN:
                    resultOfGame = "p2";
                    break;
                case DRAW:
                    resultOfGame = "draw";
            }
            assertEquals("game result is wrong", resultOfGame, game.getResultOfGame());
        }
        return result;
    }


    private final int[] dxs = new int[]{1, 1, 1, 0, 0, -1, -1, -1};
    private final int[] dys = new int[]{1, 0, -1, 1, -1, 1, 0, -1};

    private boolean isInBound(int i, int j, int n) {
        return (0 <= i && i < n) && (0 <= j && j < n);
    }

    private boolean canPlayerMove(boolean black, OthelloDiskTest[][] disks) {
        for (int i = 0; i < disks.length; i++) {
            for (int j = 0; j < disks[i].length; j++) {
                if (isValidMove(black, disks, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidMove(boolean black, OthelloDiskTest[][] disks, int i, int j) {
        if (disks[i][j] != null)
            return false;
        for (int k = 0; k < 8; k++) {
            int dx = dxs[k];
            int dy = dys[k];
            int x = i + dx, y = j + dy;
            boolean seenOtherColor = false;
            while (isInBound(x, y, disks.length) && disks[x][y] != null) {
                if (disks[x][y].isBlack() == black) {
                    if (seenOtherColor)
                        return true;
                    else break;
                } else {
                    seenOtherColor = true;
                }
                x += dx;
                y += dy;
            }
        }
        return false;
    }

    private void putDisk(OthelloDiskTest disk, OthelloDiskTest[][] disks, int i, int j) {
        disks[i][j] = disk;
        for (int k = 0; k < 8; k++) {
            int dx = dxs[k];
            int dy = dys[k];
            int x = i + dx, y = j + dy;
            boolean seenOtherColor = false;
            boolean changeOtherColors = false;
            while (isInBound(x, y, disks.length) && disks[x][y] != null) {
                if (disks[x][y].isBlack() == disk.isBlack()) {
                    if (seenOtherColor) changeOtherColors = true;
                    else break;
                } else {
                    seenOtherColor = true;
                }
                x += dx;
                y += dy;
            }

            if (changeOtherColors) {
                x = i + dx;
                y = j + dy;
                while (isInBound(x, y, disks.length) && disks[x][y] != null &&
                        disks[x][y].isBlack() != disk.isBlack()) {
                    disks[x][y] = disk;
                    x += dx;
                    y += dy;
                }
            }

        }
    }

    private String toStringBoard(OthelloDiskTest[][] disks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (OthelloDiskTest[] rowDisks : disks) {
            for (OthelloDiskTest disk : rowDisks) {
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

    @Test
    public void testCountLines() throws IOException {
        List<File> files = new LinkedList<>();
        listFile("src/main/java", files);
        int cnt = 0;
        for (File file : files) {
            if (file.getPath().endsWith(".java")) {
                byte[] data = Files.readAllBytes(file.toPath());
                String content = new String(data);
                for (int i = 0; i < content.length(); i++) {
                    if (content.charAt(i) == ';') cnt++;
                }
            }
        }
        if (cnt > 170) {
            fail("There are over 170 semicolons in your code. number of semicolons: " + cnt);
        }
        System.out.println("number of semicolons: " + cnt);
    }


    public void listFile(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listFile(file.getAbsolutePath(), files);
                }
            }
    }
}
