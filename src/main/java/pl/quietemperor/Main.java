package pl.quietemperor;

import commands.RozdajCommand;
import complementer.RozdajCommandTabCompleter;
import listeners.PlayerJoinListener;
import manager.RozdajManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static RozdajManager rozdajManager;

    @Override
    public void onEnable() {
        rozdajManager = new RozdajManager(this);
        getCommand("rozdaj").setExecutor(new RozdajCommand(this));
        getCommand("rozdaj").setTabCompleter(new RozdajCommandTabCompleter());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(rozdajManager), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RozdajManager getRozdajManager() {
        return rozdajManager;
    }
}
