package net.peacefulcraft.xss;

import java.sql.Connection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class TrenchBounties implements Listener{

	private Economy eco = null;
	private Connection mysql = null;
	
	public TrenchBounties() {
		
		RegisteredServiceProvider<Economy> rsp = XXS.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
		eco = rsp.getProvider();
		
		
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		
	}
	
}
