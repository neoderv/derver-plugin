package net.xenocubium.derver;

import java.util.List;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class ChatThing implements ChatRenderer {

	@Override
	public @NotNull Component render(@NotNull Player player, @NotNull Component comp,
			@NotNull Component message, @NotNull Audience viewer) {
   	 	Group groupObj = (new Group());
	    List<String> groups = groupObj.getGroups(player, false);
	     
	    comp = comp.append(Component.text(" >> "));
	    comp = comp.color(TextColor.color(255,255,255));
	     
	    int i = 0;
	    
	    for (String group : groups) {
	    	i++;
	    	if (group.length() > 15) {
	    		group = group.substring(0,12) + "...";
	    	} else {
	    		group = group;
	    	}
	    	
	    	if (i > 3) continue;
	    	
	    	Component prefix = Component.text("["+group+"] ", TextColor.color(164, 99, 255));
	    	comp = prefix.append(comp);

	    }
	    
	    message = message.color(TextColor.color(180,180,180));

		return comp.append(message);
	}

}
