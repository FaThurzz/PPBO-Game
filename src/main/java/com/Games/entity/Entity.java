package main.java.com.Games.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y;
    public int width, height;
    public int speed;
    public BufferedImage sprite;
    public boolean active = true;

    public abstract void update();
    public abstract void render(Graphics2D g, int camX, int camY);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean collidesWith(Entity other) {
        return getBounds().intersects(other.getBounds());
    }
}