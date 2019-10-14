package net.peacefulcraft.xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class TrenchBounties implements Listener{

	private Economy eco = null;
	private Connection mysql = null;
	
	public TrenchBounties() {
		
		RegisteredServiceProvider<Economy> rsp = XSS.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
		eco = rsp.getProvider();
		
		mysqlConnect();
	}
	
	public void mysqlConnect() {
		String ip = XSS.getXXSConfig().getDb_ip();
		String name = XSS.getXXSConfig().getDb_name();
		String username = XSS.getXXSConfig().getDb_user();
		String password = XSS.getXXSConfig().getDb_password();
		String dbUrl = "jdbc:mysql://" + ip + ":3306/" + name;
		
		try {
			mysql = DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {
			if(mysql != null && mysql.isClosed())
				return;
			
			mysql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		
		final Player p = e.getPlayer();
		final String uuid = e.getPlayer().getUniqueId().toString().replace("-", "");
		
		(new BukkitRunnable() {
			
			public void run() {
				try {
				
					if(mysql.isClosed()) {
						mysqlConnect();
					}
					
					PreparedStatement stmt = mysql.prepareStatement("SELECT `payout` FROM `bounties` WHERE `uuid`=?");
					stmt.setString(1, uuid);
					ResultSet res = stmt.executeQuery();
					
					if(res.next()) {
						
						final double payout = res.getDouble(1);
						
						if(payout > 0.0) {
						
							(new BukkitRunnable() {
								public void run() {
									
									eco.depositPlayer(p, payout);
									p.sendMessage(
										ChatColor.DARK_RED + "["+ ChatColor.RED + "Trench" + ChatColor.DARK_RED + "] " +
										ChatColor.RED + " You've earned $" + payout + " on the battle field"
									);	
									
								}
							}).runTask(XSS.getInstance());
							
							stmt = mysql.prepareStatement("UPDATE `bounties` SET `payout`= 0 WHERE `uuid`=?");
							stmt.setString(1, uuid);
							stmt.execute();
							
							stmt.close();
						}
						
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}	
			}
		}).runTaskAsynchronously(XSS.getInstance());
		
	}
	
}
