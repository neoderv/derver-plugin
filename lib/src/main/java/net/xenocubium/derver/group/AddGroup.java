package net.xenocubium.derver.group;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddGroup implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (args.length < 2) {
			return false;
		}
		
		String group = args[0];
		String addPlayer = args[1];
		
		Player addPlayerObj = Bukkit.getPlayer(addPlayer);
    	
    	Player player = (Player) sender;
    	Group groupObj = (new Group());
    	List<String> groups = groupObj.getGroups(player, true);
    	
		if (!groups.contains(group)) return false;
		
		groupObj.addGroup(group, addPlayerObj,false);
		
		return true;
	}

}
