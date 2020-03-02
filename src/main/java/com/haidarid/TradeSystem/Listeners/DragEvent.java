package com.haidarid.tradesystem.Listeners;

import com.haidarid.tradesystem.Utils;
import com.haidarid.tradesystem.Trade.Trade;
import com.haidarid.tradesystem.Trade.TradeManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class DragEvent implements Listener {

	
	
	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		Player p = (Player) e.getWhoClicked();
		Trade trade = TradeManager.getTrade(p);
		if (trade == null) return;
		for (int slot : e.getRawSlots()) {
			if (Utils.right.contains(slot)) {
				e.setCancelled(true);
				return;
			}
			if (trade.isOffererReady() || trade.isRecieverReady()) {
				Utils.setTradeStatus(p, trade, (Inventory) p.getOpenInventory(), false);
			}
		}
		Utils.itemUpdater(trade.getOffererInventory(), trade.getRecieverInventory());
	}
}
