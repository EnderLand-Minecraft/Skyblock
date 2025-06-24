package com.creepyx.template.bukkit.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

	private final ItemStack item;
	private final ItemMeta meta;

	public ItemBuilder(Material material) {
		this.item = new ItemStack(material);
		this.meta = item.getItemMeta();
	}

	public ItemBuilder(Material material, int amount) {
		this.item = new ItemStack(material, amount);
		this.meta = item.getItemMeta();
	}

	public ItemBuilder name(String name) {
		this.meta.displayName(ComponentUtil.asComponent(name));
		return this;
	}

	public ItemBuilder name(Component name) {
		this.meta.displayName(name);
		return this;
	}

	public ItemBuilder lore(String... lore) {
		List<Component> loreList = new ArrayList<>();
		for (String string : lore) {
			ComponentUtil.asComponent(string);
			loreList.add(ComponentUtil.asComponent(string));
		}
		this.meta.lore(loreList);
		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		List<Component> loreList = new ArrayList<>();
		for (String string : lore) {
			ComponentUtil.asComponent(string);
			loreList.add(ComponentUtil.asComponent(string));
		}
		this.meta.lore(loreList);
		return this;
	}

	public ItemBuilder lore(Component... lore) {
		this.meta.lore(Arrays.stream(lore).toList());
		return this;
	}

	public ItemBuilder compLore(List<Component> loreComponents) {
		this.meta.lore(loreComponents);
		return this;
	}

	public ItemBuilder modelData(int number) {
		this.meta.setCustomModelData(number);
		return this;
	}

	public ItemBuilder headOwner(String uuid) {
		if (this.meta instanceof SkullMeta skullMeta)
			skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
		return this;
	}

	public ItemBuilder addPersistent(NamespacedKey key, PersistentDataType<Object, Object> type, Object value) {
		this.meta.getPersistentDataContainer().set(key, type, value);
		return this;
	}
	public ItemBuilder addPersistentString(NamespacedKey key, String value) {
		this.meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
		return this;
	}

	public ItemBuilder unbreakable() {
		this.meta.setUnbreakable(!this.meta.isUnbreakable());
		return this;
	}

	public ItemBuilder addFlag(ItemFlag... flags) {
		this.meta.addItemFlags(flags);
		return this;
	}

	public ItemBuilder removeFlag(ItemFlag... flags) {
		this.meta.removeItemFlags(flags);
		return this;
	}

	public ItemBuilder clearLore() {
		this.meta.lore(new ArrayList<>());
		return this;
	}

	public ItemBuilder hideTags() {
		for (ItemFlag value : ItemFlag.values()) {
			this.meta.addItemFlags(value);
		}
		this.meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
		return this;
	}

	public ItemBuilder hideAll() {
		for (ItemFlag value : ItemFlag.values()) {
			this.meta.addItemFlags(value);
		}
		return this;
	}

	public ItemBuilder showTags() {
		for (ItemFlag value : ItemFlag.values()) {
			this.meta.removeItemFlags(value);
		}
		return this;
	}

	public ItemBuilder glow(boolean glow) {

		this.meta.addEnchant(Enchantment.RIPTIDE, 1, true);
		this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return this;
	}

	public ItemBuilder enchant(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder removeEnchant(Enchantment enchantment, int level) {
		this.meta.removeEnchant(enchantment);
		return this;
	}

	public ItemBuilder removeEnchants() {
		for (Enchantment enchantment : this.meta.getEnchants().keySet()) {
			this.meta.removeEnchant(enchantment);
		}
		return this;
	}

	public	ItemBuilder attribute(Attribute attribute, AttributeModifier modifier) {
		this.meta.addAttributeModifier(attribute, modifier);
		return this;
	}

	public ItemBuilder removeAttribute(Attribute attribute, AttributeModifier modifier) {
		this.meta.removeAttributeModifier(attribute, modifier);
		return this;
	}

	public ItemBuilder removeAttribute(Attribute attribute) {
		this.meta.removeAttributeModifier(attribute);
		return this;
	}

	public ItemStack build() {
		this.item.setItemMeta(this.meta);
		return this.item;
	}
}
