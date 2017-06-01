import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class ButtonClickListener implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(Minesweeper.class);
    static Minesweeper minesweeper = new Minesweeper();
    static char[][] map = minesweeper.generateMap(10);
    static char[][] newMap = minesweeper.countMines(map);
    static char[][] playerMap = new char[10][10];
    static Stack stack = new Stack();


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        for(int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (command.equals("" + row + col)) {
                    stack.push(new String("Game steps: User clicked on row " + row + " and col "+ col));
                    if(map[row][col] == '*') {
                        minesweeper.gameEnded();
                        logger.info("Mine was found.");
                        writeStackToConsole(stack);
                    }
                    else if(map[row][col] == '.') {
                        playerMap[row][col] = newMap[row][col];
                        minesweeper.drawTable(playerMap);
                        logger.info("User clicked.");
                    }
                }
            }
        }

        if (command.equals("start")) {
            minesweeper.drawTable(playerMap);
            logger.warn("Successfully game ending is not implemented!");
            logger.info("Game started");
        }
        else if (command.equals("help")) {
            minesweeper.helpPanel();
            logger.info("Help page opened.");
        }
        else if (command.equals("exit")) {
            logger.info("Exit button clicked.");
            System.exit(0);
        }
        else if (command.equals("back")) {
            minesweeper.startFrame();
            logger.info("Back button pressed.");
        }
        else if (command.equals("playAgain")) {
            logger.info("New game starting.");
            map = minesweeper.generateMap(10);
            newMap = minesweeper.countMines(map);
            playerMap = new char[10][10];
            minesweeper.drawTable(playerMap);
            minesweeper.printMap(map);
        }
    }

    public void writeStackToConsole(Stack stack){
        try{
            PrintWriter writer = new PrintWriter("steps.txt", "UTF-8");
            while(stack.empty() == false) {
                writer.println(stack.pop());
            }
            writer.close();
            logger.info("Steps added to 'steps.txt'!");
        }
        catch (IOException e) {
            logger.error("IOException occurred when tried to write stack to file!");
        }

    }
}