package main.java.com.Games.entity;

import main.java.com.Games.Items.Inventory;
import main.java.com.Games.Items.Item;
import main.java.com.Games.Items.Seed;
import main.java.com.Games.Items.Tool;
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
    public Inventory inventory = new Inventory();

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

        inventory.addItem(Tool.basicHoe());
        inventory.addItem(Tool.basicWateringCan());
        inventory.addItem(Tool.basicScythe());
        inventory.addItem(Seed.parsnip());
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

        key.update();
        // Scroll slot dengan Q/E atau angka 1-9
        if (key.slot1) inventory.setActiveIndex(0);
        if (key.slot2) inventory.setActiveIndex(1);
        if (key.slot3) inventory.setActiveIndex(2);
        if (key.slot4) inventory.setActiveIndex(3);
        if (key.slot5) inventory.setActiveIndex(4);
        if (key.slot6) inventory.setActiveIndex(5);
        if (key.slot7) inventory.setActiveIndex(6);
        if (key.slot8) inventory.setActiveIndex(7);
        if (key.slot9) inventory.setActiveIndex(8);

        // Gunakan item aktif
        if (key.actionJustPressed) {
            Item active = inventory.getActiveItem();
            if (active != null && active.isUsable()) {
                active.use(this, tileMap);
            }
        }
    }

    private void move(int dx, int dy) {
        int tileSize = GamePanel.TILE_SIZE * GamePanel.SCALE;
        int mapW = tileMap.cols * tileSize;
        int mapH = tileMap.rows * tileSize;

        // Cek collision horizontal + batas map
        int newX = x + dx;
        if (newX < 0) newX = 0;                      // ← batas kiri
        if (newX + width > mapW) newX = mapW - width; // ← batas kanan

        int col  = (newX + (dx > 0 ? width : 0)) / tileSize;
        int row1 = y / tileSize;
        int row2 = (y + height - 1) / tileSize;
        if (tileMap.isPassable(col, row1) && tileMap.isPassable(col, row2)) {
            x = newX;
        }

        // Cek collision vertikal + batas map
        int newY = y + dy;
        if (newY < 0) newY = 0;                        // ← batas atas
        if (newY + height > mapH) newY = mapH - height; // ← batas bawah

        int col1 = x / tileSize;
        int col2 = (x + width - 1) / tileSize;
        int row1v = (newY + (dy > 0 ? height : 0)) / tileSize;
        if (tileMap.isPassable(col1, row1v) && tileMap.isPassable(col2, row1v)) {
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