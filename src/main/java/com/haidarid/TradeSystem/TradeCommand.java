package com.haidarid.tradesystem;

import com.haidarid.tradesystem.Trade.Trade;
import com.haidarid.tradesystem.Trade.TradeManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TradeCommand implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target != null) {
					Trade trade = TradeManager.getTrade(target);
					if (trade == null) {
						TradeManager.offerTrade(p, target);
						p.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("trade-offer-sender")).replace("%reciever%", target.getName()));
						target.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("trade-offer-reciever")).replace("%sender%", p.getName()));
					} else {
						if (trade.getOfferer().equals(target)) {
							Utils.openGui(p, target, trade.getRecieverInventory(), trade.getOffererInventory());
							trade.setTrading(true);
							TradeManager.startTrading(target, p);
						} else {
							if (trade.isTrading() == false) {
								TradeManager.offerTrade(p, target);
								p.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("trade-offer-sender")).replace("%reciever%", target.getName()));
								target.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("trade-offer-reciever")).replace("%sender%", p.getName()));
							} else {
								p.sendMessage(Utils.color( TradeSystem.getInstance().getConfig().getString("problem-alreadytrade")).replace("%reciever%", target.getName()));
							}
						}
					}
				} else {
					p.sendMessage(Utils.color( TradeSystem.getInstance().getConfig().getString("problem-notplayer")).replace("%reciever%", args[0]));
				}
			} else {
				p.sendMessage(Utils.color(TradeSystem.getInstance().getConfig().getString("problem-command")));
			}
		}
		return true;
	}

}
