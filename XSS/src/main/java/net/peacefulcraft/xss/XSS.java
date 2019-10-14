package net.peacefulcraft.xss;

import org.bukkit.plugin.java.JavaPlugin;

public class XSS extends JavaPlugin{

	private static XSS instance = null;
	public static XSS getInstance() { return instance; }
	
	private static XSSConfig config;
	public static XSSConfig getXXSConfig() { return config; }
	
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		config = new XSSConfig(getConfig());
		
	}
	
	public void onDisable() {
		tb.shutdown();
		saveConfig();
	}
	
}
