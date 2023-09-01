package net.xenocubium.derver.claim;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.xenocubium.derver.group.Group;

public class ClaimChunk implements CommandExecutor {

	int cost = 1;
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (args.length < 1) return false;
		
		String group = args[0];
		
		Player player = (Player) sender;
    	
    	Group groupObj = (new Group());
    	List<String> groups = groupObj.getGroups(player, false);
		
		if (!groups.contains(group)) return false;
		
		Location loc = player.getLocation();
	
		if (loc.getWorld().getEnvironment() != World.Environment.NORMAL) return false;
		
		int x = loc.blockX() / 16;
		int z = loc.blockZ() / 16;
		
		ChunkThing cThing = new ChunkThing(groupObj);
		String owner = cThing.getChunk(x, z);
		if (owner != null && owner != group) return false;
		
		PowerThing pThing = new PowerThing(groupObj);
		int power = pThing.getPower(group);
		
		if (power < 1) return false;
		
		pThing.addPower(group, -1);
		cThing.claimChunk(group,x,z);
		
		return true;
	}

}
