package checkers;

public class MoveCounter {
    private int moveCounter;

    public MoveCounter(int moveCounter){
        this.moveCounter = moveCounter;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    @Override
    public String toString() {
        return "MoveCounter{" +
                "moveCounter=" + moveCounter +
                '}';
    }
}
