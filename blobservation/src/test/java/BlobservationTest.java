import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BlobservationTest {


    @Test
    public void basic_populate_test() {
        // setup
        List<Map<String, Integer>> map = new ArrayList<>();
        ArashBlob b1 = new ArashBlob(1, 1, 1);
        ArashBlob b2 = new ArashBlob(2, 2, 4);
        ArashBlob b3 = new ArashBlob(1, 2, 2);
        map.add(b1.serialize());
        map.add(b2.serialize());
        map.add(b3.serialize());
        Blobservation o = new Blobservation(4, 4);


        // act
        o.populate(map);


        // test
        assertNotNull(o.getTable());
        assertNotNull(o.getTable()[1][2]);
        assertEquals(2, o.getTable()[1][2].getSize());
    }


    @Test
    public void fuse_and_overwrite_test() {
        // setup
        List<Map<String, Integer>> map = new ArrayList<>();
        Blobservation b = new Blobservation(10, 10);
        ArashBlob b1 = new ArashBlob(10, 10, 2);
        ArashBlob b2 = new ArashBlob(1, 1, 1);
        map.add(b1.serialize());
        map.add(b2.serialize());


        // act
        b.populate(map);


        // test
        assertEquals(2, b.getTable()[10][10].getSize());
        assertEquals(1, b.getTable()[1][1].getSize());


        // act
        map = new ArrayList<>();
        b1 = new ArashBlob(10, 10, 2);
        b2 = new ArashBlob(1, 1, 1);
        map.add(b1.serialize());
        map.add(b2.serialize());
        b.populate(map);


        // test
        assertEquals(4, b.getTable()[10][10].getSize());
        assertEquals(2, b.getTable()[1][1].getSize());
    }


    @Test
    public void illegal_populate_arguments_test() {
        // setup
        Blobservation b = new Blobservation(10, 10);
        List<Map<String, Integer>> map = new ArrayList<>();
        ArashBlob b0 = new ArashBlob(1, 1, 1);
        ArashBlob b1 = new ArashBlob(11, 1, 1);
        ArashBlob b2 = new ArashBlob(1, -1, 1);
        ArashBlob b3 = new ArashBlob(1, 1, -1);
        Map<String, Integer> obj = new HashMap<>();
        obj.put("x", 1);
        obj.put("y", 2);


        // act / test
        map.add(b0.serialize());
        map.add(b1.serialize());
        assertThrows(IllegalArgumentException.class, () -> b.populate(map));
        map.clear();

        map.add(b2.serialize());
        assertThrows(IllegalArgumentException.class, () -> b.populate(map));
        map.clear();

        map.add(b3.serialize());
        assertThrows(IllegalArgumentException.class, () -> b.populate(map));
        map.clear();

        map.add(obj);
        assertThrows(IllegalArgumentException.class, () -> b.populate(map));

        assertNull(b.getTable()[1][1]);
    }


    @Test
    public void basic_move() {
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


    @Test
    public void clockwise_priority_test() {
        List<List<Integer>> exclusions = new ArrayList<>();
        exclusions.add(Arrays.asList());
        exclusions.add(Arrays.asList(3));
        exclusions.add(Arrays.asList(3, 4));
        exclusions.add(Arrays.asList(3, 4, 7));
        exclusions.add(Arrays.asList(3, 4, 7, 9));
        exclusions.add(Arrays.asList(3, 4, 7, 9, 5));
        exclusions.add(Arrays.asList(3, 4, 7, 9, 5, 8));
        exclusions.add(Arrays.asList(3, 4, 7, 9, 5, 8, 6));
        exclusions.add(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9));

        // setup
        List<Map<String, Integer>> map = new ArrayList<>();
        ArashBlob[] toInsert = new ArashBlob[10];
        toInsert[1] = new ArashBlob(4, 4, 2);
        toInsert[2] = new ArashBlob(2, 2, 1);
        toInsert[3] = new ArashBlob(4, 2, 1);
        toInsert[4] = new ArashBlob(6, 2, 1);
        toInsert[5] = new ArashBlob(4, 6, 1);
        toInsert[6] = new ArashBlob(2, 4, 1);
        toInsert[7] = new ArashBlob(6, 4, 1);
        toInsert[8] = new ArashBlob(2, 6, 1);
        toInsert[9] = new ArashBlob(6, 6, 1);

        for (int c = 0; c < 9; c++) {
            for (int i = 1; i <= 8; i++)
                if (!exclusions.get(c).contains(i))
                    map.add(toInsert[i].serialize());

            Solution solution = new Solution(8, 8);
            Blobservation b = new Blobservation(8, 8);
            solution.populate(map);
            b.populate(map);


            // test
            solution.move();
            b.move();

            // test
            assertTableEquals(solution.getTable(), b.getTable());
        }
    }


    @Test
    public void multi_movement_test() {
        List<Long> seeds = new ArrayList<>();
        seeds.add(898627218243035994L);
        seeds.add(98085765269096505L);
        seeds.add(994818480150942467L);
        seeds.add(858888828540691414L);
        seeds.add(12153778122059020L);


        for (int c = 0; c < 5; c++) {
            // setup
            Random random = new Random(seeds.get(c));
            int width = getRandomInteger(random, 8, 50);
            int height = getRandomInteger(random, 8, 50);

            Blobservation blobservation = new Blobservation(width, height);

            int count = getRandomInteger(random, 64, width * height);
            int maxSize = getRandomInteger(random, 1, 21);

            List<Map<String, Integer>> map = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int x = getRandomInteger(random, 1, width);
                int y = getRandomInteger(random, 1, height);
                int size = getRandomInteger(random, 1, maxSize);
                map.add(new ArashBlob(x, y, size).serialize());
            }

            Solution solution = new Solution(width, height);
            solution.populate(map);
            blobservation.populate(map);


            // act / test
            int testCount = getRandomInteger(random, 10, 40);
            for (int i = 0; i < testCount; i++) {
                int steps = getRandomInteger(random, 1, 4);
                blobservation.move(steps);
                solution.move(steps);
                assertTableEquals(solution.getTable(), blobservation.getTable());
            }
        }
    }


    @Test
    public void complex_movement_test() {
        // setup
        List<Map<String, Integer>> map = new ArrayList<>();
        ArashBlob b1 = new ArashBlob(3, 4, 6);
        ArashBlob b2 = new ArashBlob(4, 4, 5);
        ArashBlob b3 = new ArashBlob(5, 4, 7);
        ArashBlob b4 = new ArashBlob(6, 4, 4);

        map.add(b1.serialize());
        map.add(b2.serialize());
        map.add(b3.serialize());
        map.add(b4.serialize());

        Blobservation blobservation = new Blobservation(8, 8);
        Solution s = new Solution(8, 8);
        s.populate(map);
        blobservation.populate(map);

        // act
        s.move();
        blobservation.move();


        // test
        assertTableEquals(s.getTable(), blobservation.getTable());
    }


    @Test
    public void general_movement_test() {
        List<Long> seeds = new ArrayList<>();
        seeds.add(95099465300028494L);
        seeds.add(871963428464504961L);
        seeds.add(669661609938631039L);
        seeds.add(23360593640468977L);
        seeds.add(679989740840000803L);

        for (int c = 0; c < 5; c++) {
            // setup
            Random random = new Random(seeds.get(c));
            int width = getRandomInteger(random, 8, 50);
            int height = getRandomInteger(random, 8, 50);

            Blobservation blobservation = new Blobservation(width, height);

            int count = getRandomInteger(random, 64, width * height);
            int maxSize = getRandomInteger(random, 1, 21);

            List<Map<String, Integer>> map = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int x = getRandomInteger(random, 1, width);
                int y = getRandomInteger(random, 1, height);
                int size = getRandomInteger(random, 1, maxSize);
                map.add(new ArashBlob(x, y, size).serialize());
            }

            Solution s = new Solution(width, height);
            s.populate(map);
            blobservation.populate(map);


            // act / test
            int testCount = getRandomInteger(random, 20, 60);
            for (int i = 0; i < testCount; i++) {
                blobservation.move();
                s.move();
                assertTableEquals(s.getTable(), blobservation.getTable());
            }
        }
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


    // min inclusive max exclusive
    public static int getRandomInteger(Random random, int min, int max) {
        return min + (int) (random.nextDouble() * (max - min));
    }


    public static <T extends Throwable> void assertThrows(Class<T> expectedThrowable,
                                                          Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable actualThrown) {
            if (expectedThrowable.isInstance(actualThrown)) {
                return;
            } else {
                String mismatchMessage = "unexpected exception type thrown;";
                throw new AssertionError(mismatchMessage, actualThrown);
            }
        }
        String notThrownMessage = String.format("expected %s to be thrown, but nothing was thrown",
                formatClass(expectedThrowable));
        throw new AssertionError(notThrownMessage);
    }

    public static String formatClass(Class<?> value) {
        String className = value.getCanonicalName();
        return className == null ? value.getName() : className;
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
}