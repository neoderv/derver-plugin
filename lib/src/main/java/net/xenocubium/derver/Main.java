package net.xenocubium.derver;

import net.kyori.adventure.text.Component;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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