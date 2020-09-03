package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Checker;
import model.GameState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainApp extends Application {

    GameState gameState = new GameState();
    private Group cells = new Group();
    private Group checkers = new Group();
    public static int size = 90;
    Pane root = new Pane();

    private Pane makeScreen() throws Exception {

        Image image =
                new Image(new FileInputStream(new File("src\\main\\resources\\checkers-436285.jpg").getAbsolutePath()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(8 * size);
        imageView.setFitHeight(8 * size);

        Text head = new Text("Welcome to Russian Checkers");
        head.setFont(new Font(size * 0.5));
        head.setFill(Color.WHITESMOKE);
        head.relocate(0.7 * size, 1);

        Text down = new Text("Game by Dmitry Buryanov");
        down.setFont(new Font(size * 0.2));
        down.setFill(Color.WHITESMOKE);
        down.relocate(0.7 * size, size * 7.5);

        Button but1 = new Button("Игра с другом");
        Button but2 = new Button("Игра с компьютером");
        but1.relocate(3.5 * size, 3.5 * size);
        but2.relocate(3.3 * size, 4 * size);
        root.setPrefSize(8 * size, 8 * size);

        root.getChildren().addAll(imageView, but1, but2, head, down);

        but1.setOnMouseClicked(e -> {
            try {
                gameState.getBoard();
                fillBoard();
                root.setPrefSize(8 * size, 8 * size);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Rectangle rectangle = createCell(i, j);
                        cells.getChildren().add(rectangle);
                    }
                }
                root.getChildren().addAll(cells, checkers);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return root;
    }

    private void fillBoard() throws FileNotFoundException {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Checker checker;
                if (gameState.board[i][j].hasChecker()) {
                    checker = gameState.board[i][j].getChecker();
                    CheckerModel checkerModel = createChecker(i, j, checker, checker.isDamka);
                    checkers.getChildren().add(checkerModel);
                }
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(makeScreen(), 8 * size, 8 * size);
        stage.setScene(scene);
        stage.setTitle("Checkers");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private Rectangle createCell(int i, int j) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(size);
        rectangle.setWidth(size);
        if (gameState.board[i][j].color == model.Color.BROWN) rectangle.setFill(Color.BROWN);
        else rectangle.setFill(Color.BEIGE);
        rectangle.relocate(i * size, j * size);
        return rectangle;
    }

    private CheckerModel createChecker(int i, int j, Checker checker, boolean isDamka) throws FileNotFoundException {
        CheckerModel checkerModel = new CheckerModel(i, j, checker, isDamka);
        checkerModel.setOnMouseReleased(e -> {
            int newX = (int) Math.floor(e.getSceneX() / size);
            int newY = (int) Math.floor(e.getSceneY() / size);

            if (gameState.previousMoveColor == model.Color.BLACK && gameState.needtobyteforWhite() &&
                    checker.color == model.Color.WHITE && gameState.canMove(newX, newY, checker) != 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Now you must byte black checker!");
                alert.showAndWait();
            }

            if (gameState.previousMoveColor == model.Color.WHITE && gameState.needtobyteforBlack() &&
                    checker.color == model.Color.BLACK && gameState.canMove(newX, newY, checker) != 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Now you must byte white checker!");
                alert.showAndWait();
            }

            gameState.makeMove(newX, newY, checker);

            if (gameState.moveCount == 0 && gameState.board[1][0].hasChecker() && gameState.board[3][0].hasChecker() &&
                    gameState.board[5][0].hasChecker() && gameState.board[7][0].hasChecker() &&
                    gameState.board[0][1].hasChecker() && gameState.board[2][1].hasChecker() &&
                    gameState.board[4][1].hasChecker() && gameState.board[6][1].hasChecker() &&
                    gameState.board[1][2].hasChecker() && gameState.board[3][2].hasChecker() &&
                    gameState.board[5][2].hasChecker() && gameState.board[7][2].hasChecker() &&
                    gameState.board[0][5].hasChecker() && gameState.board[2][5].hasChecker() &&
                    gameState.board[4][5].hasChecker() && gameState.board[6][5].hasChecker() &&
                    gameState.board[1][6].hasChecker() && gameState.board[3][6].hasChecker() &&
                    gameState.board[5][6].hasChecker() && gameState.board[7][6].hasChecker() &&
                    gameState.board[0][7].hasChecker() && gameState.board[2][7].hasChecker() &&
                    gameState.board[4][7].hasChecker() && gameState.board[6][7].hasChecker()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("We have winner");
                alert.setHeaderText(null);
                alert.setContentText("Game is over. Let's start again");
                alert.showAndWait();
                try {
                    root.getChildren().clear();
                    makeScreen();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            checkers.getChildren().clear();
            try {
                fillBoard();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return checkerModel;
    }

}
