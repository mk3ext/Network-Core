package me.mk3ext.dev.core.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemMeta meta;
    private ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public static ItemBuilder copyOf(ItemStack itemStack) {
        return new ItemBuilder(new ItemStack(itemStack));
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder type(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder dye(Color color) {
        if(!(meta instanceof LeatherArmorMeta)) return this;
        ((LeatherArmorMeta)meta).setColor(color);
        return this;
    }

    public ItemBuilder setSkullOwner(String name) {
        if(meta instanceof SkullMeta)
        {
            ((SkullMeta)meta).setOwner(name);
        }
        return this;
    }

    public ItemBuilder displayname(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemBuilder itemMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    public ItemBuilder durability(short dura) {
        this.itemStack.setDurability(dura);
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        this.itemStack.setData(data);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        lore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        addLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLoreLine(int line, String text) {
        if(!meta.hasLore()) return this;
        if(meta.getLore().size() <= line) return this;
        List<String> lore = meta.getLore();
        lore.set(line, text);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> tempLore = new ArrayList<>();
        for(String str : lore)
        {
            tempLore.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        this.meta.setLore(tempLore);
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        List<String> tempLore = this.meta.hasLore()?this.meta.getLore():new ArrayList<>();;
        for(String str : lore)
        {
            tempLore.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        this.meta.setLore(tempLore);
        return this;
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments, boolean safe) {
        enchantments.keySet().forEach(enchantment -> meta.addEnchant(enchantment, enchantments.get(enchantment), !safe));
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level, boolean safe) {
        meta.addEnchant(enchantment, level, !safe);
        return this;
    }

    public ItemBuilder glow() {
        enchantment(Enchantment.DAMAGE_ALL, 1, false);
        addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
