package github.rmagur1203.deathinventory;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    public static String root = "";
    @Override
    public void onEnable() {
        super.onEnable();
        Main.root = "plugins/" + this.getName() + "/";
        File folder = new File(Main.root);
        if (!folder.exists())
            folder.mkdirs();

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        getCommand("deathinfo").setExecutor(new CommandHandler(this));
        getCommand("deathrestore").setExecutor(new CommandHandler(this));
    }
}