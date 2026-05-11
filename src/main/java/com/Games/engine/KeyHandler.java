package main.java.com.Games.engine;

import java.awt.event.*;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean actionPressed, inventoryPressed, pausePressed;
    public boolean slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9;

    public boolean actionJustPressed;
    private boolean actionWasPressed = false;

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
            case KeyEvent.VK_1 -> slot1 = true;
            case KeyEvent.VK_2 -> slot2 = true;
            case KeyEvent.VK_3 -> slot3 = true;
            case KeyEvent.VK_4 -> slot4 = true;
            case KeyEvent.VK_5 -> slot5 = true;
            case KeyEvent.VK_6 -> slot6 = true;
            case KeyEvent.VK_7 -> slot7 = true;
            case KeyEvent.VK_8 -> slot8 = true;
            case KeyEvent.VK_9 -> slot9 = true;
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
            case KeyEvent.VK_1 -> slot1 = false;
            case KeyEvent.VK_2 -> slot2 = false;
            case KeyEvent.VK_3 -> slot3 = false;
            case KeyEvent.VK_4 -> slot4 = false;
            case KeyEvent.VK_5 -> slot5 = false;
            case KeyEvent.VK_6 -> slot6 = false;
            case KeyEvent.VK_7 -> slot7 = false;
            case KeyEvent.VK_8 -> slot8 = false;
            case KeyEvent.VK_9 -> slot9 = false;
        }
    }
    public void update() {
        actionJustPressed = actionPressed && !actionWasPressed;
        actionWasPressed  = actionPressed;
    }
    @Override public void keyTyped(KeyEvent e) {}
}