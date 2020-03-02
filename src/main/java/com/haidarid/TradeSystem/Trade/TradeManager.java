package com.haidarid.tradesystem.Trade;

import com.haidarid.tradesystem.TradeSystem;
import com.haidarid.tradesystem.Utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TradeManager {
	
	private static HashMap<Player, Trade> trading = new HashMap<>();

	public static Trade offerTrade(Player offerer, Player reciever) {
		Trade trade = new Trade(offerer, reciever);
		trading.put(offerer, trade);
		acceptTrade(offerer);
		return trade;
	}
	
	public static void startTrading(Player offerer, Player reciever) {
		trading.put(reciever, getTrade(offerer));
		getTrade(offerer).setTrading(true);		
	}
	
	private static void acceptTrade(Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (getTrade(player).isTrading()) {
					cancel();
				} else {
					deleteTrade(player);
					player.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("trade-denied-sender")));
				}
			}
		}.runTaskLater(TradeSystem.getInstance(), 200L);
	}

	public static Trade getTrade(Player p) {
		return trading.get(p);
	}
	
	public static void deleteTrade(Player player) {
		trading.put(player, null);
	}
}
