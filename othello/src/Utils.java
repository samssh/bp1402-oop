public class Utils {
    private static final int[] dxs = new int[]{1, 1, 1, 0, 0, -1, -1, -1};
    private final static int[] dys = new int[]{1, 0, -1, 1, -1, 1, 0, -1};

    public static boolean isInBound(int i, int j, int n) {
        return (0 <= i && i < n) && (0 <= j && j < n);
    }

    public static boolean canPlayerMove(boolean black, OthelloDisk[][] disks) {
        for (int i = 0; i < disks.length; i++) {
            for (int j = 0; j < disks[i].length; j++) {
                if (isValidMove(black, disks, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidMove(boolean black, OthelloDisk[][] disks, int i, int j) {
        if (disks[i][j] != null)
            return false;
        for (int k = 0; k < 8; k++) {
            int dx = Utils.dxs[k];
            int dy = Utils.dys[k];
            int x = i + dx, y = j + dy;
            boolean seenOtherColor = false;
            while (Utils.isInBound(x, y, disks.length) && disks[x][y] != null) {
                if (disks[x][y].isBlack() == black) {
                    if (seenOtherColor) return true;
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

    public static void putDisk(OthelloDisk disk, OthelloDisk[][] disks, int i, int j) {
        disks[i][j] = disk;
        for (int k = 0; k < 8; k++) {
            int dx = Utils.dxs[k];
            int dy = Utils.dys[k];
            int x = i + dx, y = j + dy;
            boolean seenOtherColor = false;
            boolean changeOtherColors = false;
            while (Utils.isInBound(x, y, disks.length) && disks[x][y] != null) {
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
                while (Utils.isInBound(x, y, disks.length) && disks[x][y] != null &&
                        disks[x][y].isBlack() != disk.isBlack()) {
                    disks[x][y] = disk;
                    x += dx;
                    y += dy;
                }
            }
        }
    }

}
