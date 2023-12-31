package net.xenocubium.derver.main;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import net.kyori.adventure.text.Component;

public class MainListener implements Listener {

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		
		Environment fromEnv = from.getWorld().getEnvironment();
		Environment toEnv = to.getWorld().getEnvironment();
		
		if (!((fromEnv == Environment.NETHER && toEnv == Environment.NORMAL ) || 
				(toEnv == Environment.NETHER && fromEnv == Environment.NORMAL )))
			return;
		
		to = new Location(to.getWorld(),from.getX(),from.getY(),from.getZ());
		
		event.setTo(to);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		player.sendMessage(Component.text("Welcome to MDGCiv! It is highly suggested that you authenticate at https://civ.dervland.net"));	}
}