import model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    public void test() {

        GameState gameState = new GameState();

        gameState.getBoard();

        //тестирование правильного заполнения доски в начале игры
        assertTrue(
                //белые шашки
                gameState.board[0][7].hasChecker() && gameState.board[2][7].hasChecker() &&
                        gameState.board[4][7].hasChecker() && gameState.board[6][7].hasChecker() &&
                        gameState.board[1][6].hasChecker() && gameState.board[3][6].hasChecker() &&
                        gameState.board[5][6].hasChecker() && gameState.board[7][6].hasChecker() &&
                        gameState.board[0][5].hasChecker() && gameState.board[2][5].hasChecker() &&
                        gameState.board[4][5].hasChecker() && gameState.board[6][5].hasChecker() &&

                        //черные шашки
                        gameState.board[1][0].hasChecker() && gameState.board[3][0].hasChecker() &&
                        gameState.board[5][0].hasChecker() && gameState.board[7][0].hasChecker() &&
                        gameState.board[0][1].hasChecker() && gameState.board[2][1].hasChecker() &&
                        gameState.board[4][1].hasChecker() && gameState.board[6][1].hasChecker() &&
                        gameState.board[1][2].hasChecker() && gameState.board[3][2].hasChecker() &&
                        gameState.board[5][2].hasChecker() && gameState.board[7][2].hasChecker());

        //тестирование хождения на белую клетку
        gameState.makeMove(1, 5, gameState.board[0][5].getChecker());
        assertTrue(!gameState.board[1][5].hasChecker() && gameState.board[0][5].hasChecker());

        //тестирование хода белой шашки
        gameState.makeMove(1, 4, gameState.board[0][5].getChecker());
        assertTrue(gameState.board[1][4].hasChecker());

        //тестирование хода шашки одного цвета 2 раза подряд
        gameState.makeMove(2, 3, gameState.board[1][4].getChecker());
        assertTrue(!gameState.board[2][3].hasChecker() && gameState.board[1][4].hasChecker());

        // тестирование хода черной шашки
        gameState.makeMove(2, 3, gameState.board[1][2].getChecker());
        assertTrue(gameState.board[2][3].hasChecker());

        //ход белой шашки
        gameState.makeMove(3, 4, gameState.board[2][5].getChecker());

        //тестирование того, как черная шашка побила белую
        gameState.makeMove(0, 5, gameState.board[2][3].getChecker());
        assertTrue(gameState.board[0][5].hasChecker() && !gameState.board[2][3].hasChecker() &&
                !gameState.board[1][4].hasChecker());
    }
}
