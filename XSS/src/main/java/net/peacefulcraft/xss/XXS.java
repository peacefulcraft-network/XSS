package net.peacefulcraft.xss;

import org.bukkit.plugin.java.JavaPlugin;

public class XXS extends JavaPlugin{

	private static XXS instance = null;
	public static XXS getInstance() { return instance; }
	
	private static XXSConfig config;
	public static XXSConfig getXXSConfig() { return config; }
	
	private TrenchBounties tb = null;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		config = new XXSConfig(getConfig());
		
		tb = new TrenchBounties();
		getServer().getPluginManager().registerEvents(tb, this);
	}
	
	public void onDisable() {
		tb.shutdown();
		saveConfig();
	}
	
}
