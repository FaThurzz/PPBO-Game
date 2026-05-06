package main.java.com.Games.engine;

import java.awt.event.*;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean actionPressed, inventoryPressed, pausePressed;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> upPressed        = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> downPressed      = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> leftPressed      = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed     = true;
            case KeyEvent.VK_Z, KeyEvent.VK_ENTER -> actionPressed    = true;
            case KeyEvent.VK_E                    -> inventoryPressed = true;
            case KeyEvent.VK_ESCAPE               -> pausePressed     = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> upPressed        = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> downPressed      = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> leftPressed      = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed     = false;
            case KeyEvent.VK_Z, KeyEvent.VK_ENTER -> actionPressed    = false;
            case KeyEvent.VK_E                    -> inventoryPressed = false;
            case KeyEvent.VK_ESCAPE               -> pausePressed     = false;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
}