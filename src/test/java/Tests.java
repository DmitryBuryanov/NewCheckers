import model.Checker;
import model.Color;
import model.GameState;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    public void test() {

        GameState gameState = new GameState();

        gameState.getBoard();

        gameState.previousMoveColor = Color.WHITE;

        gameState.board[7][6].setChecker(new Checker(7, 6, Color.WHITE, 0, false));
        gameState.board[6][5].setChecker(new Checker(6, 5, Color.BLACK, 0, false));
        gameState.board[5][4].setChecker(new Checker(5, 4, Color.BLACK, 0, false));
        gameState.board[6][3].setChecker(new Checker(6, 3, Color.BLACK, 0, false));
        gameState.board[5][2].setChecker(new Checker(5, 2, Color.BLACK, 0, false));
        gameState.board[7][0].setChecker(new Checker(7, 0, Color.BLACK, 0, false));
        gameState.board[4][1].setChecker(new Checker(4, 1, Color.BLACK, 0, false));
        gameState.board[1][0].setChecker(new Checker(1, 0, Color.BLACK, 0, false));
        gameState.board[2][1].setChecker(new Checker(2, 1, Color.BLACK, 0, false));
        gameState.board[1][2].setChecker(new Checker(1, 2, Color.BLACK, 0, false));
        gameState.board[0][3].setChecker(new Checker(0, 3, Color.WHITE, 0, false));
        gameState.board[0][3].setChecker(new Checker(0, 3, Color.WHITE, 0, false));
        gameState.board[0][5].setChecker(new Checker(0, 5, Color.WHITE, 0, false));
        gameState.board[0][7].setChecker(new Checker(0, 7, Color.WHITE, 0, false));
        gameState.board[1][4].setChecker(new Checker(1, 4, Color.WHITE, 0, false));
        gameState.board[1][6].setChecker(new Checker(1, 6, Color.WHITE, 0, false));
        gameState.board[2][5].setChecker(new Checker(2, 5, Color.WHITE, 0, false));
        gameState.board[2][7].setChecker(new Checker(2, 7, Color.WHITE, 0, false));

        gameState.makeIImove(Color.BLACK);

        /*Cell[][] oldboard = gameState.copyBoard();

        gameState.makeMove(1, 4, gameState.board[0][5].getChecker());
        assertTrue(gameState.board[1][4].hasChecker());

        gameState.makeOldBoard(oldboard);*/

        /*gameState.board[1][0].setChecker(new Checker(1, 0, Color.WHITE, 0, true));
        gameState.board[4][3].setChecker(new Checker(4, 3, Color.BLACK, 0, false));
        gameState.board[5][4].setChecker(new Checker(5, 4, Color.BLACK, 0, false));

        boolean tr = gameState.needtobyteforWhite();
        System.out.println(tr);*/
        assertTrue(gameState.previousMoveColor == Color.BLACK);
        //тестирование правильного заполнения доски в начале игры
        /*assertTrue(
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

        /*gameState.makeMove(1, 4, gameState.board[0][5].getChecker());
        assertTrue(!gameState.board[0][5].hasChecker() && gameState.board[1][4].hasChecker());

        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(7, 4, gameState.board[6][5].getChecker());
        assertTrue(!gameState.board[6][5].hasChecker() && gameState.board[7][4].hasChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(6, 5, gameState.board[5][6].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(5, 6, gameState.board[4][7].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(6, 3, gameState.board[7][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(5, 4, gameState.board[4][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(4, 5, gameState.board[3][6].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 6, gameState.board[2][7].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 4, gameState.board[2][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(2, 5, gameState.board[0][7].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(1, 2, gameState.board[3][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 2, gameState.board[5][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(5, 4, gameState.board[4][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(4, 5, gameState.board[3][6].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 4, gameState.board[2][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(1, 2, gameState.board[3][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(0, 1, gameState.board[1][2].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(1, 0, gameState.board[0][1].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(4, 3, gameState.board[1][0].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 2, gameState.board[4][3].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 4, gameState.board[4][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(2, 3, gameState.board[3][2].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 4, gameState.board[5][6].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(5, 4, gameState.board[6][5].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(6, 3, gameState.board[5][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(1, 2, gameState.board[2][3].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 0, gameState.board[1][2].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(6, 5, gameState.board[7][6].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(7, 4, gameState.board[3][0].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(5, 6, gameState.board[6][7].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 0, gameState.board[7][4].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(1, 2, gameState.board[3][0].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(3, 4, gameState.board[1][2].getChecker());
        gameState.makeIImove(Color.BLACK);

        gameState.makeMove(4, 3, gameState.board[3][4].getChecker());
        gameState.makeIImove(Color.BLACK);

         */
        //тестирование хождения на белую клетку
        /*gameState.makeMove(1, 5, gameState.board[0][5].getChecker());
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
                !gameState.board[1][4].hasChecker());*/
    }
}
