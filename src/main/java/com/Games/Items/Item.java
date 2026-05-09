package main.java.com.Games.Items;

import main.java.com.Games.entity.Player;
import main.java.com.Games.world.TileMap;

public abstract class Item {
    protected String name;
    protected String description;
    protected int quantity;
    protected ItemType type;
    protected int maxStack;
    protected int sellPrice;
    protected int buyPrice;

    public Item(String name, String description, ItemType type, int maxStack, int sellPrice, int buyPrice) {
        this.name        = name;
        this.description = description;
        this.type        = type;
        this.maxStack    = maxStack;
        this.sellPrice   = sellPrice;
        this.buyPrice    = buyPrice;
        this.quantity    = 1;
    }

    public abstract boolean isUsable();

    public abstract void use(Player player, TileMap tileMap);

    public boolean canStackWith(Item other) {
        return other != null
                && other.name.equals(this.name)
                && this.quantity < this.maxStack;
    }
    public String getTooltip() {
        return name + "\n"
                + description + "\n"
                + "Jual: $" + sellPrice
                + (buyPrice > 0 ? " | Beli: $" + buyPrice : "");
    }

    public String getName()      { return name; }
    public String getDescription(){ return description; }
    public ItemType getType()      { return type; }
    public int getQuantity()  { return quantity; }
    public int getMaxStack()  { return maxStack; }
    public int getSellPrice() { return sellPrice; }
    public int getBuyPrice()  { return buyPrice; }

    public void setQuantity(int q) { this.quantity = Math.max(0, q); }
}