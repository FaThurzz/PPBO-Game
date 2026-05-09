package main.java.com.Games.Items;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.TileMap;

public class Material extends Item{
    public enum Grade { COMMON, UNCOMMON, RARE }

    public Grade grade;

    public Material(String name, String description,
                    int sellPrice, Grade grade) {
        super(name, description, ItemType.MATERIAL, 999, sellPrice, 0);
        this.grade = grade;
    }

    @Override
    public boolean isUsable() { return false; }

    @Override
    public void use(Player player, TileMap tileMap) {
        System.out.println(name + " adalah bahan baku. Gunakan untuk crafting atau dijual.");
    }
}
