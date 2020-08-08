package game;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    private void difficultyPrompt() {
        System.out.println("Enter difficulty Level");
        System.out.println("Pres 0 for BEGINNER (9 * 9 Cells and 10 Mines)");
        System.out.println("Pres 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)");
        System.out.println("Pres 2 for ADVANCED (24 * 24 Cells and 99 Mines)");
    }

    public void startGame() {
        Difficulty difficulty = getDifficulty();
        Board board = new Board(difficulty);
        String currMoveStr;
        int moveX, moveY;
        boolean firstMove = true;
        boolean endOfGame = false;

        while (!endOfGame) {
            board.printBoard(endOfGame);
            System.out.print("Enter your move, (row, column) ");
            currMoveStr = scanner.nextLine().trim();
            String[] split = currMoveStr.split(" ");
            moveX = Integer.parseInt(split[0]);
            moveY = Integer.parseInt(split[1]);

            if (firstMove) {
                board.generateMines(moveX, moveY);
                firstMove = false;
            }

            if (board.isMine(moveX, moveY)) {
                endOfGame = true;;
                board.printBoard(endOfGame);
                System.out.println("You lost!");
            } else {
                int count = findCountOfAdjacentMines(board, moveX, moveY);

                if (count == 0) {
                    clearCells(moveX, moveY, board);
                } else {
                    board.setAtPosition(moveX, moveY, (char) (count + '0'));
                }
            }


            if(checkWin(board, difficulty)){
                endOfGame = true;
                board.printBoard(endOfGame);
                System.out.println("You win!");
            }
        }
    }


    private boolean checkWin(Board board, Difficulty difficulty) {
        int count = 0;
        for(int i = 0; i < board.getBoardSize(); i++){
            for(int j = 0; j < board.getBoardSize(); j++){
                if(board.get(i, j) == '-'){
                    count++;
                }
            }
        }

        return count == difficulty.getMines();
    }

    private void clearCells(int x, int y, Board board) {
        boolean[][] visited = new boolean[board.getBoardSize()][board.getBoardSize()];

        for (int i = 0; i < board.getBoardSize(); i++) {
            Arrays.fill(visited[i], false);
        }

        clearCells(x, y, board, visited);
    }

    private void clearCells(int x, int y, Board board, boolean[][] visited) {
        if (!board.inBound(x, y)) {
            return;
        }

        if (visited[x][y]) {
            return;
        }

        int count = findCountOfAdjacentMines(board, x, y);
        visited[x][y] = true;

        board.setAtPosition(x, y, 'C');

        if (count == 0) {

            int[][] possibleMoves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1},
                    {-1, 1}, {1, -1}, {1, 1}, {-1, -1}};

            clearAdjacentCells(board, x, y);

            for (int[] possibleMove : possibleMoves) {
                clearCells(x + possibleMove[0], y + possibleMove[1], board, visited);
            }

        } else {

            board.setAtPosition(x, y, (char) (count + '0'));
        }
    }

    private void clearAdjacentCells(Board board, int x, int y){
        int[][] possibleMoves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1},
                {-1, 1}, {1, -1}, {1, 1}, {-1, -1}};

        for (int[] possibleMove : possibleMoves) {
            if (board.inBound(x + possibleMove[0], y + possibleMove[1])) {
                //Mark cell as cleared
                if(board.get(x + possibleMove[0], y  + possibleMove[1]) < '0' ||
                        board.get(x + possibleMove[0], y  + possibleMove[1]) >  '9')
                board.setAtPosition(x + possibleMove[0],y + possibleMove[1], 'C');
            }
        }
    }

    private int findCountOfAdjacentMines(Board board, int row, int col) {
        int[][] possibleMoves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1},
                {-1, 1}, {1, -1}, {1, 1}, {-1, -1}};
        int count = 0;

        for (int[] possibleMove : possibleMoves) {
            if (board.inBound(row + possibleMove[0], col + possibleMove[1]) &&
                    board.isMine(row + possibleMove[0], col + possibleMove[1])) {
                count++;
            }
        }

        return count;
    }

    private Difficulty getDifficulty() {
        difficultyPrompt();
        int level;
        Difficulty difficulty;

        do {
            level = Integer.parseInt(scanner.nextLine());

            if (level >= 3 || level < 0) {
                System.out.println("Wrong level!!\nTry again");
            }
        } while (level < 0 || level >= 3);

        if (level == 0) {
            difficulty = Difficulty.BEGINNER;
        } else if (level == 1) {
            difficulty = Difficulty.INTERMEDIATE;
        } else {
            difficulty = Difficulty.ADVANCE;
        }

        return difficulty;
    }
}
