package com.haidarid.tradesystem;

import com.haidarid.tradesystem.Trade.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Utils {
	
	public static final List<Integer> left = new ArrayList<>(Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30));
	public static final List<Integer> right= new ArrayList<>(Arrays.asList(14, 15, 16, 23, 24, 25, 32, 33, 34));
	
	private static  void sendTradeGui(Inventory gui) {

		for (int i = 0; i < 9 * 5; i++) {
			if (!left.contains(i) && !right.contains(i)) {
				gui.setItem(i, makeItem(Material.RED_STAINED_GLASS_PANE, " ", 1));
			}
			if (Arrays.asList(4,13,31,40,49).contains(i)) {
				gui.setItem(i, makeItem(Material.BLACK_STAINED_GLASS_PANE, " ", 1));
			}
		}
		gui.setItem(22, makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "READY", 1));
		gui.setItem(14, makeItem(Material.DIRT, ChatColor.RESET + "Dirt", 1));
	}
	
	public static ItemStack makeItem(Material material, String name, int value) {
		ItemStack pin = new ItemStack(material, value);
		ItemMeta pinMeta = pin.getItemMeta();
		pinMeta.setDisplayName(name);
		pin.setItemMeta(pinMeta);
		return pin;
	}
	

	
	public static void openGui(Player p, Player p2, Inventory gui, Inventory gui2) {
		p.openInventory(gui);
		p2.openInventory(gui2);
		sendTradeGui(gui);
		sendTradeGui(gui2);
	}
	
	public static void switchGlass(Inventory gui, Material from, Material to, int first) {
		int slot = first;
		for (int i = 0; i < 5; i++) {
			slot = slot + i * 9;
			for (int j = 0; j < 4; j++) {
				try {
					if (gui.getItem(slot).equals(makeItem(from, " ", 1))) {
						gui.setItem(slot, makeItem(to, " ", 1));
					}
				} catch (Exception exc) {
					// TODO: handle exception
				}
				slot++;
			}
			slot = first;
		}
	}
	
	private static void sendItemstack(Inventory from, Inventory to) {
		for (int slot : left) {
				ItemStack item = from.getItem(slot);
				to.setItem(slot + 4, item);
		}
	}
	
	public static void itemUpdater(Inventory one, Inventory two) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				sendItemstack(one, two);
				sendItemstack(two, one);
				for (HumanEntity humanEntity : two.getViewers()) {
					Player player = (Player) humanEntity;
					player.updateInventory();
				}
				for (HumanEntity humanEntity : one.getViewers()) {
					Player player = (Player) humanEntity;
					player.updateInventory();
				}	
			}
		}.runTaskLater(TradeSystem.getInstance(), 1L);
	}
	
	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static void setTradeStatus(Player p,Trade trade, Inventory inventory, boolean status) {
		if (status == false) {
			if (trade.getReciever() == p && trade.isRecieverReady()) {
				inventory.setItem(22, (Utils.makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "READY", 1)));
				Utils.switchGlass(trade.getRecieverInventory(), Material.LIME_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, 0);
				Utils.switchGlass(trade.getOffererInventory(), Material.LIME_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, 5);
				trade.setRecieverReady(status);
			} 
			else if(trade.getOfferer() == p && trade.isOffererReady()) {
				inventory.setItem(22, (Utils.makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "READY", 1)));
				Utils.switchGlass(trade.getOffererInventory(), Material.LIME_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, 0);
				Utils.switchGlass(trade.getRecieverInventory(), Material.LIME_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, 5);
				trade.setOffererReady(status);
			}
		} else {
			if (trade.getReciever() == p && !trade.isRecieverReady()) {
				inventory.setItem(22, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "CANCEL", 1));
				Utils.switchGlass(trade.getRecieverInventory(), Material.RED_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, 0);
				Utils.switchGlass(trade.getOffererInventory(), Material.RED_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, 5);
				trade.setRecieverReady(status);
				return;
			}
			else if(trade.getOfferer() == p && !trade.isOffererReady()) {
				inventory.setItem(22, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "CANCEL", 1));
				Utils.switchGlass(trade.getRecieverInventory(), Material.RED_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, 0);
				Utils.switchGlass(trade.getOffererInventory(), Material.RED_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, 5);
				trade.setOffererReady(status);
			}
		}
	}
	
	public static void doubleClick(ItemStack cursor, ItemStack current, Player p) {
		
	}
	
}
