package net.xenocubium.derver.group;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Group {
	YamlConfiguration config;
	
	String file = "groups.yaml";
	
	public Group() {
		File powerFile = new File(file);
    	if (!powerFile.exists()) {
    		try {
    			powerFile.createNewFile();
    		} catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	
    	this.config = YamlConfiguration.loadConfiguration(powerFile);
	}
	
	public List<String> getGroups(Player player, boolean checkOwner) {
		UUID uuid = player.getUniqueId();
    	String idStr = uuid.toString();
    	
    	Set<String> data = config.getKeys(false);
    	
    	ArrayList<String> groups = new ArrayList<String>();
    	
    	for (String entry : data) {
    		List<String> mem = config.getStringList(entry);
    		if (mem.contains(idStr) && (!checkOwner || mem.get(0).equals(idStr))) {
        		groups.add(entry);
    		}
    	}
    	
    	return groups;
	}
	
	public void deleteGroup(String str) {
		config.set(str, null);
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void addGroup(String str, Player player, boolean remove) {
		List<String> group = getGroup(str);
		
		UUID id = player.getUniqueId();
		String idStr = id.toString();
		
		if (!remove) {
			group.add(idStr);
		} else {
			group.remove(idStr);
		}

		this.config.set(str, group);
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public List<String> getGroup(String str) {
		return config.getStringList(str);
	}
}
