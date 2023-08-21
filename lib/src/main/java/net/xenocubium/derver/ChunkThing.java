package net.xenocubium.derver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ChunkThing {
	YamlConfiguration config;
	
	String file = "chunk.yaml";
	Group groupObj;
	
	public ChunkThing(Group groupObj) {
		File powerFile = new File(file);
    	if (!powerFile.exists()) {
    		try {
    			powerFile.createNewFile();
    		} catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	
    	this.config = YamlConfiguration.loadConfiguration(powerFile);
    	
    	this.groupObj = groupObj;
	}
	
	public void claimChunk(String group, int x, int z) {
		String key = x + "c" + z;
		
		config.set(key, group);
		
    	try {
			config.save("chunk.yaml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getChunk(int x, int z) {
		String key = x + "c" + z;
		
		String owner = config.getString(key);
		
		if (owner == null || this.groupObj.getGroup(owner).size() == 0) return null;
		
		return owner;
	}
}
