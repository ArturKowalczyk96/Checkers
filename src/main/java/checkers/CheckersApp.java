package checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    Player playerW = new Player(PlayerType.WHITE, false);
    Player playerR = new Player(PlayerType.RED, false);

    MoveCounter moveCounter = new MoveCounter(0);

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        //add pieces
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());
        System.out.println(moveCounter);

        //win condition
        if (pieceGroup.getChildren().contains(piece.getType().WHITE)){
            System.out.println("Red Win!");
        }else if (pieceGroup.getChildren().contains(piece.getType().RED)){
            System.out.println("White Win!");
        }

        //whose turn
        if (moveCounter.getMoveCounter() % 2 == 0) {
            playerW.setTurn(true);
            playerR.setTurn(false);
        } else if (moveCounter.getMoveCounter() % 2 != 0) {
            playerW.setTurn(false);
            playerR.setTurn(true);
        }

        //moves
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        if (playerW.getTurn() == false && board[x0][y0].getPiece().getType() == piece.getType().WHITE){
            return new MoveResult(MoveType.NONE);
        }
        if (playerW.getTurn() == false && board[x0][y0].getPiece().getType() == piece.getType().WHITE_QUIN){
            return new MoveResult(MoveType.NONE);
        }
        if (playerR.getTurn() == false && board[x0][y0].getPiece().getType() == piece.getType().RED){
            return new MoveResult(MoveType.NONE);
        }
        if (playerR.getTurn() == false && board[x0][y0].getPiece().getType() == piece.getType().RED_QUIN){
            return new MoveResult(MoveType.NONE);
        }

        int x1 = x0 + (newX - x0) / 2;
        int y1 = y0 + (newY - y0) / 2;

        if (piece.getType() == PieceType.RED || piece.getType() == PieceType.WHITE) {
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir && newY != 0 && newY != 7) {
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }
            } else if (Math.abs(newX - x0) == 2 && newY - y0 == -piece.getType().moveDir * 2) {
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }
            }

            if (newY == 7 && piece.getType() == PieceType.RED){
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.TRANSFORM);
            }

            if (newY == 0 && piece.getType() == PieceType.WHITE){
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.TRANSFORM);
            }
        }
        if (piece.getType() == PieceType.RED_QUIN || piece.getType() == PieceType.WHITE_QUIN) {
            if (newX + newY == x0 + y0) {
                if (x0 > newX) {
                    for (int i = x0 - 1; i > newX; i--) {
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                } else if (x0 < newX) {
                    for (int i = x0 + 1; i < newX; i++) {
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.NORMAL);
            } else if (newX - newY == x0 - y0) {
                if (x0 > newX) {
                    for (int i = x0 - 1; i > newX; i--) {
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                } else if (x0 < newX) {
                    for (int i = x0 + 1; i < newX; i++) {
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.NORMAL);
            }
        }

        /*if (piece.getType() == PieceType.RED_QUIN || piece.getType() == PieceType.WHITE_QUIN){
            if (newX + newY == x0 + y0){
                if (x0 > newX){
                    for (int i = x0 - 1; i > newX; i--){
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece() && board[i][j].getPiece().getType() != piece.getType()) {
                            int k = i - 1;
                            int l = j + 1;
                            if (board[k][l].hasPiece()) {
                                return new MoveResult(MoveType.NONE);
                            }
                            moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                            return new MoveResult(MoveType.KILL, board[i][j].getPiece());
                        }else if (board[i][j].hasPiece() && board[i][j].getPiece().getType() == piece.getType()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }else if(x0 < newX){
                    for (int i = x0 + 1; i < newX; i++){
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece() && board[i][j].getPiece().getType() != piece.getType()) {
                            int k = i + 1;
                            int l = j - 1;
                            if (board[k][l].hasPiece()) {
                                return new MoveResult(MoveType.NONE);
                            }
                            moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                            return new MoveResult(MoveType.KILL, board[i][j].getPiece());
                        }else if (board[i][j].hasPiece() && board[i][j].getPiece().getType() == piece.getType()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.NORMAL);

            }else if(newX - newY == x0 - y0){
                if (x0 > newX){
                    for (int i = x0 - 1; i > newX; i--){
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece() && board[i][j].getPiece().getType() != piece.getType()) {
                            int k = i + 1;
                            int l = j + 1;
                            if (board[k][l].hasPiece()) {
                                return new MoveResult(MoveType.NONE);
                            }
                            moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                            return new MoveResult(MoveType.KILL, board[i][j].getPiece());
                        }else if (board[i][j].hasPiece() && board[i][j].getPiece().getType() == piece.getType()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }else if(x0 < newX){
                    for (int i = x0 + 1; i < newX; i++){
                        int j = x0 + y0 - i;
                        if (board[i][j].hasPiece() && board[i][j].getPiece().getType() != piece.getType()) {
                            int k = i - 1;
                            int l = j - 1;
                            if (board[k][l].hasPiece()) {
                                return new MoveResult(MoveType.NONE);
                            }
                            moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                            return new MoveResult(MoveType.KILL, board[i][j].getPiece());
                        }else if (board[i][j].hasPiece() && board[i][j].getPiece().getType() == piece.getType()) {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }
                moveCounter.setMoveCounter(moveCounter.getMoveCounter() + 1);
                return new MoveResult(MoveType.NORMAL);
            }
        }*/
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;

                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;

                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;

                case TRANSFORM:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    if (piece.getType() == PieceType.RED){
                        piece.setType(PieceType.RED_QUIN);
                    }else if (piece.getType() == PieceType.WHITE){
                        piece.setType(PieceType.WHITE_QUIN);
                    }
                    board[newX][newY].setPiece(piece);
                    System.out.println("Tramsform");
                    break;
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}