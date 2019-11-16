package xyz.joshrpg.SpeakNarrator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class Main extends JavaPlugin {
	
	@Override public void onEnable() {
		getServer().getPluginManager().registerEvents(new SpeakNarratorChatListener(), this);
	}
	
	@Override public void onDisable() {}
	
	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("version")) {
            sender.sendMessage("You are running version 1.0.1 of SpeakNarrator by Joshrpg");
            return true;
        }
        return false;
    }
	
	
	public class SpeakNarratorChatListener implements Listener {
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
		public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
			if (event.isCancelled())
				return;
			
			BaseComponent message;
			if (event.getPlayer().getScoreboard().getEntryTeam(event.getPlayer().getDisplayName()) != null) {
				ChatColor color = event.getPlayer().getScoreboard().getEntryTeam(event.getPlayer().getDisplayName()).getColor();
				message = new TranslatableComponent("chat.type.text", color + event.getPlayer().getDisplayName() + ChatColor.RESET, event.getMessage());
			}
			else
				message = new TranslatableComponent("chat.type.text", event.getPlayer().getDisplayName(), event.getMessage());
			
			for (Player p : event.getRecipients()) {
				p.spigot().sendMessage(ChatMessageType.CHAT, message);
			}
			
			event.getRecipients().clear();
		}
	}
}
