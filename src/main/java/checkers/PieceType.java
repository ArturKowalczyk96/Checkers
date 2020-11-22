package checkers;

public enum PieceType {
    RED(1), WHITE(-1),RED_QUIN(0), WHITE_QUIN(0);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}