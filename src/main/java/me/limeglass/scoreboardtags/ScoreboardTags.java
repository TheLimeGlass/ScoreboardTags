package me.limeglass.scoreboardtags;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.limeglass.scoreboardtags.elements.Register;
import me.limeglass.scoreboardtags.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardTags extends JavaPlugin {
	
	private static Map<String, FileConfiguration> files = new HashMap<String, FileConfiguration>();
	private String packageName = "me.limeglass.scoreboardtags";
	private static String prefix = "&8[&cScoreboardTags&8] &e";
	private static String nameplate = "[ScoreboardTags] ";
	private static ScoreboardTags instance;
	private SkriptAddon addon;
	private Metrics metrics;
	
	public void onEnable(){
		addon = Skript.registerAddon(this);
		instance = this;
		saveDefaultConfig();
		File config = new File(getDataFolder(), "config.yml");
		if (!Objects.equals(getDescription().getVersion(), getConfig().getString("version"))) {
			consoleMessage("&dNew update found! Updating files now...");
			if (config.exists()) new SpigotConfigSaver(this).execute();
		}
		for (String name : Arrays.asList("config", "syntax")) { //replace config with future files here
			File file = new File(getDataFolder(), name + ".yml");
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				saveResource(file.getName(), false);
			}
			FileConfiguration configuration = new YamlConfiguration();
			try {
				configuration.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			files.put(name, configuration);
		}
		metrics = new Metrics(this);
		Register.metrics(metrics);
		if (!getConfig().getBoolean("DisableRegisteredInfo", false)) Bukkit.getLogger().info(nameplate + "has been enabled!");
	}
	
	public SkriptAddon getAddonInstance() {
		return addon;
	}
	
	public static String getNameplate() {
		return nameplate;
	}
	
	public static ScoreboardTags getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public Metrics getMetrics() {
		return metrics;
	}
	
	//Grabs a FileConfiguration of a defined name. The name can't contain .yml in it.
	public FileConfiguration getConfiguration(String file) {
		return (files.containsKey(file)) ? files.get(file) : null;
	}
	
	public static void save(String configuration) {
		try {
			File configurationFile = new File(instance.getDataFolder(), configuration + ".yml");
			instance.getConfiguration(configuration).save(configurationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void debugMessage(@Nullable String... messages) {
		if (instance.getConfig().getBoolean("debug")) {
			for (String text : messages) consoleMessage("&b" + text);
		}
	}
	
	public static void infoMessage(@Nullable String... messages) {
		if (messages != null && messages.length > 0) {
			for (String text : messages) Bukkit.getLogger().info(getNameplate() + text);
		} else {
			Bukkit.getLogger().info("");
		}
	}

	public static void consoleMessage(@Nullable String... messages) {
		if (instance.getConfig().getBoolean("DisableConsoleMessages", false)) return;
		if (messages != null && messages.length > 0) {
			for (String text : messages) {
				if (instance.getConfig().getBoolean("DisableConsoleColour", false)) infoMessage(ChatColor.stripColor(Utils.cc(text)));
				else Bukkit.getConsoleSender().sendMessage(Utils.cc(prefix + text));
			}
		} else {
			Bukkit.getLogger().info("");
		}
	}

}