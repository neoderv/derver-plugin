package net.xenocubium.derver.main;

import net.xenocubium.derver.claim.ClaimChunk;
import net.xenocubium.derver.claim.GetPower;
import net.xenocubium.derver.claim.Power;
import net.xenocubium.derver.gen.BiomeGen;
import net.xenocubium.derver.gen.ChunkGen;
import net.xenocubium.derver.gen.WorldGen;
import net.xenocubium.derver.group.AddGroup;
import net.xenocubium.derver.group.GroupInfo;
import net.xenocubium.derver.group.LeaveGroup;
import net.xenocubium.derver.group.NewGroup;

import org.bukkit.Bukkit;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	WorldGen mainGen = new WorldGen();
	
    @Override
    public void onEnable() {    	
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
    	mainGen.renderImage();
    	this.getCommand("getpower").setExecutor(new GetPower());
    	this.getCommand("power").setExecutor(new Power());
    	this.getCommand("claim").setExecutor(new ClaimChunk());
    	this.getCommand("newgroup").setExecutor(new NewGroup());
    	this.getCommand("addgroup").setExecutor(new AddGroup());
    	this.getCommand("leavegroup").setExecutor(new LeaveGroup());
    	this.getCommand("group").setExecutor(new GroupInfo());
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new ChunkGen(mainGen);
    }
    
    @Override
    public BiomeProvider getDefaultBiomeProvider(String worldName, String id) {
        return new BiomeGen(mainGen);
    }

}