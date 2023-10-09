package net.xenocubium.derver.auth;

import org.bukkit.command.Command;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public class Auth implements CommandExecutor {
	
	String sql;
	Connection conn = null;
	
	public Auth(String sql) {
		this.sql = sql;
	}

	    public void connect() {
	    	if (this.conn != null) return;
	        try {
	            String url = "jdbc:sqlite:".concat(this.sql);
	            this.conn = DriverManager.getConnection(url);
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {		
		connect();
		if (args.length < 1) return false;
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE user SET mcname = ? WHERE token = ?");
			statement.setString(1, sender.getName());
			statement.setString(2, args[0]);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sender.sendMessage(Component.text("You have been authenticated."));
		
		return true;
	}
}
