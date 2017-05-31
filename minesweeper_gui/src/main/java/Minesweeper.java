import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Minesweeper {

    // Create new frame and set its size
    static JFrame window = new JFrame("Minesweeper");

    private static final Logger logger = LoggerFactory.getLogger(Minesweeper.class);

    public char[][] generateMap(int size) {

        Random rand = new Random();

        char[][] map = new char[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int x = 0; x < map[i].length; x++) {
                int randomNumber = rand.nextInt(5) + 1;
                if (randomNumber == 1) {
                    map[i][x] = '*';
                } else {
                    map[i][x] = '.';
                }
            }
        }
        logger.info("New map generated");
        return map;
    }


    public char[][] countMines(char[][] map) {
        char[][] newMap = new char[10][10];
        for (int i = 0; i < map.length; i++) {
            for (int x = 0; x < map[i].length; x++) {
                newMap[i][x] = map[i][x];
            }
        }
        for (int i = 0; i < newMap.length; i++) {
            for (int x = 0; x < newMap[i].length; x++) {
                if (newMap[i][x] == '*') {
                    continue;
                } else {
                    int counter = 0;
                    counter += isMine(i - 1, x - 1, newMap);
                    counter += isMine(i - 1, x, newMap);
                    counter += isMine(i - 1, x + 1, newMap);
                    counter += isMine(i, x - 1, newMap);
                    counter += isMine(i, x + 1, newMap);
                    counter += isMine(i + 1, x - 1, newMap);
                    counter += isMine(i + 1, x, newMap);
                    counter += isMine(i + 1, x + 1, newMap);
                    newMap[i][x] = Integer.toString(counter).charAt(0);
                }
            }
        }
        return newMap;
    }


    private int isMine(int i, int x, char[][] map) {
        if (i >= 0 && x >= 0 && i <= map.length - 1 && x <= map[0].length - 1 && map[i][x] == '*') {
            return 1;
        } else {
            logger.trace("Negative index");
            return 0;
        }
    }


    public void printMap(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int x = 0; x < map[i].length; x++) {
                System.out.print(map[i][x]);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        // char[][] map = game.generateMap(10);
        // game.printMap(game.countMines(map));
        game.startFrame();
    }

    public void startFrame() {

        removeComponents();

        // Set frames size
        window.setSize(600, 100);

        // Change background color
        window.getContentPane().setBackground(new Color(135, 168, 209));

        // when closing window the program stops automatically
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create new title
        JLabel title = new JLabel("Minesweeper");

        // Set title's position
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.TOP);


        JButton start = new JButton("Start");
        JButton help = new JButton("Help");
        JButton exit = new JButton("Exit :(");

        start.setBounds(0,0,50,50);
        help.setBounds(0,0,50,50);

        JPanel panel = new JPanel();
        panel.setSize(new Dimension(100,100));
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);



        start.setActionCommand("start");
        help.setActionCommand("help");
        exit.setActionCommand("exit");

        start.addActionListener(new ButtonClickListener());
        help.addActionListener(new ButtonClickListener());
        exit.addActionListener(new ButtonClickListener());

        // Hide borders around text inside buttons
        start.setFocusPainted(false);
        help.setFocusPainted(false);
        exit.setFocusPainted(false);

        start.setBackground(new Color(66, 244, 110));
        exit.setBackground(new Color(255, 43, 43));


        window.setLayout(new BorderLayout());

        panel.add(start);
        panel.add(help);
        panel.add(exit);

        panel.setBackground(new Color(100, 150, 200));

        window.add(title, BorderLayout.NORTH);
        window.add(panel, BorderLayout.CENTER);

        // Set frame to visible
        window.setVisible(true);
    }


    public void helpPanel() {

        removeComponents();
        window.setSize(600,100);
        JPanel helpPanel = new JPanel();
        helpPanel.setBackground(new Color(255, 180, 42));


        JLabel label = new JLabel();

        label.setText("<html><p align='center'>Minesweeper is a single-player puzzle video game.<br> The objective of the game is to clear a rectangular board containing<br> hidden \"mines\"or bombs without<br> detonating any of them, with help from clues about<br> the number of neighboring mines in each field.<br></p></html>");


        JButton backButton = new JButton("Back");

        backButton.setActionCommand("back");

        backButton.addActionListener(new ButtonClickListener());

        backButton.setFocusPainted(false);

        backButton.setBackground(new Color(66, 244, 110));

        helpPanel.add(label);
        helpPanel.add(backButton);

        window.add(helpPanel);

        window.setVisible(true);
    }

    public JPanel startGame(char[][] newMap) {
        //window.setVisible(false);
        window.setSize(600,550);
        removeComponents();

        JPanel mainPanel = new JPanel();
        Random rand = new Random();
        int red = rand.nextInt(255) + 0;
        int green = rand.nextInt(255) + 0;
        int blue = rand.nextInt(255) + 0;
        mainPanel.setBackground(new Color(red, green, blue));


        JPanel panel = drawPanel(newMap);
        mainPanel.add(panel);


        JButton newGame = new JButton("New game");
        newGame.setActionCommand("playAgain");
        newGame.addActionListener(new ButtonClickListener());
        newGame.setFocusPainted(false);
        newGame.setBackground(new Color(244, 212, 66));

        JPanel bottomButton = new JPanel();
        bottomButton.add(newGame);
        mainPanel.add(bottomButton);

        return mainPanel;
    }


    public JPanel drawPanel(char[][] newMap) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10));
        for (int row = 0; row < newMap.length; row++) {
            for (int col = 0; col < newMap[row].length; col++) {
                JButton b = new JButton(String.valueOf(newMap[row][col]));
                b.setPreferredSize(new Dimension(50, 50));
                b.setActionCommand("" + row + col);
                b.addActionListener(new ButtonClickListener());
                panel.add(b);
            }
        }
        logger.info("Minefield panel created");
        return panel;
    }

    public void drawTable(char[][] playerMap) {
        removeComponents();
        window.add(startGame(playerMap));
        window.setVisible(true);
        logger.info("Frame drawn");
    }



    public void gameEnded() {
        window.setVisible(false);
        removeComponents();
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,0,0));
        JLabel label = new JLabel();
        label.setText("<html><h2>Haha! You fool! There was a mine! Game over :)</h2></html>");
        JButton newGame = new JButton("Play again");
        JButton exit = new JButton("Exit :(");

        newGame.setActionCommand("playAgain");
        newGame.addActionListener(new ButtonClickListener());
        newGame.setFocusPainted(false);
        newGame.setBackground(new Color(66, 244, 110));


        exit.setActionCommand("exit");
        exit.addActionListener(new ButtonClickListener());
        exit.setFocusPainted(false);
        exit.setBackground(new Color(244, 212, 66));

        panel.add(label);
        panel.add(newGame);
        panel.add(exit);
        window.add(panel);
        window.setVisible(true);
    }


    public void removeComponents() {
        window.getContentPane().removeAll();
    }

}
