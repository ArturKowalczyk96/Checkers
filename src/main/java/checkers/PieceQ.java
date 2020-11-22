package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import static checkers.CheckersApp.TILE_SIZE;

public class PieceQ extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private int oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public PieceQ(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipseQ = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        Line lineQ1 = new Line(TILE_SIZE*0.4, TILE_SIZE*0.5, TILE_SIZE*0.6, TILE_SIZE*0.5);
        Line lineQ2 = new Line(50, 40, 50, 60);
        ellipseQ.setFill(type == PieceType.RED_QUIN
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipseQ.setStroke(Color.BLACK);
        ellipseQ.setStrokeWidth(TILE_SIZE * 0.03);

        ellipseQ.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipseQ.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        lineQ1.setStroke(Color.BLACK);
        lineQ1.setStrokeWidth(TILE_SIZE*0.03);

        lineQ2.setStroke(Color.BLACK);
        lineQ2.setStrokeWidth(TILE_SIZE*0.03);

        getChildren().addAll(bg, ellipseQ, lineQ1, lineQ2);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
