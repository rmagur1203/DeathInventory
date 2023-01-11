package github.rmagur1203.deathinventory;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);

        getCommand("deathinfo").setExecutor(new CommandHandler());
        getCommand("deathrestore").setExecutor(new CommandHandler());
    }
}