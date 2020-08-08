package game;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private char[][] board;
    private boolean[][] mines;
    private Difficulty difficulty;
    private final int boardSize;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.boardSize = getBoardSizeByDifficulty();

        board = new char[boardSize][boardSize];
        mines = new boolean[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            Arrays.fill(board[i], '-');
        }

        for (int i = 0; i < boardSize; i++) {
            Arrays.fill(mines[i], false);
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isMine(int x, int y){
        return mines[x][y];
    }

    public char get(int x, int y){
        return board[x][y];
    }

    public boolean inBound(int x, int y){
        return x >= 0 && x < boardSize &&
                y >= 0 && y < boardSize;
    }

    public void setAtPosition(int x, int y, char ch){
        board[x][y] = ch;
    }

    public void printBoard(boolean endOfGame) {
        System.out.println("->");
        System.out.println("Current Status of Board :");

        for (int i = 0; i < boardSize; i++) {
            if (i == 0) {
                System.out.printf("%6d", i);
            }else {
                if (i != boardSize - 1) {
                    System.out.printf("%3d", i);
                } else {
                    System.out.printf("%3d\n", i);
                }
            }
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (j == 0) {
                    System.out.printf("%-5d", i);
                }

                if(endOfGame && isMine(i, j)){
                    if (j != boardSize - 1) {
                        System.out.printf("%-3s", '*');
                    } else {
                        System.out.println("*");
                    }
                }else {
                    if (j != boardSize - 1) {
                        System.out.printf("%-3s", board[i][j]);
                    } else {
                        System.out.println(board[i][j]);
                    }
                }
            }
        }

    }

    private int getBoardSizeByDifficulty(){
        if (difficulty == Difficulty.BEGINNER) {
            return 9;
        } else if (difficulty == Difficulty.INTERMEDIATE) {
            return 16;
        } else{
            return 24;
        }
    }

    public int getBoardSize(){
        return boardSize;
    }

    public void generateMines(int startX, int startY) {
        fillMinesByNumber(difficulty.getMines(), startX, startY);
    }

    private void fillMinesByNumber(int n, int startX, int startY) {
        Random random = new Random();
        int mineX, mineY;

        while (n > 0) {
            mineX = random.nextInt(boardSize);
            mineY = random.nextInt(boardSize);

            if (!mines[mineX][mineY] && (mineX != startX || mineY != startY)) {
                mines[mineX][mineY] = true;
                n--;
            }
        }
    }

    public int findNumberOfMines(){
        int count = 0;

        for (boolean[] mine : mines) {
            for (boolean b : mine) {
                if(b){
                    count++;
                }
            }
        }

        return count;
    }

}
