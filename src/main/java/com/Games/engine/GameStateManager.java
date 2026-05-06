package main.java.com.Games.engine;

public class GameStateManager {

    public enum GameState {
        MENU, PLAYING, PAUSED, DIALOG
    }

    private GameState currentState = GameState.MENU;

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }

    public boolean is(GameState state) {
        return currentState == state;
    }
}