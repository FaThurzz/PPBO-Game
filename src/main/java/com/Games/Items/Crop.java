package main.java.com.Games.Items;
import main.java.com.Games.entity.Player;
import main.java.com.Games.world.TileMap;

public class Crop extends Item {
    public int     energy;   // stamina yang dipulihkan kalau dimakan mentah
    public boolean edible;   // apakah bisa dimakan langsung?

    public Crop(String name, int sellPrice, int energy, boolean edible) {
        super(name, "Hasil panen segar.", ItemType.CROP, 99, sellPrice, 0);
        this.energy = energy;
        this.edible = edible;
    }

    @Override
    public boolean isUsable() { return edible; }

    @Override
    public void use(Player player, TileMap tileMap) {
        if (!edible) {
            System.out.println(name + " tidak bisa dimakan langsung.");
            return;
        }
        player.stamina = Math.min(player.maxStamina, player.stamina + energy);
        quantity--;
        System.out.println("Makan " + name + " | Stamina +" + energy);
    }
}
