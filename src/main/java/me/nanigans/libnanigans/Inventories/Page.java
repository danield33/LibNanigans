package me.nanigans.libnanigans.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;


public class Page{

    private final String guiName;
    private final Integer slots;
    private Map<Integer, Button> buttons = new HashMap<>();

    public Page(String guiName, Integer slots, Map<Integer, Button> buttons) {
        this.guiName = guiName;
        this.slots = slots;
        this.buttons = buttons;
    }

    public Page(String guiName, Integer slots) {
        this.guiName = guiName;
        this.slots = slots;
    }

    public void addButton(Integer slot, Button button){
        buttons.put(slot, button);
    }
    public void addButtons(Button[] buttons){
        for(int i = 0; i < buttons.length - 1; i++){
            this.buttons.put(i, buttons[i]);
        }
    }

    public void removeButton(Integer slot){
        buttons.remove(slot);
    }

    public void clearButtons(){
        buttons.clear();
    }

    public Button getButton(Integer slot){
        return buttons.get(slot);
    }

    public Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, slots, guiName);
        for(Integer slot : buttons.keySet()){
            if(!(inventory.getSize() < slot || slot < 0)){
                inventory.setItem(slot, buttons.get(slot).getItem());
            }
        }
        return inventory;
    }
}
