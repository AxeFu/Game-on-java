package Main;

import java.awt.*;
import javax.swing.*;

public class Start extends JFrame {public static void main(String[] args) {SwingUtilities.invokeLater(Start::new);}
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Start() {
        setSize(screenSize);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        add(new Screen(this));
        setVisible(true);
    }
}
