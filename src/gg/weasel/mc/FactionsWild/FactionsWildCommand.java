package gg.weasel.mc.FactionsWild;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

import net.md_5.bungee.api.ChatColor;

public class FactionsWildCommand implements CommandExecutor {
	
	static final int MAX_STEPS = 20, MAX_DIST = 750;
	
	private static FactionsWildCommand fCmdClass = null;
	
	public static FactionsWildCommand getInstance() {
		if (fCmdClass == null)
			fCmdClass = new FactionsWildCommand();
		
		return fCmdClass;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			
			p.sendMessage(ChatColor.YELLOW + "Attempting to teleport you to a random location. Please wait...");
			
			Location spawn = p.getLocation().getWorld().getSpawnLocation();

			int step = 0;
			int minX = spawn.getBlockX() - MAX_DIST, maxX = spawn.getBlockX() + MAX_DIST;
			int minZ = spawn.getBlockZ() - MAX_DIST, maxZ = spawn.getBlockZ() + MAX_DIST;
			
			Location teleportLoc = null;
			
			// Go until location is valid
			while(true) {
				Random rX = new Random(), rZ = new Random();
				int newX = rX.nextInt(maxX - minX) + minX, newZ = rZ.nextInt(maxZ - minZ) + minZ;
				
				teleportLoc = new Location(p.getWorld(), newX, p.getWorld().getHighestBlockYAt(newX, newZ) + 1, newZ);
				
				if(teleportLoc.getBlock().getBiome().name().toLowerCase().contains("ocean")) {
					continue; // fuck the oceans
				}
				
				Faction facAtLoc = Board.getInstance().getFactionAt(new FLocation(teleportLoc));
				
				if(facAtLoc == null || facAtLoc.isWilderness()) {
					break;
				}
				
				step++;
				
				if(step >= MAX_STEPS) {
					p.sendMessage(ChatColor.RED + "Failed to teleport you to a random location! Try again!");
					
					return true;
				}
			}
			
			p.teleport(teleportLoc);
			p.sendMessage(ChatColor.GREEN + "Teleported you to a random location in the wilderness!");
			
			return true;
		}

		return false;
	}

}
