package net.peacefulcraft.xss;

import org.bukkit.plugin.java.JavaPlugin;

public class XXS extends JavaPlugin{

	private static XXS instance = null;
	public static XXS getInstance() { return instance; }
	
	private static XXSConfig config;
	public static XXSConfig getXXSConfig() { return config; }
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		config = new XXSConfig(getConfig());
	}
	
	public void onDisable() {
		saveConfig();
	}
	
}
