package net.xenocubium.derver.group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveGroup implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (args.length < 1) {
			return false;
		}
		
		String group = args[0];
		
    	Player player = (Player) sender;
    	Group groupObj = (new Group());
		
		groupObj.addGroup(group, player, true);

		return true;
	}

}
