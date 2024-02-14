import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlobMain {

    private static Map<String, Integer> makeBlob(int x, int y, int size) {
        Map<String, Integer> result = new HashMap<>();
        result.put("x", x);
        result.put("y", y);
        result.put("size", size);

        return result;
    }

    public static void main(String[] args) {
        // set up
        List<Map<String, Integer>> map = Arrays.asList(
                makeBlob(5, 1, 3),
                makeBlob(8, 1, 5),
                makeBlob(1, 3, 2),
                makeBlob(8, 4, 2),
                makeBlob(4, 5, 4),
                makeBlob(7, 6, 2),
                makeBlob(8, 7, 1),
                makeBlob(1, 8, 3),
                makeBlob(3, 8, 1)
        );


        Blobservation blobservation = new Blobservation(8, 8);
        blobservation.populate(map);
        Blob[][] table = blobservation.getTable();

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);

        // act
        blobservation.move();
        printTable(table);
    }


    private static void printTable(Blob[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[j][i] == null)
                    System.out.print("0 ");
                else
                    System.out.print(table[j][i].getSize() + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
