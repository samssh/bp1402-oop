public class OthelloDisk {
    public static final OthelloDisk BLACK = new OthelloDisk(true);
    public static final OthelloDisk WHITE = new OthelloDisk(false);

    public static OthelloDisk getDisk(boolean black){
        return black ? BLACK : WHITE;
    }

    private final boolean black;

    private OthelloDisk(boolean black) {
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
