package checkers;

public class Player {
    private PlayerType type;
    private PieceType pieceType;
    private boolean turn;

    public PlayerType getType(){
        return type;
    }
    public Player(PlayerType type, PieceType pieceType, boolean turn){
        this.type = type;
        this.pieceType = pieceType;
        this.turn = turn;
    }
    public boolean getTurn(){
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
