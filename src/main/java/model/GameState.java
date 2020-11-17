package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    public Cell[][] board = new Cell[8][8];
    public Color previousMoveColor = Color.BLACK;
    public int moveCount = 0;

    public void getBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color;
                if ((i + j) % 2 == 0) color = Color.BEIGE;
                else color = Color.BROWN;
                board[i][j] = new Cell(i, j, color);
                Checker checker = null;
                board[i][j].setChecker(null);
                if (j < 3 && board[i][j].color == Color.BROWN) {
                    checker = new Checker(i, j, Color.BLACK, 0, false);
                }
                if (j > 4 && board[i][j].color == Color.BROWN) {
                    checker = new Checker(i, j, Color.WHITE, 0, false);
                }
                if (checker != null) {
                    board[i][j].setChecker(checker);
                }
            }
        }
        moveCount = 0;
    }

    public int canMove(int newX, int newY, Checker checker) {
        if (checker.color == previousMoveColor) return 0;
        if (!cellExist(newX, newY) || board[newX][newY].hasChecker() || (newX + newY) % 2 != 1) return 0;
        else {
            int nowX = (int) Math.floor(checker.getOldX() / 100); // what is 100
            int nowY = (int) Math.floor(checker.getOldY() / 100);
            if (!checker.isDamka) {
                if (checker.color == Color.BLACK && Math.abs(newX - nowX) == 1 && newY - nowY == 1 || // what is this
                        checker.color == Color.WHITE && Math.abs(newX - nowX) == 1 && newY - nowY == -1) {
                    return 1;
                }
                int evilX = (newX + nowX) / 2;
                int evilY = (newY + nowY) / 2;
                if (board[evilX][evilY].hasChecker() && board[evilX][evilY].getChecker().color != checker.color &&
                        Math.abs(newX - nowX) == 2 && Math.abs(newY - nowY) == 2) { // what

                    return 2;
                }
            } else {
                int lx = (newX - nowX) / Math.abs(newX - nowX);
                int ly = (newY - nowY) / Math.abs(newY - nowY);
                int xx = nowX + lx;
                int yy = nowY + ly;
                int countChecker = 0;
                while (xx != newX && yy != newY) {
                    if (board[xx][yy].hasChecker() && board[xx][yy].getChecker().color == checker.color) return 0;
                    if (board[xx][yy].hasChecker() && board[xx][yy].getChecker().color != checker.color)
                        countChecker += 1;
                    xx += lx;
                    yy += ly;
                }
                if (countChecker == 0) return 1;
                if (countChecker == 1) return 2;
            }
        }
        return 0;
    }

    public void makeMove(int newX, int newY, Checker checker) {
        moveCount++;
        int moveResult;
        if (newX < 0 || newY < 0 || newX > 7 || newY > 7) moveResult = 0; // размер поля
        else moveResult = canMove(newX, newY, checker);

        if ((needtobyteforWhite() && checker.color == Color.WHITE || needtobyteforBlack()
                && checker.color == Color.BLACK) && moveResult != 2) {
            moveResult = 0;
        }

        int nowX = (int) Math.floor(checker.getOldX() / 100);
        int nowY = (int) Math.floor(checker.getOldY() / 100);

        if (moveResult != 0) {
            checker.go(newX, newY);
            board[nowX][nowY].setChecker(null);
            board[newX][newY].setChecker(checker);
            if (moveResult == 1) {
                if (checker.color == Color.BLACK && newY == 7) checker.isDamka = true;
                if (checker.color == Color.WHITE && newY == 0) checker.isDamka = true;
                previousMoveColor = checker.color;

            } else if (moveResult == 2) {
                if (!checker.isDamka) {
                    int evilX = (newX + nowX) / 2;
                    int evilY = (newY + nowY) / 2;
                    board[evilX][evilY].setChecker(null);

                } else {
                    int lx = (newX - nowX) / Math.abs(newX - nowX);
                    int ly = (newY - nowY) / Math.abs(newY - nowY);
                    int xx = nowX + lx;
                    int yy = nowY + ly;
                    while (!board[xx][yy].hasChecker()) {
                        xx += lx;
                        yy += ly;
                    }
                    board[xx][yy].setChecker(null);
                }
                if (!canByte(board[newX][newY])) previousMoveColor = checker.color;

                if (checker.color == Color.BLACK && newY == 7) checker.isDamka = true;
                if (checker.color == Color.WHITE && newY == 0) checker.isDamka = true;

                if (gameover().equals("White won") || gameover().equals("Black won")) {
                    getBoard();
                    previousMoveColor = Color.BLACK;
                }
            }

            if (checker.color == Color.BLACK && newY == 7 || checker.color == Color.WHITE && newY == 0)
                checker.isDamka = true;

            if (gameover().equals("White won") || gameover().equals("Black won")) {
                getBoard();
                previousMoveColor = Color.BLACK;
            }
        }
        checker.moveType = moveResult;
    }

    public String gameover() {
        int black = 0;
        int white = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.BLACK) black += 1;
                if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.WHITE) white += 1;
            }
        }
        if (black == 0) {
            return "White won";
        }
        if (white == 0) {
            return "Black won";
        }
        return "";
    }

    private boolean cellExist(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean canByte(Cell cell) {
        if (cell.hasChecker()) {
            int i = cell.x;
            int j = cell.y;
            if (!cell.getChecker().isDamka) {
                if (cellExist(i + 2, j + 2) && board[i + 1][j + 1].hasChecker() && !board[i + 2][j + 2].hasChecker()
                        && board[i + 1][j + 1].getChecker().color != cell.getChecker().color) return true;
                if (cellExist(i + 2, j - 2) && board[i + 1][j - 1].hasChecker() && !board[i + 2][j - 2].hasChecker()
                        && board[i + 1][j - 1].getChecker().color != cell.getChecker().color) return true;
                if (cellExist(i - 2, j + 2) && board[i - 1][j + 1].hasChecker() && !board[i - 2][j + 2].hasChecker()
                        && board[i - 1][j + 1].getChecker().color != cell.getChecker().color) return true;
                if (cellExist(i - 2, j - 2) && board[i - 1][j - 1].hasChecker() && !board[i - 2][j - 2].hasChecker()
                        && board[i - 1][j - 1].getChecker().color != cell.getChecker().color) return true;
            } else {
                int nowX = i;
                int nowY = j;
                while (nowX != 0 && nowY != 0) {
                    nowX--;
                    nowY--;
                    if (cellExist(nowX, nowY)) {
                        if (board[nowX][nowY].hasChecker()) {
                            if (board[nowX][nowY].getChecker().color == cell.getChecker().color) break;
                            if (board[nowX][nowY].getChecker().color != cell.getChecker().color &&
                                    cellExist(nowX - 1, nowY - 1) && !board[nowX - 1][nowY - 1].hasChecker())
                                return true;
                        }
                    }
                }

                nowX = i;
                nowY = j;
                while (nowX != 7 && nowY != 0) {
                    nowX++;
                    nowY--;
                    if (cellExist(nowX, nowY)) {
                        if (board[nowX][nowY].hasChecker()) {
                            if (board[nowX][nowY].getChecker().color == cell.getChecker().color) break;
                            if (board[nowX][nowY].getChecker().color != cell.getChecker().color &&
                                    cellExist(nowX + 1, nowY - 1) && !board[nowX + 1][nowY - 1].hasChecker())
                                return true;
                        }
                    }
                }

                nowX = i;
                nowY = j;
                while (nowX != 7 && nowY != 7) {
                    nowX++;
                    nowY++;
                    if (cellExist(nowX, nowY)) {
                        if (board[nowX][nowY].hasChecker()) {
                            if (board[nowX][nowY].getChecker().color == cell.getChecker().color) break;
                            if (board[nowX][nowY].getChecker().color != cell.getChecker().color &&
                                    cellExist(nowX + 1, nowY + 1) && !board[nowX + 1][nowY + 1].hasChecker())
                                return true;
                        }
                    }
                }

                nowX = i;
                nowY = j;
                while (nowX != 0 && nowY != 7) {
                    nowX--;
                    nowY++;
                    if (cellExist(nowX, nowY)) {
                        if (board[nowX][nowY].hasChecker()) {
                            if (board[nowX][nowY].getChecker().color == cell.getChecker().color) break;
                            if (board[nowX][nowY].getChecker().color != cell.getChecker().color &&
                                    cellExist(nowX - 1, nowY + 1) && !board[nowX - 1][nowY + 1].hasChecker())
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean needtobyteforWhite() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.WHITE && canByte(board[i][j]))
                    return true;
            }
        }
        return false;
    }

    public boolean needtobyteforBlack() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.BLACK && canByte(board[i][j]))
                    return true;
            }
        }
        return false;
    }

    int maxDepth = 2;

    //разделить надо
    public void makeIImove(Color color) {
        Color color1 = previousMoveColor;
        while (previousMoveColor == color1) {
            Integer[] minimax = minimax(0, color);
            System.out.println(minimax[0]);
            System.out.println(minimax[1]);
            System.out.println(minimax[2]);
            System.out.println(minimax[3]);
            makeMove(minimax[2], minimax[3], board[minimax[0]][minimax[1]].getChecker());
        }
    }

    public void undoMove(int oldx, int oldy, int newx, int newy, Color color) {
        board[newx][newy].setChecker(null);
        board[oldx][oldy].setChecker(new Checker(oldx, oldy, color, 0, false));

        if (Math.abs(oldx - newx) == 2 && Math.abs(oldy - newy) == 2) {
            int evilX = (newx + oldx) / 2;
            int evilY = (newy + oldy) / 2;
            Color evilColor = Color.BLACK;
            if (color == Color.BLACK) evilColor = Color.WHITE;
            board[evilX][evilY].setChecker(new Checker(evilX, evilY, evilColor, 0, false));
        }

        if (color == Color.BLACK) previousMoveColor = Color.WHITE;
        else previousMoveColor = Color.BLACK;
        moveCount--;
    }

    //минимакс
    public Integer[] minimax(int depth, Color color) {
        Integer[] currentDepth = new Integer[]{0, 0, 0, 0, 0};

        int evaluation = getEvaluation();
        if (evaluation > 950) {
            currentDepth[4] = 950;
            return currentDepth;
        }
        if (evaluation < -950) {
            currentDepth[4] = -950;
            return currentDepth;
        }
        if (color == Color.BLACK) currentDepth[4] = Integer.MAX_VALUE;
        else currentDepth[4] = Integer.MIN_VALUE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (color == Color.BLACK) {
                    if (needtobyteforBlack()) {
                        if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.BLACK && canByte(board[i][j])) {
                            if (board[i][j].getChecker().isDamka) {

                            } else {
                                if (canMove(i - 2, j - 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i - 2, j - 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 2, j - 2, Color.BLACK);
                                }
                                if (canMove(i + 2, j + 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i + 2, j + 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 2, j + 2, Color.BLACK);
                                }
                                if (canMove(i - 2, j + 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i - 2, j + 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 2, j + 2, Color.BLACK);
                                }
                                if (canMove(i + 2, j - 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i + 2, j - 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 2, j - 2, Color.BLACK);
                                }
                            }
                        }
                    } else {
                        if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.BLACK) {
                            if (board[i][j].getChecker().isDamka) {

                            } else {
                                if (canMove(i + 1, j + 1, board[i][j].getChecker()) == 1) {
                                    makeMove(i + 1, j + 1, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 1;
                                            currentDepth[3] = j + 1;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 1;
                                            currentDepth[3] = j + 1;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 1, j + 1, Color.BLACK);
                                }
                                if (canMove(i + 1, j - 1, board[i][j].getChecker()) == 1) {
                                    makeMove(i + 1, j - 1, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.WHITE);
                                        if (score[4] <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 1;
                                            currentDepth[3] = j - 1;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score <= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 1;
                                            currentDepth[3] = j - 1;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 1, j - 1, Color.BLACK);
                                }
                            }
                        }
                    }
                } else if (color == Color.WHITE) {
                    if (needtobyteforWhite()) {
                        if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.WHITE && canByte(board[i][j])) {
                            if (board[i][j].getChecker().isDamka) {

                            } else {
                                if (canMove(i - 2, j - 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i - 2, j - 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 2, j - 2, Color.WHITE);
                                }
                                if (canMove(i + 2, j + 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i + 2, j + 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 2, j + 2, Color.WHITE);
                                }
                                if (canMove(i - 2, j + 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i - 2, j + 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 2;
                                            currentDepth[3] = j + 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 2, j + 2, Color.WHITE);
                                }
                                if (canMove(i + 2, j - 2, board[i][j].getChecker()) == 2) {
                                    makeMove(i + 2, j - 2, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i + 2;
                                            currentDepth[3] = j - 2;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i + 2, j - 2, Color.WHITE);
                                }
                            }
                        }
                    } else {
                        if (board[i][j].hasChecker() && board[i][j].getChecker().color == Color.WHITE) {
                            if (board[i][j].getChecker().isDamka) {

                            } else {
                                if (canMove(i - 1, j + 1, board[i][j].getChecker()) == 1) {
                                    makeMove(i - 1, j + 1, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 1;
                                            currentDepth[3] = j + 1;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 1;
                                            currentDepth[3] = j + 1;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 1, j + 1, Color.WHITE);
                                }
                                if (canMove(i - 1, j - 1, board[i][j].getChecker()) == 1) {
                                    makeMove(i - 1, j - 1, board[i][j].getChecker());
                                    if (depth != maxDepth) {
                                        Integer[] score = minimax(depth + 1, Color.BLACK);
                                        if (score[4] >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 1;
                                            currentDepth[3] = j - 1;
                                            currentDepth[4] = score[4];
                                        }
                                    } else {
                                        int score = getEvaluation();
                                        if (score >= currentDepth[4]) {
                                            currentDepth[0] = i;
                                            currentDepth[1] = j;
                                            currentDepth[2] = i - 1;
                                            currentDepth[3] = j - 1;
                                            currentDepth[4] = score;
                                        }
                                    }
                                    undoMove(i, j, i - 1, j - 1, Color.WHITE);
                                }
                            }
                        }
                    }
                }
            }
        }
        return currentDepth;
    }

    //оценочная функция
    public int getEvaluation() {
        int whiteCount = 0;
        int blackCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasChecker()) {
                    Checker checker = board[i][j].getChecker();
                    if (board[i][j].getChecker().color == Color.BLACK) {
                        if (checker.isDamka) blackCount += 3;
                        else blackCount++;
                    }
                    if (board[i][j].getChecker().color == Color.WHITE) {
                        if (checker.isDamka) whiteCount += 3;
                        else whiteCount++;
                    }
                }
            }
        }
        if (previousMoveColor == Color.BLACK && needtobyteforWhite()) blackCount--;
        else if (previousMoveColor == Color.WHITE && needtobyteforBlack()) whiteCount--;
        if (whiteCount == 0) blackCount += 1000;
        if (blackCount == 0) whiteCount += 1000;
        return whiteCount - blackCount;
    }
}