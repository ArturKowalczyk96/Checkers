package checkers;

public class Player {
    private PlayerType type;
    private boolean turn;

    public PlayerType getType(){
        return type;
    }
    public Player(PlayerType type, boolean turn){
        this.type = type;
        this.turn = turn;
    }
    public boolean getTurn(){
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
