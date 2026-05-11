package main.java.com.Games.Items;

import main.java.com.Games.engine.GamePanel;
import main.java.com.Games.Items.Item;
import java.awt.*;

public class InventoryRenderer {

    private static final int SLOT_SIZE   = 40; // ukuran tiap slot (pixel)
    private static final int SLOT_MARGIN = 4;  // jarak antar slot
    private static final int PADDING     = 6;  // padding dalam slot

    /**
     * Render hotbar di bagian bawah tengah layar.
     * Dipanggil dari GamePanel.paintComponent()
     */
    public static void renderHotbar(Graphics2D g, Inventory inv) {
        int total    = Inventory.HOTBAR_SIZE;
        int barWidth = total * (SLOT_SIZE + SLOT_MARGIN) - SLOT_MARGIN;

        // Posisi hotbar: bawah tengah layar
        int startX = (GamePanel.SCREEN_WIDTH - barWidth) / 2;
        int startY = GamePanel.SCREEN_HEIGHT - SLOT_SIZE - 50;

        for (int i = 0; i < total; i++) {
            int slotX = startX + i * (SLOT_SIZE + SLOT_MARGIN);
            boolean isActive = (i == inv.getActiveIndex());

            drawSlot(g, slotX, startY, isActive);

            Item item = inv.getItem(i);
            if (item != null) {
                drawItemInSlot(g, item, slotX, startY);
            }
        }
    }

    // ── Helper: gambar 1 kotak slot ─────────────────────────

    private static void drawSlot(Graphics2D g, int x, int y, boolean active) {
        // Background slot
        g.setColor(new Color(40, 30, 20, 200));
        g.fillRoundRect(x, y, SLOT_SIZE, SLOT_SIZE, 6, 6);

        // Border — kuning kalau aktif, abu-abu kalau tidak
        g.setColor(active ? new Color(255, 220, 60) : new Color(100, 90, 80));
        g.setStroke(new BasicStroke(active ? 2.5f : 1.5f));
        g.drawRoundRect(x, y, SLOT_SIZE, SLOT_SIZE, 6, 6);
        g.setStroke(new BasicStroke(1f)); // reset stroke
    }

    // ── Helper: gambar ikon + quantity item di dalam slot ───

    private static void drawItemInSlot(Graphics2D g, Item item, int x, int y) {
        if (item.getIcon() != null) {
            // Gambar sprite item kalau sudah ada
            g.drawImage(item.getIcon(),
                    x + PADDING, y + PADDING,
                    SLOT_SIZE - PADDING * 2, SLOT_SIZE - PADDING * 2,
                    null);
        } else {
            // Fallback: warna blok berdasarkan ItemType
            g.setColor(colorOf(item.getType()));
            g.fillRoundRect(x + PADDING, y + PADDING,
                    SLOT_SIZE - PADDING * 2, SLOT_SIZE - PADDING * 2,
                    4, 4);

            // Huruf pertama nama item sebagai placeholder
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 11));
            g.drawString(
                    String.valueOf(item.getName().charAt(0)),
                    x + SLOT_SIZE / 2 - 4,
                    y + SLOT_SIZE / 2 + 4
            );
        }

        // Quantity (pojok kanan bawah slot) — tampilkan jika > 1
        if (item.getQuantity() > 1) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 9));
            String qty = String.valueOf(item.getQuantity());
            g.drawString(qty, x + SLOT_SIZE - 6 - qty.length() * 5, y + SLOT_SIZE - 4);
        }
    }

    // ── Helper: warna placeholder per ItemType ───────────────

    private static Color colorOf(ItemType type) {
        return switch (type) {
            case SEED     -> new Color(120, 200, 80);  // hijau muda
            case CROP     -> new Color(255, 180, 50);  // oranye
            case TOOL     -> new Color(150, 150, 200); // biru abu
            case FOOD     -> new Color(220, 100, 100); // merah muda
            case MATERIAL -> new Color(160, 130, 90);  // coklat
            default       -> new Color(180, 180, 180); // abu-abu
        };
    }
}