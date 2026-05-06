package main.java.com.Games.world;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public TileType type;
    public BufferedImage image;
    public boolean passable;

    public Tile(TileType type, BufferedImage image) {
        this.type     = type;
        this.image    = image;
        this.passable = type.passable;
    }

    public void render(Graphics2D g, int x, int y, int size) {
        if (image != null) {
            g.drawImage(image, x, y, size, size, null);
        } else {
            // Fallback warna kalau sprite belum ada
            switch (type) {
                case GRASS    -> g.setColor(new Color(80, 160, 80));
                case DIRT     -> g.setColor(new Color(139, 100, 60));
                case WATER    -> g.setColor(new Color(60, 120, 200));
                case STONE    -> g.setColor(new Color(120, 120, 120));
                case FARMLAND -> g.setColor(new Color(100, 70, 40));
                case PATH     -> g.setColor(new Color(180, 160, 100));
            }
            g.fillRect(x, y, size, size);
            g.setColor(new Color(0, 0, 0, 30));
            g.drawRect(x, y, size, size);
        }
    }
}