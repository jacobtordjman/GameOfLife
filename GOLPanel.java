package Ex5;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GOLPanel represents the panel for the Game of Life simulation.
 * It extends JPanel and contains buttons, labels, and a grid of cells.
 */
public class GOLPanel extends JPanel {
    private JButton cmdGo, cmdFaster, cmdSlower, cmdNext, cmdClear;
    private GOLMatrix logic;
    private JLabel labels[][];
    private JLabel lblGenerations;
    private Timer timer;
    private TimerListener timerListener;
    private int interval;
    private MouseClickListener cellClickListener;
    private ButtonClickListener buttonClickListener;
    private final int WORLD_SIZE;
    private JPanel gPanel;

    /**
     * Constructs a GOLPanel with the specified world size.
     *
     * @param worldSize the size of the game world
     */
    public GOLPanel(int worldSize) {
        if (worldSize < 3)
            worldSize = 3;
        WORLD_SIZE = worldSize;
        logic = new GOLMatrix(WORLD_SIZE);
        interval = 200;
        timerListener = new TimerListener();
        timer = new Timer(interval, timerListener);
        labels = new JLabel[WORLD_SIZE][WORLD_SIZE];
        GridLayout gridLayout = new GridLayout(WORLD_SIZE, WORLD_SIZE);
        gPanel = new JPanel(gridLayout);
        gPanel.setBackground(Color.white);
        cellClickListener = new MouseClickListener();
        gPanel.addMouseListener(cellClickListener);
        buttonClickListener = new ButtonClickListener();
        lblGenerations = new JLabel("Number of Generations: " + logic.getGenerations());
        JPanel buttons = new JPanel();
        cmdGo = new JButton("Go");
        cmdGo.addActionListener(buttonClickListener);
        cmdNext = new JButton("Next");
        cmdNext.addActionListener(buttonClickListener);
        cmdClear = new JButton("Clear");
        cmdClear.addActionListener(buttonClickListener);
        cmdFaster = new JButton("Faster");
        cmdFaster.addActionListener(buttonClickListener);
        cmdSlower = new JButton("Slower");
        cmdSlower.addActionListener(buttonClickListener);
        cmdGo.setEnabled(true);
        cmdNext.setEnabled(true);
        cmdClear.setEnabled(true);
        cmdFaster.setEnabled(false);
        cmdSlower.setEnabled(false);
        buttons.add(cmdGo);
        buttons.add(cmdNext);
        buttons.add(cmdClear);
        buttons.add(cmdFaster);
        buttons.add(cmdSlower);
        buttons.add(lblGenerations);
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                JLabel label = new JLabel();
                label.setBorder(new LineBorder(Color.BLACK));
                label.setOpaque(true);
                gPanel.add(label);
                labels[i][j] = label;
            }
        }
        setLayout(new BorderLayout());
        add(buttons, BorderLayout.NORTH);
        add(gPanel, BorderLayout.CENTER);
    }


    /**
     * Updates the cell colors based on the current state of the game world.
     */
    public void updateCells() {
        boolean[][] temp = logic.getWorld();
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                if (temp[i][j])
                    labels[i][j].setBackground(Color.BLUE);
                else
                    labels[i][j].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Represents the mouse click listener for the cell grid.
     */
    private class MouseClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int coor_y = e.getX()*WORLD_SIZE / (gPanel.getWidth());
            int coor_x = e.getY()*WORLD_SIZE / (gPanel.getHeight());
            logic.flipCell(coor_x, coor_y);
            System.out.println(logic.getWorld()[coor_x][coor_y]);
            repaint();
        }
    }

    /**
     * Represents the button click listener for the control buttons.
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == cmdNext)
                logic.nextGeneration();
            else if (source == cmdClear)
                logic.clearWorld();
            else if (source == cmdSlower) {
                if (interval < 1000) {
                    interval += 20;
                    timer.setDelay(interval);
                }
            } else if (source == cmdFaster) {
                if (interval > 100) {
                    interval -= 20;
                    timer.setDelay(interval);
                }
            } else if (source == cmdGo) {
                if (cmdGo.getText().equals("Go")) {
                    timer.start();
                    cmdGo.setText("Stop");
                    cmdNext.setEnabled(false);
                    cmdClear.setEnabled(false);
                    cmdSlower.setEnabled(true);
                    cmdFaster.setEnabled(true);
                } else {
                    timer.stop();
                    cmdGo.setText("Go");
                    cmdNext.setEnabled(true);
                    cmdClear.setEnabled(true);
                    cmdSlower.setEnabled(false);
                    cmdFaster.setEnabled(false);
                }
            }
            repaint();
        }
    }

    /**
     * Represents the timer listener for advancing generations automatically.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logic.nextGeneration();
            repaint();
        }
    }

    /**
     * Overrides the paintComponent method to update the label showing the number of generations.
     *
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        lblGenerations.setText("Number of Generations: " + logic.getGenerations());
        updateCells();
    }
}
