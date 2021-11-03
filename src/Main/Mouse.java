package Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        Screen.p = e.getPoint();
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (Screen.pause)
            switch (Screen.buttonMenu) {
                case 0:
                    Screen.pause = false;
                    break;
                case 1:
                    System.out.println("Options");
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        else {
            if (e.getButton() == MouseEvent.BUTTON1)
                KeyBoard.X = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (!Screen.pause && e.getButton() == MouseEvent.BUTTON1) {
            Screen.fire = true;
            KeyBoard.X = false;
        }
    }
}
