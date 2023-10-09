package net.xenocubium.derver.main;

import net.xenocubium.derver.auth.Auth;
import net.xenocubium.derver.gen.BiomeGen;
import net.xenocubium.derver.gen.ChunkGen;
import net.xenocubium.derver.gen.WorldGen;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	WorldGen mainGen = new WorldGen();
	
    @Override
    public void onEnable() {    	
    	String conf = this.getConfig().getString("sql");
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
    	mainGen.renderImage();
    	this.getCommand("auth").setExecutor(new Auth(conf));
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