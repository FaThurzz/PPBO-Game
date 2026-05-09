package main.java.com.Games.world;

public enum Season {
    SPRING, // Semi   — Parsnip, Cauliflower, Potato
    SUMMER, // Panas  — Melon, Tomato, Blueberry
    FALL,   // Gugur  — Pumpkin, Yam, Cranberry
    WINTER; // Dingin — tidak bisa bertani (hanya foraging)

    public String getDisplayName() {
        return switch (this) {
            case SPRING -> "Spring";
            case SUMMER -> "Summer";
            case FALL   -> "Fall";
            case WINTER -> "Winter";
        };
    }

    public boolean canFarm() {
        return this != WINTER;
    }

    public Season next() {
        return values()[(this.ordinal() + 1) % 4];
    }
}