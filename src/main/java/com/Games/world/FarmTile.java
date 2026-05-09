package main.java.com.Games.world;

public class FarmTile {
    public boolean tilled   = false;
    public boolean watered  = false;
    public boolean hasPlant = false;
    public String  cropType = null;
    public int     plantedDay = 0;
    public int     growStage  = 0;

    public boolean isHarvestable() {
        return hasPlant && growStage >= 3; // fully grown
    }
    public void reset() {
        hasPlant   = false;
        cropType   = null;
        plantedDay = 0;
        growStage  = 0;
        watered    = false;
    }
}