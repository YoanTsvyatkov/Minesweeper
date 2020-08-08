package tests;

import game.Board;
import game.Difficulty;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestJunit {

    @Test
    public void testGeneration() {
        Board board = new Board(Difficulty.BEGINNER);
        Random random = new Random();
        int randX, randY;

        for (int i = 0; i < 3; i++) {
            randX = random.nextInt(board.getBoardSize());
            randY = random.nextInt(board.getBoardSize());
            board.generateMines(randX, randY);
            assertFalse(board.isMine(randX, randY));
        }
    }

    @Test
    public void testNumberOfMinesBeginner() {
        Board board = new Board(Difficulty.BEGINNER);
        board.generateMines(0, 0);
        assertEquals(Difficulty.BEGINNER.getMines(),board.findNumberOfMines());
    }


    @Test
    public void testNumberOfMinesIntermediate() {
        Board board = new Board(Difficulty.INTERMEDIATE);
        board.generateMines(0, 0);
        assertEquals(Difficulty.INTERMEDIATE.getMines(),board.findNumberOfMines());
    }

    @Test
    public void testNumberOfMinesAdvanced() {
        Board board = new Board(Difficulty.ADVANCE);
        board.generateMines(0, 0);
        assertEquals(Difficulty.ADVANCE.getMines(),board.findNumberOfMines());
    }

}
