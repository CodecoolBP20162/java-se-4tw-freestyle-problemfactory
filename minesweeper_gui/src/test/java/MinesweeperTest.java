import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperTest {

    Minesweeper minesweeper = new Minesweeper();

    @Test
    public void testGenerateMap() {
        assertNotNull(minesweeper.generateMap(10));
    }

    @Test
    public void testGenerateMapLength() {
        assertEquals(10, minesweeper.generateMap(10).length);
    }

    @Test
    public void testCountMinesLengthEqualsGenerateMaxLength() {
        assertEquals(minesweeper.generateMap(10).length, minesweeper.countMines(minesweeper.generateMap(10)).length);
    }

    @Test
    public void isMineReturns0() {
        assertEquals(0, minesweeper.isMine(-1, 0, minesweeper.generateMap(10)));
    }

    @Test
    public void isMineReturns1() {
        char[][] testArray = new char[10][10];
        for(int i = 0; i < testArray.length; i++){
            for(int x = 0; x < testArray[i].length; x++) {
                testArray[i][x] = '*';
            }
        }
        assertEquals(1, minesweeper.isMine(0, 0, testArray));
    }

}