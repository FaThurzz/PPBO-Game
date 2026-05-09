package main.java.com.Games.entity;

import main.java.com.Games.engine.GamePanel;
import main.java.com.Games.engine.KeyHandler;
import main.java.com.Games.world.TileMap;
import main.java.com.Games.world.TilePos;

import java.awt.*;

public class Player extends Entity {

    // Stats
    public int hp, maxHp;
    public int stamina, maxStamina;
    public int level, exp, money;

    // Input
    private final KeyHandler key;

    // Referensi ke map untuk collision
    private TileMap tileMap;

    // Animasi sederhana
    private int animTimer = 0;
    private int animFrame = 0;
    private String direction = "down"; // up/down/left/right
    private boolean moving = false;

    public Player(KeyHandler key, TileMap tileMap) {
        this.key      = key;
        this.tileMap  = tileMap;

        int tileSize = GamePanel.TILE_SIZE * GamePanel.SCALE;
        this.x      = tileSize * 5;
        this.y      = tileSize * 5;
        this.width  = tileSize;
        this.height = tileSize;
        this.speed  = 4;

        this.hp         = 100; this.maxHp      = 100;
        this.stamina    = 100; this.maxStamina = 100;
        this.level      = 1;   this.exp        = 0;
        this.money      = 500;
    }

    @Override
    public void update() {
        int dx = 0, dy = 0;
        moving = false;

        if (key.upPressed)    { dy = -speed; direction = "up";    moving = true; }
        if (key.downPressed)  { dy =  speed; direction = "down";  moving = true; }
        if (key.leftPressed)  { dx = -speed; direction = "left";  moving = true; }
        if (key.rightPressed) { dx =  speed; direction = "right"; moving = true; }

        if (dx != 0 || dy != 0) {
            move(dx, dy);
        }

        // Update animasi
        if (moving) {
            animTimer++;
            if (animTimer >= 10) { animTimer = 0; animFrame = (animFrame + 1) % 4; }
        } else {
            animFrame = 0;
        }
    }

    private void move(int dx, int dy) {
        int tileSize = GamePanel.TILE_SIZE * GamePanel.SCALE;

        // Cek collision horizontal
        int newX = x + dx;
        int col  = (newX + (dx > 0 ? width : 0)) / tileSize;
        int row1 = y / tileSize;
        int row2 = (y + height - 1) / tileSize;
        if (tileMap.isPassable(col, row1) && tileMap.isPassable(col, row2)) {
            x = newX;
        }

        // Cek collision vertikal
        int newY = y + dy;
        int col1 = x / tileSize;
        int col2 = (x + width - 1) / tileSize;
        row1 = (newY + (dy > 0 ? height : 0)) / tileSize;
        if (tileMap.isPassable(col1, row1) && tileMap.isPassable(col2, row1)) {
            y = newY;
        }
    }

    public void gainExp(int amount) {
        exp += amount;
        if (exp >= level * 100) levelUp();
    }

    public void levelUp() {
        exp = 0;
        level++;
        maxHp += 10;
        hp = maxHp;
        System.out.println("Level Up! Sekarang level " + level);
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    @Override
    public void render(Graphics2D g, int camX, int camY) {
        int screenX = x - camX;
        int screenY = y - camY;

        if (sprite != null) {
            g.drawImage(sprite, screenX, screenY, width, height, null);
        } else {
            // Placeholder pixel art player
            g.setColor(new Color(70, 130, 180));
            g.fillRect(screenX + 8, screenY + 4, 32, 32);  // badan
            g.setColor(new Color(255, 210, 160));
            g.fillRect(screenX + 12, screenY - 4, 24, 24); // kepala

            // Indikator arah
            g.setColor(Color.RED);
            switch (direction) {
                case "up"    -> g.fillRect(screenX + 20, screenY,      8, 6);
                case "down"  -> g.fillRect(screenX + 20, screenY + 36, 8, 6);
                case "left"  -> g.fillRect(screenX,      screenY + 20, 6, 8);
                case "right" -> g.fillRect(screenX + 42, screenY + 20, 6, 8);
            }

            // Nama frame animasi (debug)
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 9));
            g.drawString(direction + " " + animFrame, screenX, screenY - 6);
        }
    }
    public TilePos getFacingTile() {
        int ts   = GamePanel.TILE_SIZE * GamePanel.SCALE;
        int col  = (x + width  / 2) / ts;
        int row  = (y + height / 2) / ts;

        return switch (direction) {
            case "up"    -> new TilePos(col, row - 1);
            case "down"  -> new TilePos(col, row + 1);
            case "left"  -> new TilePos(col - 1, row);
            case "right" -> new TilePos(col + 1, row);
            default      -> new TilePos(col, row);
        };
    }
}