package main.java.com.Games.Items;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.FarmTile;
import main.java.com.Games.world.TileMap;
import main.java.com.Games.world.TilePos;

public class Tool extends Item{
    public enum ToolType { HOE, WATERING_CAN, AXE, PICKAXE, SCYTHE, SWORD }

    public ToolType toolType;
    public int      energyCost; // stamina yang dikurangi per pemakaian
    public int      level;      // 1=Basic, 2=Copper, 3=Steel, 4=Gold, 5=Iridium

    public Tool(String name, ToolType toolType, int energyCost, int level) {
        // Tool tidak bisa ditumpuk (maxStack=1) dan tidak dijual murah
        super(name, descOf(toolType), ItemType.TOOL, 1, 0, 0);
        this.toolType   = toolType;
        this.energyCost = energyCost;
        this.level      = level;
    }

    private static String descOf(ToolType t) {
        return switch (t) {
            case HOE          -> "Mencangkul tanah menjadi lahan pertanian.";
            case WATERING_CAN -> "Menyiram tanaman agar tumbuh.";
            case AXE          -> "Menebang pohon dan semak.";
            case PICKAXE      -> "Memecah batu dan ore.";
            case SCYTHE       -> "Memanen tanaman yang sudah matang.";
            case SWORD        -> "Senjata untuk bertarung.";
        };
    }

    @Override
    public boolean isUsable() { return true; }

    @Override
    public void use(Player player, TileMap tileMap) {
        // Cek stamina cukup
        if (player.stamina < energyCost) {
            System.out.println("Stamina tidak cukup!");
            return;
        }

        TilePos target = player.getFacingTile();
        FarmTile farm = tileMap.getFarmTile(target);

        switch (toolType) {
            case HOE -> {
                if (farm != null && !farm.tilled) {
                    farm.tilled = true;
                    player.stamina -= energyCost;
                    System.out.println("Tanah dicangkul!");
                } else {
                    System.out.println("Tidak bisa mencangkul di sini.");
                }
            }
            case WATERING_CAN -> {
                if (farm != null && farm.tilled && farm.hasPlant) {
                    farm.watered = true;
                    player.stamina -= energyCost;
                    System.out.println("Tanaman disiram!");
                } else {
                    System.out.println("Tidak ada tanaman untuk disiram.");
                }
            }
            case SCYTHE -> {
                if (farm != null && farm.hasPlant && farm.isHarvestable()) {
                    System.out.println("Panen " + farm.cropType + "!");
                    farm.reset(); // bersihkan tile setelah panen
                    player.stamina -= energyCost;
                } else {
                    System.out.println("Belum waktunya panen.");
                }
            }
            case AXE, PICKAXE -> {
                player.stamina -= energyCost;
                System.out.println(name + " digunakan.");
            }
            case SWORD -> {
                player.stamina -= energyCost;
                System.out.println("Menyerang!");
            }
        }
    }
    // ── Factory method ───────────────────────────────────────
    public static Tool basicHoe() {
        return new Tool("Basic Hoe", ToolType.HOE, 2, 1);
    }

    public static Tool basicWateringCan() {
        return new Tool("Watering Can", ToolType.WATERING_CAN, 2, 1);
    }

    public static Tool basicScythe() {
        return new Tool("Scythe", ToolType.SCYTHE, 1, 1);
    }

    public static Tool basicAxe() {
        return new Tool("Basic Axe", ToolType.AXE, 4, 1);
    }

    public static Tool basicPickaxe() {
        return new Tool("Basic Pickaxe", ToolType.PICKAXE, 4, 1);
    }

    public static Tool basicSword() {
        return new Tool("Basic Sword", ToolType.SWORD, 4, 1);
    }
}
