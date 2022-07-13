package me.nanigans.libnanigans.Inventories;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class InvGui implements Listener {

    private Page[] pages;
    private final HashMap<UUID, Integer> guiOpen = new HashMap<>();
    private int pageNum = 0;
    private Inventory currentInventory;

    public InvGui(Page[] pages, Plugin pl){
        this.pages = pages;
        pl.getServer().getPluginManager().registerEvents(this, pl);
        pl.getLogger().info("Class events registered.");
    }

    public void open(Player p, Integer pageNum){
        if(pageNum <= pages.length-1) {
            this.pageNum = pageNum;
            guiOpen.put(p.getUniqueId(), pageNum);
            final Inventory inv = pages[pageNum].build();
            this.currentInventory = inv;
            p.openInventory(inv);
        }else{
            throw new IllegalArgumentException("Integer is too large for array size.");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();
        if(guiOpen.containsKey(p.getUniqueId())){
            e.setCancelled(true);
            Button currentButton = pages[guiOpen.get(p.getUniqueId())].getButton(e.getSlot());
            if (currentButton != null) {
                if(currentButton.isPlayClick()){
                    p.playSound(p.getLocation(), Sound.valueOf("CLICK"), 1f, 1f);
                }
                currentButton.execute();
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player)e.getPlayer();
        guiOpen.remove(p.getUniqueId());

    }

    public Inventory getCurrentInventory() {
        return currentInventory;
    }

    public void setPages(Page[] pages) {
        this.pages = pages;
    }

    public int getPageNum() {
        return pageNum;
    }
}