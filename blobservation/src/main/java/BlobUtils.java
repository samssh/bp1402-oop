public class BlobUtils {
    public static int dist(Blob a, Blob b) {
        return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    public static double getAngle(Blob pivot, Blob b) {
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
