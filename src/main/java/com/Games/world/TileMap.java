package main.java.com.Games.world;

import main.java.com.Games.engine.GamePanel;
import java.awt.*;

public class TileMap {
    private Tile[][] tiles;
    private FarmTile[][] farmData;
    public int rows, cols;

    // Map sederhana hardcoded dulu (nanti bisa load dari file .txt)
    private static final int[][] DEFAULT_MAP = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,2,2,2,2,0,0,0,1,1,1,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1,4,4,1,0,0,0,0,0,0,0},
            {0,0,0,4,4,4,0,0,0,1,4,4,1,0,0,0,0,0,0,0},
            {0,0,0,4,4,4,0,0,0,1,1,1,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
    };
    // 0=GRASS,1=STONE,2=WATER,3=DIRT,4=FARMLAND,5=PATH

    private static final TileType[] TYPE_MAP = {
            TileType.GRASS, TileType.STONE, TileType.WATER,
            TileType.DIRT, TileType.FARMLAND, TileType.PATH
    };

    public TileMap() {
        loadDefaultMap();
    }

    private void loadDefaultMap() {
        rows = DEFAULT_MAP.length;
        cols = DEFAULT_MAP[0].length;
        tiles = new Tile[rows][cols];
        farmData = new FarmTile[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = new Tile(TYPE_MAP[DEFAULT_MAP[r][c]], null);
                farmData[r][c] = new FarmTile();
            }
        }
    }

    public Tile getTileAt(int col, int row) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return null;
        return tiles[row][col];
    }

    public boolean isPassable(int col, int row) {
        Tile t = getTileAt(col, row);
        return t != null && t.passable;
    }

    public void render(Graphics2D g, Camera cam) {
        int tileSize = GamePanel.TILE_SIZE * GamePanel.SCALE;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int screenX = c * tileSize - cam.x;
                int screenY = r * tileSize - cam.y;
                // Cull tile yang tidak terlihat
                if (screenX + tileSize < 0 || screenX > GamePanel.SCREEN_WIDTH) continue;
                if (screenY + tileSize < 0 || screenY > GamePanel.SCREEN_HEIGHT) continue;
                tiles[r][c].render(g, screenX, screenY, tileSize);
            }
        }
    }
    public FarmTile getFarmTile(TilePos pos) {
        if (pos.row < 0 || pos.row >= rows) return null;
        if (pos.col < 0 || pos.col >= cols) return null;
        return farmData[pos.row][pos.col];
    }
}