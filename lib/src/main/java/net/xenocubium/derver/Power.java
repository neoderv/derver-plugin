package net.xenocubium.derver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public class Power implements CommandExecutor {

	Material cost = Material.DIAMOND;
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (!(sender instanceof Player)) return false;
		
		if (args.length < 1) return false;
		
		String group = args[0];
		
		Player player = (Player) sender;
    	
    	Group groupObj = (new Group());
    	List<String> groups = groupObj.getGroups(player, false);
		
		if (!groups.contains(group)) return false;
		
		Inventory inv = player.getInventory();
		ItemStack[] contents = inv.getContents();
		inv.remove(cost);
		
		int amount = 0;
		for (int i = 0; i < contents.length; i++) {
			ItemStack content = contents[i];
			if (content == null) continue;
			if (content.getType() == cost) {
				amount += content.getAmount();
			}
		}
		
		amount *= 5;
		
    	PowerThing pthing = new PowerThing(new Group());
    	
    	pthing.addPower(group, amount);
    	
    	player.sendMessage(Component.text("Your score has been updated by " +  amount));
		
		return true;
	}

}
