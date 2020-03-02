package com.haidarid.tradesystem.Listeners;

import com.haidarid.tradesystem.Utils;
import com.haidarid.tradesystem.Trade.Trade;
import com.haidarid.tradesystem.Trade.TradeManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener{


	@EventHandler
	public void onInventoryCLick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		Inventory inventory = e.getClickedInventory();
		Trade trade = TradeManager.getTrade(p);
		ItemStack cursor = p.getItemOnCursor();
		ItemStack current = e.getCurrentItem();
		if (trade == null||inventory == null) return;
		int slot = e.getSlot();
		Utils.itemUpdater(trade.getOffererInventory(), trade.getRecieverInventory());
		if (inventory.equals(e.getView().getTopInventory())) {
			if (Utils.left.contains(slot)) {
				if (e.getClick().equals(ClickType.DOUBLE_CLICK)) {
					if ((current == null) && cursor != null) {
						for (int j = 0 ; j < 9*4 ; j++) {
							if (Utils.left.contains(j)) {
								ItemStack item = p.getOpenInventory().getItem(j);
								if (item != null && item.isSimilar(cursor)) {
									int result = item.getAmount() + cursor.getAmount();
									if (result <= cursor.getMaxStackSize()) {
										cursor.setAmount(result);
										item.setAmount(0);
										if (cursor.getAmount() == cursor.getMaxStackSize()) {
											break;
										}
										continue;
									}
									item.setAmount(cursor.getAmount() - cursor.getMaxStackSize() + item.getAmount());
									cursor.setAmount(item.getMaxStackSize());
									for (int k = 0 ; k < 9*4 ; k++) {
										if (k != j && (Utils.left.contains(j))) {
											ItemStack item2= p.getOpenInventory().getItem(k);
											if (item2 != null && item2.isSimilar(item)) {
												int result2 = item2.getAmount() + item.getAmount();
												if ( result2 <= item.getMaxStackSize()) {
													item.setAmount(result2);
													item2.setAmount(0);
													if (item.getAmount() == item.getMaxStackSize()) {
														break;
													}
													continue;
												}
												item2.setAmount(item.getAmount() - item.getMaxStackSize() + item2.getAmount());
												item.setAmount(item.getMaxStackSize());
											}
										}
									}
								}
							}
						}
					}
				}
				Utils.setTradeStatus(p, trade, inventory, false);		
			} else if (e.getSlot() == 22) {
				e.setCancelled(true);
				if (trade.isOffererReady() || trade.isRecieverReady()) {
					Utils.setTradeStatus(p, trade, inventory, false);
					return;
				}
				Utils.setTradeStatus(p, trade, inventory, true);
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (inventory.equals(e.getView().getBottomInventory())) {
			if (e.getClick().equals(ClickType.DOUBLE_CLICK)) {
				if ((current == null) && cursor != null) {
					
					for (int j = 0 ; j < 9*4 ; j++) {	
							ItemStack item = p.getInventory().getItem(j);
							if (item != null && item.isSimilar(cursor)) {
								int result = item.getAmount() + cursor.getAmount();
								if (result <= cursor.getMaxStackSize()) {
									cursor.setAmount(result);
									item.setAmount(0);
									continue;
								}
								item.setAmount((cursor.getAmount() - cursor.getMaxStackSize()) + item.getAmount());
								cursor.setAmount(item.getMaxStackSize());
								for (int k = 0 ; k < 9*4 ; k++) {
									if (k != j) {
										ItemStack item2= p.getInventory().getItem(k);
										if (item2 != null && item2.isSimilar(item)) {
											int result2 = item2.getAmount() + item.getAmount();
											if ( result2 <= item.getMaxStackSize()) {
												item.setAmount(result2);
												item2.setAmount(0);
												continue;
											}
											item2.setAmount((item.getAmount() - item.getMaxStackSize()) + item2.getAmount());
											item.setAmount(item.getMaxStackSize());
										}
									}
								}
							}
						
					}
				}
			} else if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT) ) {
				e.setCancelled(true);
				if (e.getCurrentItem() != null) {
					for (int left: Utils.left) {
						ItemStack leftItemStack = p.getOpenInventory().getItem(left);
						if (leftItemStack != null && leftItemStack.isSimilar(e.getCurrentItem())) {
							int result = leftItemStack.getAmount() + e.getCurrentItem().getAmount();
							if (result <= leftItemStack.getMaxStackSize()) {
								leftItemStack.setAmount(result);
								e.setCurrentItem(null);
								break;
							}
							e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - e.getCurrentItem().getMaxStackSize() + leftItemStack.getAmount());
							leftItemStack.setAmount(leftItemStack.getMaxStackSize());
						} if (leftItemStack == null) {
							p.getOpenInventory().setItem(left, e.getCurrentItem());
							e.setCurrentItem(null);
						}
					}
				}
			}
		}
	}
}

