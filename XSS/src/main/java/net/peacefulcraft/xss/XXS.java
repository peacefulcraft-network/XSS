package net.peacefulcraft.xss;

import org.bukkit.plugin.java.JavaPlugin;

public class XXS extends JavaPlugin{

	private static XXS instance = null;
	public static XXS getInstance() { return instance; }
	
	public void onEnable() {
		instance = this;
	}
	
	public void onDisable() {
		
	}
	
}
