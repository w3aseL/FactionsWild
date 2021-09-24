package gg.weasel.mc.FactionsWild;

import org.bukkit.plugin.java.JavaPlugin;

public class FactionsWildMain extends JavaPlugin {

	public void onEnable() {	
		this.getCommand("factionswild").setExecutor(FactionsWildCommand.getInstance());
		this.getCommand("f_wild").setExecutor(FactionsWildCommand.getInstance());
		this.getCommand("fwild").setExecutor(FactionsWildCommand.getInstance());
		
		this.getLogger().info("Loaded FactionsWild!");
	}
	
	public void onDisable() {
		
	}
	
}
