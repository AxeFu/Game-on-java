package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyBoard extends KeyAdapter {

    public static boolean UP = false, DOWN = false, RIGHT = false, LEFT = false, X = false;

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) Screen.pause = true;
        if (e.getKeyCode() == KeyEvent.VK_X) X = true;

        if (e.getKeyCode() == KeyEvent.VK_UP) {UP = true; Screen.rotate = 0;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {LEFT = true; Screen.rotate = 3;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {RIGHT = true; Screen.rotate = 1;}
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {DOWN = true; Screen.rotate = 2;}

        if (e.getKeyCode() == KeyEvent.VK_W) {UP = true; Screen.rotate = 0;}
        if (e.getKeyCode() == KeyEvent.VK_A) {LEFT = true; Screen.rotate = 3;}
        if (e.getKeyCode() == KeyEvent.VK_D) {RIGHT = true; Screen.rotate = 1;}
        if (e.getKeyCode() == KeyEvent.VK_S) {DOWN = true; Screen.rotate = 2;}
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) {
            Screen.fire = true;
            X = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) UP = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) LEFT = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) RIGHT = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) DOWN = false;

        if (e.getKeyCode() == KeyEvent.VK_W) UP = false;
        if (e.getKeyCode() == KeyEvent.VK_A) LEFT = false;
        if (e.getKeyCode() == KeyEvent.VK_D) RIGHT = false;
        if (e.getKeyCode() == KeyEvent.VK_S) DOWN = false;
    }
}
