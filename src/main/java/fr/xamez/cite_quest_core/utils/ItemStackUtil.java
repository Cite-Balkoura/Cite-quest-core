package fr.xamez.cite_quest_core.utils;

import fr.milekat.cite_libs.utils_tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackUtil {

    private String name;
    private final Material material;
    private final int quantity;
    private final List<String> lore;
    private final Map<String, Integer> enchantment;
    private final boolean unbreakable;
    private String owner;

    public ItemStackUtil(ItemStack itemStack) {

        this.material = itemStack.getType();
        this.quantity = itemStack.getAmount();
        this.enchantment = new HashMap<>();
        Map<Enchantment, Integer> ench = itemStack.getEnchantments();
        for (Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
            this.enchantment.put(entry.getKey().getKey().getKey(), entry.getValue());
        }

        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            this.name = meta.getDisplayName() != null ? meta.getDisplayName() : " ";
            this.lore = meta.getLore() != null ? meta.getLore() : Collections.emptyList();
            this.unbreakable = meta.isUnbreakable();
            if (!(meta instanceof SkullMeta)) { return; }
            SkullMeta skull = (SkullMeta) meta;
            this.owner = skull.hasOwner() ? skull.getOwningPlayer().getPlayer().getName() : null;
        } else {
            this.name = " ";
            this.lore = Collections.emptyList();
            this.unbreakable = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<String> getLore() {
        return lore;
    }

    public Map<String, Integer> getEnchantment() {
        return enchantment;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public String getOwner() {
        return owner;
    }

    public ItemStack toItemstack(){
        ItemBuilder itemStack = new ItemBuilder(getMaterial(), getQuantity()).setInfinityDurability(isUnbreakable());
        if (!getName().isEmpty()) { itemStack.setName(getName()); }
        if (getLore().size() > 0) { itemStack.setLore(getLore()); }
        for (Map.Entry<String, Integer> entry : getEnchantment().entrySet()) {
            itemStack.addEnchantements(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(entry.getKey())), entry.getValue());
        }
        if (getOwner() != null){
            itemStack.setSkullOwner(Bukkit.getOfflinePlayer(getOwner()));
        }
        return itemStack.toItemStack();

    }

}
