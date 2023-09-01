package net.xenocubium.derver.group;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public class GroupInfo implements CommandExecutor {
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		if (args.length < 1) return false;
		
		Group groupObj = (new Group());
		
		String group = args[0];
		
    	List<String> groupInfo = groupObj.getGroup(group);
    	
    	for (int i = 0; i < groupInfo.size(); i++) {
    		String user = groupInfo.get(i);
    		UUID uuid = UUID.fromString(user);
    		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
    		
    		groupInfo.set(i,(player == null) ? "Unknown" : player.getName());
    	}
    	
    	String groupConcat = String.join("\n- ", groupInfo);
    	
    	sender.sendMessage(Component.text("Group " + group + " has the following members:\n- " +  groupConcat));
		return true;
	}

}
