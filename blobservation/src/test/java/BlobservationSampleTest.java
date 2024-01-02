import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BlobservationSampleTest {


    @Test
    public void sample_test() {
        // set up
        List<Map<String, Integer>> map = new ArrayList<>();

        ArashBlob b1 = new ArashBlob(5, 1, 3);
        ArashBlob b2 = new ArashBlob(8, 1, 5);
        ArashBlob b3 = new ArashBlob(1, 3, 2);
        ArashBlob b4 = new ArashBlob(8, 4, 2);
        ArashBlob b5 = new ArashBlob(4, 5, 4);
        ArashBlob b6 = new ArashBlob(7, 6, 2);
        ArashBlob b7 = new ArashBlob(8, 7, 1);
        ArashBlob b8 = new ArashBlob(1, 8, 3);
        ArashBlob b9 = new ArashBlob(3, 8, 1);

        map.add(b1.serialize());
        map.add(b2.serialize());
        map.add(b3.serialize());
        map.add(b4.serialize());
        map.add(b5.serialize());
        map.add(b6.serialize());
        map.add(b7.serialize());
        map.add(b8.serialize());
        map.add(b9.serialize());

        Blobservation blobservation = new Blobservation(8, 8);
        Solution solution = new Solution(8, 8);
        solution.populate(map);
        blobservation.populate(map);
        Blob[][] table = blobservation.getTable();
        ArashBlob[][] targetTable = solution.getTable();


        // act
        blobservation.move();


        // test
        solution.move();
        assertTableEquals(targetTable, table);


        // act
        blobservation.move();


        // test
        solution.move();
        assertTableEquals(targetTable, table);


        // act
        blobservation.move();


        // test
        solution.move();
        assertTableEquals(targetTable, table);
    }


    public static void assertTableEquals(ArashBlob[][] expected, Blob[][] actual) {
        for (int i = 1; i < expected.length; i++) {
            for (int j = 1; j < expected[0].length; j++) {

                if (expected[i][j] == null)
                    assertNull(actual[i][j]);

                else
                    assertEquals(expected[i][j].getSize(), actual[i][j].getSize());
            }
        }
    }

    private static class ArashBlob {
        protected int x,y,size;

        public ArashBlob(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }


        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Map<String, Integer> serialize() {
            Map<String, Integer> out = new HashMap<>();
            out.put("x", x);
            out.put("y", y);
            out.put("size", size);
            return out;
        }
    }


    private static class Utils {
        public static int dist(ArashBlob a, ArashBlob b) {
            return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
        }

        public static double getAngle(ArashBlob pivot, ArashBlob b) {
            if (b.getY() < pivot.getY() && b.getX() > pivot.getX()) {
                // first quarter NE 0 < t < 90
                return Math.atan((b.getX() - pivot.getX()) / (double) (pivot.getY() - b.getY()));
            } else if (b.getY() > pivot.getY() && b.getX() > pivot.getX()) {
                // second quarter SE 90 < t < 180
                return Math.atan((b.getX() - pivot.getX()) / (double) (pivot.getY() - b.getY())) + Math.PI;
            } else if (b.getY() > pivot.getY() && b.getX() < pivot.getX()) {
                // third quarter SW 180 < t < 270
                return Math.atan((b.getX() - pivot.getX()) / (double) (pivot.getY() - b.getY())) + Math.PI;
            } else if (b.getY() < pivot.getY() && b.getX() < pivot.getX()) {
                // fourth quarter NW 270 < t < 360
                return Math.atan((b.getX() - pivot.getX()) / (double) (pivot.getY() - b.getY())) + 2 * Math.PI;
            } else if (b.getX() == pivot.getX() && b.getY() < pivot.getY()) {
                return 0;
            } else if (b.getX() == pivot.getX() && b.getY() > pivot.getY()) {
                return Math.PI;
            } else if (b.getY() == pivot.getY() && b.getX() > pivot.getX()) {
                return Math.PI / 2;
            } else {
                return 1.5 * Math.PI;
            }
        }
    }


    private static class Solution {
        protected int width;
        protected int height;

        protected ArashBlob[][] table;

        protected List[][] queue;


    /*
    | - - > x
    |
    v
    y
     */


        public Solution(int width, int height) {

            // Class instances will always be instantiated with valid arguments

            this.width = width;
            this.height = height;
            table = new ArashBlob[width + 1][height + 1];
            queue = new ArrayList[width + 1][height + 1];
            for (int i = 1; i <= width; i++) {
                for (int j = 1; j <= height; j++) {
                    queue[i][j] = new ArrayList();
                }
            }
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
                if (size < 1)
                    throw new IllegalArgumentException("blob size can't be less than 1!");
            }


            for(Map<String, Integer> blob : blobs) {
                int x = blob.get("x");
                int y = blob.get("y");
                int size = blob.get("size");

                // insert or modify
                if (table[x][y] != null) {
                    table[x][y].setSize(table[x][y].getSize() + size);
                } else {
                    table[x][y] = new ArashBlob(x, y, size);
                }
            }
        }

        public void move(int steps) {
            if (steps <= 0)
                throw new IllegalArgumentException("steps can't be negative!");
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

                    if (table[i][j].getSize() == 4)
                        System.out.println();

                    ArashBlob arashBlob = table[i][j];

                    // list all valid blobs
                    List<ArashBlob> targets = new ArrayList<>();
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
                    targets = targets.stream().filter(blob1 -> Utils.dist(blob1, arashBlob) == minDist).
                            collect(Collectors.toList());
                    int maxSize = findMaxSize(targets);
                    targets = targets.stream().filter(blob1 -> blob1.getSize() == maxSize).collect(Collectors.toList());
                    ArashBlob target = targets.stream().min(Comparator.comparingDouble
                            (b -> Utils.getAngle(arashBlob, b))).get();
                    move(arashBlob, target);
                }
            }
            clearTable();
            fuseAndFinallize();
        }

        private static int findMinDist(ArashBlob arashBlob, List<ArashBlob> targets) {
            int min = Integer.MAX_VALUE;
            for (ArashBlob b : targets)
                min = Math.min(min, Utils.dist(b, arashBlob));
            return min;
        }

        private static int findMaxSize(List<ArashBlob> targets) {
            int max = Integer.MIN_VALUE;
            for (ArashBlob b : targets)
                max = Math.max(max, b.getSize());
            return max;
        }

        private void move(ArashBlob b, ArashBlob target) {
            int x = b.getX();
            int y = b.getY();
            if (target.getX() == b.getX()) {
                if (target.getY() > b.getY())
                    y += 1;
                else
                    y-= 1;
            } else if (target.getY() == b.getY()) {
                if (target.getX() > b.getX())
                    x += 1;
                else
                    x -= 1;
            } else  {
                x += (target.getX() > b.getX()) ? 1 : -1;
                y += (target.getY() > b.getY()) ? 1 : -1;
            }
            queue[x][y].add(b);
        }

        private void fuseAndFinallize() {
            for (int i = 1; i <= width; i++) {
                for (int j = 1; j <= height; j++) {
                    if (queue[i][j].isEmpty())
                        continue;
                    int size = 0;
                    for (ArashBlob b : (List<ArashBlob>)queue[i][j]) {
                        size += b.getSize();
                    }
                    table[i][j] = new ArashBlob(i, j, size);
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

        public ArashBlob[][] getTable() {
            return table;
        }
    }

}
