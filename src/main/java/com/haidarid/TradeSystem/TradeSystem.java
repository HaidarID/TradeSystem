package com.haidarid.tradesystem;

import com.haidarid.tradesystem.Listeners.ClickEvent;
import com.haidarid.tradesystem.Listeners.DragEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

public class TradeSystem extends JavaPlugin{

	private static TradeSystem instance;
	
	@Override
	public void onEnable() {
		instance = this;
		getCommand("trade").setExecutor(new TradeCommand());
		getCommand("tradesystem").setExecutor(this);
		getCommand("tradesystem").setTabCompleter(this);
		getConfig().options().copyDefaults(false);
		Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
		Bukkit.getPluginManager().registerEvents(new DragEvent(), this);
		saveDefaultConfig();
	}

	public static TradeSystem getInstance() {
		return instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {		
			if (args[0].equalsIgnoreCase("Reload") && sender.hasPermission("TradeSystem.Trade.Reload")) {
				reloadConfig();
				sender.sendMessage("Trade-System : " + ChatColor.GREEN + "Reload Complete !");
			} else if (args[0].equalsIgnoreCase("Version") && sender.hasPermission("TradeSystem.Trade.Reload")) {
				sender.sendMessage("Trade-System : " + ChatColor.GREEN + "v" + getDescription().getVersion());
			} else {
				getServer().dispatchCommand(sender, "Tradesystem");
			}
		} else {
			sender.sendMessage("");
			if (sender.hasPermission("TradeSystem.Trade.Reload")) {
				sender.sendMessage(ChatColor.GRAY + "/TradeSystem " + ChatColor.GREEN + "Reload");
				sender.sendMessage(ChatColor.GRAY + "/TradeSystem " + ChatColor.GREEN + "Version");
				sender.sendMessage(ChatColor.GRAY + "/TradeSystem " + ChatColor.GREEN + "Help");
			}
			sender.sendMessage(ChatColor.GRAY + "/Trade " + ChatColor.GREEN + "[<Player>]");
			sender.sendMessage("");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completions = new ArrayList<>();
		
		if (args.length == 1) {
			if (sender.hasPermission("TradeSystem.Trade.Reload")) {
				StringUtil.copyPartialMatches(args[0], Arrays.asList("Reload", "Version", "Help"), completions);
				Collections.sort(completions);
				return completions;
		 }
			StringUtil.copyPartialMatches(args[0], Arrays.asList("Help"), completions);
			Collections.sort(completions);
			return completions;
		}
		return null;
	}
}
