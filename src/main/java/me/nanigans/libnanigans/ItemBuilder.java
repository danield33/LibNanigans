package me.nanigans.libnanigans;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack itemStack;
    private final ItemMeta meta;
    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder(String material){
        itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(material.split("/")[0])),
                1, Byte.parseByte(material.split("/")[1]));
        meta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack item){
        itemStack = item;
        meta = item.getItemMeta();
    }

    public ItemBuilder setType(Material material){
        itemStack.setType(material);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setType(String material){
        Material mat = Material.getMaterial(Integer.parseInt(material.split("/")[0]));
        byte itemByte = Byte.parseByte(material.split("/")[1]);
        itemStack.setData(new MaterialData(mat, itemByte));
        return this;
    }

    public ItemBuilder setName(String name){
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }
    public ItemBuilder setLore(List<String> lore){
        meta.setLore(lore);
        return this;
    }
    public ItemBuilder setLore(String... lore){
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLore(String lore){
        final List<String> mLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        mLore.add(lore);
        meta.setLore(mLore);
        return this;
    }
    public ItemBuilder addLore(String lore, int position){
        final List<String> mLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        mLore.add(position, lore);
        meta.setLore(mLore);
        return this;
    }

    public ItemBuilder clearLore(){
        meta.getLore().clear();
        return this;
    }

    public ItemBuilder removeLore(String lore, boolean allMatches){
        final List<String> lore1 = meta.getLore();
        if(!allMatches)
        lore1.remove(lore);
        else{
            for (int i = 0; i < lore1.size(); i++) {
                if(lore1.get(i).equals(lore)){
                    lore1.remove(i);
                    i--;
                }
            }
        }
        meta.setLore(lore1);
        return this;
    }

    public ItemBuilder removeLore(int position){
        final List<String> lore = meta.getLore();
        lore.remove(position);
        return this;
    }

    public ItemBuilder removeLoreThatContains(String string, boolean ignoreCase){

        final List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size(); i++) {

            final String l = lore.get(i);
            if(ignoreCase && l.toLowerCase().contains(string)){
                lore.remove(i);
                i--;
            }else if(l.contains(string)){
                lore.remove(i);
                i--;
            }

        }
        return this;
    }

    public ItemBuilder addNBT(List<String> nbt){
        itemStack = NBTData.setNBT(itemStack, (String[]) nbt.toArray());
        return this;
    }
    public ItemBuilder addNBT(String... nbt){
        itemStack = NBTData.setNBT(itemStack, nbt);
        return this;
    }

    public ItemBuilder removeNBT(String... nbt){
        for (String s : nbt) {
            itemStack = NBTData.removeNBT(itemStack, s);
        }
        return this;
    }

    public ItemBuilder clearNBT(){
        final Map<String, String> allNBT = NBTData.getAllNBT(itemStack);
        if(allNBT != null) {
            allNBT.forEach((i, j) -> itemStack = NBTData.removeNBT(itemStack, i));
        }
        return this;
    }

    public ItemBuilder setDurability(int durability){

        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment){
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder clearEnchantments(){
        itemStack.getEnchantments().clear();
        return this;
    }

    public ItemStack buildItem(){
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
