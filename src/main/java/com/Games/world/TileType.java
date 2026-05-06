package main.java.com.Games.world;

public enum TileType {
    GRASS(true),
    DIRT(true),
    WATER(false),
    STONE(false),
    FARMLAND(true),
    PATH(true);

    public final boolean passable;

    TileType(boolean passable) {
        this.passable = passable;
    }
}