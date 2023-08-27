package net.xenocubium.derver;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

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
	
	public boolean blockHandler(BlockEvent event, Player player) {
		Location loc = event.getBlock().getLocation();

		if (loc.getWorld().getEnvironment() != World.Environment.NORMAL) return false;
		int x = loc.blockX() / 16;
		int z = loc.blockZ() / 16;
		 
		Group groupObj = (new Group());
		ChunkThing cThing = new ChunkThing(groupObj);
		
		String group = cThing.getChunk(x,z);
		
		if (group == null) return false;
		
		List<String> groups = groupObj.getGroups(player, false);
		
		if (!groups.contains(group)) {
			player.sendMessage(Component.text("This block is owned by " + group));
			return true;
		}
		
		return false;
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (blockHandler(event,player)) {
			event.setCancelled(true);
		}
	 }
	 
	 @EventHandler
	 public void onBlockPlace(BlockPlaceEvent event) {
		 Player player = event.getPlayer();
		 if (blockHandler(event,player)) {
			 event.setCancelled(true);
		 }
	 }
	 
     @EventHandler
     public void onPlayerChat(AsyncChatEvent e){
    	 e.renderer(new ChatThing());
     }
     
     @EventHandler
     public void onKill(PlayerDeathEvent d) {
     	
    	Player target = d.getPlayer();
    	Entity killer = ((LivingEntity) target).getKiller();
    	 
    	Group groupObj = (new Group());
     	List<String> groups = groupObj.getGroups(target, false);
     	
     	if (groups.size() < 1 || groups == null) return;
     	
     	PowerThing pThing = new PowerThing(groupObj);
     	
     	String group = groups.get(0);
     	
     	int amount = pThing.getPower(group);
     	 
     	target.sendMessage(Component.text("You lost power from dying"));
     	
     	int deduct = (killer == null) ? 1 : 5;
     	
     	pThing.addPower(group, -deduct);
     	
     	if (amount < deduct) {
     		target.sendMessage(Component.text("Your group ran out of power and collapsed"));
     		return;
     	}
     	
     	if (killer == null) return;
     	
     	Player killerPlayer = (Player) killer;
     	
     	groups = groupObj.getGroups(killerPlayer, false);
     	group = groups.get(0);
     	
     	pThing.addPower(group, deduct);

     	killerPlayer.sendMessage(Component.text("You gained power from killing another player"));
     }
}