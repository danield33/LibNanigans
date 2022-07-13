package me.nanigans.libnanigans.Inventories;

public class InventoryUtils {

    /**
     * Calculates the size of the inventory based on the amount of items needed to be in there
     * @param dataSize the amount of data in the inventory
     * @return the allocated size of the inventory less than 55
     */
    public static int calcSize(int dataSize){
        return Math.min(dataSize - (dataSize % 9), 36) + 18;
    }

}
