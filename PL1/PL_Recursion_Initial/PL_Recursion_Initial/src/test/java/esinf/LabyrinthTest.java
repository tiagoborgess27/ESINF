package esinf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LabyrinthTest {

    @Test
    public void testCheckWhenStartIsExit() {
        int[][] actual = {{1}};

        int[][] result = Labyrinth.check(actual, 0, 0);

        assertArrayEquals(new int[][]{{9}}, result);
    }

    @Test
    public void testCheckReturnsNullWhenStartIsBlocked() {
        int[][] actual = {{0}};

        int[][] result = Labyrinth.check(actual, 0, 0);

        assertNull(result);
    }

    @Test
    public void testCheckReturnsNullWhenCoordinatesAreOutOfBounds() {
        int[][] actual = {{1, 1}, {1, 1}};

        assertNull(Labyrinth.check(actual, -1, 0));
        assertNull(Labyrinth.check(actual, 0, -1));
        assertNull(Labyrinth.check(actual, 2, 0));
        assertNull(Labyrinth.check(actual, 0, 2));
    }

    @Test
    public void testCheckWithDisconnectedMaze() {
        int[][] actual = {
            {1, 1, 1},
            {0, 0, 0},
            {1, 1, 1}
        };

        int[][] result = Labyrinth.check(actual, 0, 0);

        assertNull(result);
    }

    @Test
    public void testCheckFindsPath() {
        System.out.println("check");
        int[][] actual = {
                {1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int y = 0;
        int x = 0;
        int[][] expResult = {
                {9, 9, 9, 0, 2, 2, 0, 0, 0, 2, 2, 2, 2},
                {1, 0, 9, 9, 9, 0, 2, 2, 2, 2, 2, 0, 2},
                {1, 0, 0, 0, 9, 0, 2, 0, 2, 0, 2, 0, 2},
                {1, 0, 0, 0, 9, 2, 2, 0, 2, 0, 2, 2, 2},
                {1, 1, 1, 1, 9, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9}
        };

        int[][] result = Labyrinth.check(actual, y, x);

        assertArrayEquals(expResult, result);
    }

    @Test
    public void testCheckReturnsNullWhenNoPathExists() {
        int[][] impossibleActual = {
                {1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] result = Labyrinth.check(impossibleActual, 0, 0);

        assertNull(result);
    }
}
