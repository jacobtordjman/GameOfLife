package Ex5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
    public static void main(String[] args) {
        int size = 4;
        GOLMatrix tester = new GOLMatrix(size);
        tester.setWorld(new boolean[][]{{false, true, false, false}, {true, false, false, true}, {false, false, false, false}, {true, true, true, true}});
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.println(tester.getWorld()[i][j]);
            }
        }
        System.out.println("\n");
        tester.nextGeneration();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.print(tester.getWorld()[i][j]+"  ");
                j++;
                System.out.println(tester.getWorld()[i][j]);
            }
        }

        JFrame frame = new JFrame("Testing");
        JPanel game = new GOLPanel(25);
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);

    }
}
