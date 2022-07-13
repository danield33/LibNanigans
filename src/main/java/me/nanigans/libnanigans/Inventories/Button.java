package me.nanigans.libnanigans.Inventories;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * This class is meant to be inherited for your own button code.
 */
public class Button {

    private final ItemStack buttonItem;
    private final Consumer<ItemStack> callback;
    private final boolean playClick;

    public Button(ItemStack buttonItem, boolean playClick, Consumer<ItemStack> action){
        this.buttonItem = buttonItem;
        this.callback = action;
        this.playClick = playClick;
    }


    public boolean isPlayClick() {
        return playClick;
    }

    public ItemStack getItem(){
        return buttonItem;
    }

    public void execute(){
        this.callback.accept(this.getItem());
    }

}