package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;
    private PieceQ pieceQ;


    public boolean hasPiece() {
        return piece != null;
    }
    public boolean hasPieceQ() {
        return pieceQ != null;
    }

    public Piece getPiece() {
        return piece;
    }
    public PieceQ getPieceQ() {
        return pieceQ;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public void setPieceQ(PieceQ pieceQ) {
        this.pieceQ = pieceQ;
    }

     public Tile(boolean light, int x, int y) {
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);

        relocate(x * CheckersApp.TILE_SIZE, y * CheckersApp.TILE_SIZE);

        setStroke(Color.BLACK);
        setStrokeWidth(0.03 * CheckersApp.TILE_SIZE);
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
}
