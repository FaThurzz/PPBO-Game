package main.java.com.Games.world;

import main.java.com.Games.engine.GamePanel;
import main.java.com.Games.entity.Entity;

public class Camera {
    public int x, y;

    public void follow(Entity target, TileMap map) {
        // Pusatkan kamera ke player
        x = target.x - GamePanel.SCREEN_WIDTH  / 2 + (GamePanel.TILE_SIZE * GamePanel.SCALE) / 2;
        y = target.y - GamePanel.SCREEN_HEIGHT / 2 + (GamePanel.TILE_SIZE * GamePanel.SCALE) / 2;

        // Clamp agar kamera tidak keluar batas map
        int mapW = map.cols * GamePanel.TILE_SIZE * GamePanel.SCALE;
        int mapH = map.rows * GamePanel.TILE_SIZE * GamePanel.SCALE;
        x = Math.max(0, Math.min(x, mapW  - GamePanel.SCREEN_WIDTH));
        y = Math.max(0, Math.min(y, mapH - GamePanel.SCREEN_HEIGHT));
    }
}