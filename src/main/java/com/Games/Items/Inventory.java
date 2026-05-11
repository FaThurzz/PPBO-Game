package main.java.com.Games.Items;

public class Inventory {

    public static final int HOTBAR_SIZE = 9; // 1 baris = 9 slot

    private Item[] hotbar = new Item[HOTBAR_SIZE];
    private int    activeIndex = 0; // slot yang sedang dipilih

    // ── Tambah item ─────────────────────────────────────────

    /**
     * Tambahkan item ke inventory.
     * Jika item sejenis sudah ada dan bisa ditumpuk, tambah quantity-nya.
     * Jika tidak, cari slot kosong.
     * Return true jika berhasil, false jika penuh.
     */
    public boolean addItem(Item incoming) {
        // Coba stack dulu ke slot yang sudah ada
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            if (hotbar[i] != null && hotbar[i].canStackWith(incoming)) {
                hotbar[i].quantity += incoming.quantity;
                return true;
            }
        }
        // Cari slot kosong
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            if (hotbar[i] == null) {
                hotbar[i] = incoming;
                return true;
            }
        }
        System.out.println("Inventory penuh!");
        return false;
    }

    // ── Hapus item ──────────────────────────────────────────

    /**
     * Kurangi quantity item di slot tertentu sebanyak 1.
     * Jika quantity habis, slot dikosongkan.
     */
    public void consumeAt(int index) {
        if (hotbar[index] == null) return;
        hotbar[index].quantity--;
        if (hotbar[index].quantity <= 0) {
            hotbar[index] = null;
        }
    }

    /** Hapus seluruh item di slot tertentu. */
    public void removeAt(int index) {
        hotbar[index] = null;
    }

    // ── Akses slot ──────────────────────────────────────────

    /** Item yang sedang aktif (dipilih player). */
    public Item getActiveItem() {
        return hotbar[activeIndex];
    }

    /** Ambil item di slot tertentu (boleh null). */
    public Item getItem(int index) {
        if (index < 0 || index >= HOTBAR_SIZE) return null;
        return hotbar[index];
    }

    // ── Navigasi slot aktif ─────────────────────────────────

    public void setActiveIndex(int i) {
        if (i >= 0 && i < HOTBAR_SIZE) activeIndex = i;
    }

    public void scrollNext() {
        activeIndex = (activeIndex + 1) % HOTBAR_SIZE;
    }

    public void scrollPrev() {
        activeIndex = (activeIndex - 1 + HOTBAR_SIZE) % HOTBAR_SIZE;
    }
    public int getActiveIndex() { return activeIndex; }
}