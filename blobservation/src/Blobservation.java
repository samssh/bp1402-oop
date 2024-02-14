import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Blobservation {

    protected int width;
    protected int height;

    protected Blob[][] table;

    protected List[][] queue;


    // Class instances will always be instantiated with valid arguments
    public Blobservation(int width, int height) {
        this.width = width;
        this.height = height;
        table = new Blob[width + 1][height + 1];
        queue = new ArrayList[width + 1][height + 1];
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                queue[i][j] = new ArrayList();
            }
        }
    }

    private static int findMinDist(Blob blob, List<Blob> targets) {
        int min = Integer.MAX_VALUE;
        for (Blob b : targets)
            min = Math.min(min, BlobUtils.dist(b, blob));
        return min;
    }

    private static int findMaxSize(List<Blob> targets) {
        int max = Integer.MIN_VALUE;
        for (Blob b : targets)
            max = Math.max(max, b.getSize());
        return max;
    }

    public void populate(List<Map<String, Integer>> blobs) {
        for (Map<String, Integer> blob : blobs) {
            if (!blob.containsKey("x") || !blob.containsKey("y") || !blob.containsKey("size"))
                throw new IllegalArgumentException("blob fields left empty");


            int x = blob.get("x");
            int y = blob.get("y");
            int size = blob.get("size");


            // check for invalid input
            if (x > width || y > height || x < 1 || y < 1)
                throw new IllegalArgumentException("blob location of of bounds!");
            if (size < 1) throw new IllegalArgumentException("blob size can't be less than 1!");
        }


        for (Map<String, Integer> blob : blobs) {
            int x = blob.get("x");
            int y = blob.get("y");
            int size = blob.get("size");

            // insert or modify
            if (table[x][y] != null) {
                table[x][y].setSize(table[x][y].getSize() + size);
            } else {
                table[x][y] = new Blob(x, y, size);
            }
        }
    }

    public void move(int steps) {
        if (steps <= 0) throw new IllegalArgumentException("steps can't be negative!");
        for (int i = 0; i < steps; i++) {
            move();
        }
    }

    public void move() {
        // TODO
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                if (table[i][j] == null) {
                    continue;
                }

                if (table[i][j].getSize() == 4) System.out.println();

                Blob arashBlob = table[i][j];

                // list all valid blobs
                List<Blob> targets = new ArrayList<>();
                for (int k = 1; k <= width; k++) {
                    for (int l = 1; l <= height; l++) {
                        if (table[k][l] == null || (k == i && l == j)) {
                            continue;
                        }


                        if (table[k][l].getSize() < table[i][j].getSize()) {
                            targets.add(table[k][l]);
                        }
                    }
                }

                // not-moving blobs
                if (targets.isEmpty()) {
                    queue[i][j].add(arashBlob);
                    continue;
                }

                int minDist = findMinDist(arashBlob, targets);
                targets = targets.stream().filter(blob1 -> BlobUtils.dist(blob1, arashBlob) == minDist).collect(Collectors.toList());
                int maxSize = findMaxSize(targets);
                targets = targets.stream().filter(blob1 -> blob1.getSize() == maxSize).collect(Collectors.toList());
                Blob target = targets.stream().min(Comparator.comparingDouble(b -> BlobUtils.getAngle(arashBlob, b))).get();
                move(arashBlob, target);
            }
        }
        clearTable();
        fuseAndFinallize();
    }

    private void move(Blob b, Blob target) {
        int x = b.getX();
        int y = b.getY();
        if (target.getX() == b.getX()) {
            if (target.getY() > b.getY()) y += 1;
            else y -= 1;
        } else if (target.getY() == b.getY()) {
            if (target.getX() > b.getX()) x += 1;
            else x -= 1;
        } else {
            x += (target.getX() > b.getX()) ? 1 : -1;
            y += (target.getY() > b.getY()) ? 1 : -1;
        }
        queue[x][y].add(b);
    }

    private void fuseAndFinallize() {
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                if (queue[i][j].isEmpty()) continue;
                int size = 0;
                for (Blob b : (List<Blob>) queue[i][j]) {
                    size += b.getSize();
                }
                table[i][j] = new Blob(i, j, size);
                queue[i][j].clear();
            }
        }
    }

    private void clearTable() {
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                table[i][j] = null;
            }
        }
    }

    public Blob[][] getTable() {
        return table;
    }
}
