package main.java.com.Games.Items;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.TileMap;

public class Food extends Item{
    public int    hpRestore;
    public int    staminaRestore;
    public String buffType;     // "speed" | "luck" | "defense" | null
    public int    buffDuration; // durasi buff dalam menit in-game

    public Food(String name, String description,
                int hpRestore, int staminaRestore,
                String buffType, int buffDuration,
                int sellPrice, int buyPrice) {
        super(name, description, ItemType.FOOD, 99, sellPrice, buyPrice);
        this.hpRestore      = hpRestore;
        this.staminaRestore = staminaRestore;
        this.buffType       = buffType;
        this.buffDuration   = buffDuration;
    }
    @Override
    public boolean isUsable() { return true; }

    @Override
    public void use(Player player, TileMap tileMap) {
        player.hp      = Math.min(player.maxHp,      player.hp      + hpRestore);
        player.stamina = Math.min(player.maxStamina,  player.stamina + staminaRestore);

        if (buffType != null) {
            // player.addBuff(buffType, buffDuration); // aktifkan setelah Buff system dibuat
            System.out.println("Buff " + buffType + " aktif selama " + buffDuration + " menit!");
        }

        quantity--;
        System.out.println("Makan " + name
                + " | HP +" + hpRestore
                + " | Stamina +" + staminaRestore);
    }
}
