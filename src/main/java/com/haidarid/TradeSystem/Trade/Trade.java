package com.haidarid.tradesystem.Trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Trade {

	private final Player offerer;
	private final Player reciever;
	private boolean trading;
	private boolean offererReady;
	private boolean accepterReady;
	private final Inventory recieverInventory;
	private final Inventory offererInventory;

	public Trade(Player offerer, Player reciever) {
		this.offerer = offerer;
		this.reciever = reciever;
		this.offererInventory = Bukkit.createInventory(offerer, 5 * 9, "You");
		this.recieverInventory = Bukkit.createInventory(reciever, 5 * 9, "You");
		setTrading(false);
		setRecieverReady(false);
		setOffererReady(false);
	}

	public Player getOfferer() {
		return offerer;
	}

	public Player getReciever() {
		return reciever;
	}

	public boolean isTrading() {
		return trading;
	}

	public void setTrading(boolean trading) {
		this.trading = trading;
	}

	public boolean isOffererReady() {
		return offererReady;
	}

	public void setOffererReady(boolean offererReady) {
		this.offererReady = offererReady;
	}

	public boolean isRecieverReady() {
		return accepterReady;
	}

	public void setRecieverReady(boolean accepterReady) {
		this.accepterReady = accepterReady;
	}

	public Inventory getRecieverInventory() {
		return recieverInventory;
	}

	public Inventory getOffererInventory() {
		return offererInventory;
	}
	
}
