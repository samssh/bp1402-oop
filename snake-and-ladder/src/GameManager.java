import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private final List<Player> players = new LinkedList<>();
    private final int[] goToCell;
    private final int finalCell;
    private int currentTurn;
    private boolean isFinished;

    public GameManager(int[] goToCell, int finalCell) {
        this.goToCell = goToCell;
        this.finalCell = finalCell;
        this.isFinished = false;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void initiateNextTurn() {
        if (isFinished()) return;
        int playerIndex = getCurrentTurn() % getPlayers().size();
        Player player = getPlayerByIndex(playerIndex);
        player.move(player.dice(), getFinalCell());
        while (performGoToCell(player.getPosition()) != player.getPosition()) {
            player.setPosition(performGoToCell(player.getPosition()));
        }
        if (player.getPosition() == getFinalCell()) {
            setFinished(true);
        }
        setCurrentTurn(getCurrentTurn() + 1);
    }

    public int performGoToCell(int position) {
        return goToCell[position];
    }

    public Player getPlayerByIndex(int x) {
        return getPlayers().get(x);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getFinalCell() {
        return finalCell;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
