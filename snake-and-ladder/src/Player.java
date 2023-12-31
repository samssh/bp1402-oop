import java.util.Random;

public class Player {
    private final String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 1;
    }

    public int dice() {
        if (name.startsWith("S")){
            return 5;
        }
        if (name.startsWith("T")){
            return 1;
        }
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public void move(int x, int lastCell) {
        int newPosition = getPosition() + x;
        if (newPosition <= lastCell) setPosition(getPosition() + x);
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
