package main.java.com.Games.Items;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.FarmTile;
import main.java.com.Games.world.Season;
import main.java.com.Games.world.TileMap;
import main.java.com.Games.world.TilePos;

public class Seed extends Item {
    public String cropType;     // jenis tanaman yang akan tumbuh
    public int    growthDays;   // hari yang dibutuhkan untuk panen
    public Season validSeason;  // musim yang valid untuk ditanam

    public Seed(String name, String cropType,
                int growthDays, Season validSeason,
                int sellPrice, int buyPrice) {
        super(name, "Benih " + cropType, ItemType.SEED, 99, sellPrice, buyPrice);
        this.cropType    = cropType;
        this.growthDays  = growthDays;
        this.validSeason = validSeason;
    }

    @Override
    public boolean isUsable() {
        return true;
    }

    @Override
    public void use(Player player, TileMap tileMap) {
        TilePos  target = player.getFacingTile();
        FarmTile farm   = tileMap.getFarmTile(target);

        if (farm == null) {
            System.out.println("Tidak ada lahan di sini.");
            return;
        }
        if (!farm.tilled) {
            System.out.println("Tanah belum dicangkul!");
            return;
        }
        if (farm.hasPlant) {
            System.out.println("Sudah ada tanaman di sini.");
            return;
        }

        farm.hasPlant  = true;
        farm.cropType  = this.cropType;
        farm.plantedDay = 0;
        quantity--;
        System.out.println("Berhasil menanam " + cropType + "!");
    }
    // ── Factory method ───────────────────────────────────────
    public static Seed parsnip() {
        return new Seed("Parsnip Seeds", "Parsnip", 4, Season.SPRING, 10, 20);
    }

    public static Seed melon() {
        return new Seed("Melon Seeds", "Melon", 12, Season.SUMMER, 80, 200);
    }

    public static Seed pumpkin() {
        return new Seed("Pumpkin Seeds", "Pumpkin", 13, Season.FALL, 100, 200);
    }
}
