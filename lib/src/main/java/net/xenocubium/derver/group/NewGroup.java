package net.xenocubium.derver.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NewGroup implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (args.length < 1) {
			return false;
		}
		
		String group = args[0];
		
		String pattern= "^[a-zA-Z0-9_]*$";
		
		if (!group.matches(pattern)) return false;
    	
    	Player player = (Player) sender;
    	Group groupObj = (new Group());
    	List<String> groupie = groupObj.getGroup(group);
		
		if (groupie != null && groupie.size() > 0) return false;
		
		groupObj.addGroup(group, player,false);

		return true;
	}

}
