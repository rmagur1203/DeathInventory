package github.rmagur1203.deathinventory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PlayerDeathData implements Serializable {

    public final String playerName;
    public final int droppedExp;
    public final List<ItemStack> drops;
    public final Location deathLocation;

    public PlayerDeathData(
            String playerName,
            int droppedExp,
            List<ItemStack> drops,
            Location deathLocation
    ){
        this.playerName = playerName;
        this.droppedExp = droppedExp;
        this.drops = drops;
        this.deathLocation = deathLocation;
    }

    public Inventory toInventory() {
        Inventory inventory = Bukkit.createInventory(null, 45, playerName);

        for (int i = 0; i < drops.size(); i++) {
            inventory.setItem(i, drops.get(i));
        }

        return inventory;
    }

    public boolean saveData(String uuid) {
        try {
            String root = JavaPlugin.getPlugin(Main.class).getDataFolder().getPath();
            FileOutputStream fileOut = new FileOutputStream(root + "/death-" + uuid + ".data");
            GZIPOutputStream gzOut = new GZIPOutputStream(fileOut);
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(gzOut);
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PlayerDeathData loadData(String uuid) {
        try {
            String root = JavaPlugin.getPlugin(Main.class).getDataFolder().getPath();
            File file = new File(root + "/death-" + uuid + ".data");
            if (!file.exists())
                return null;
            FileInputStream fileIn = new FileInputStream(file);
            GZIPInputStream gzIn = new GZIPInputStream(fileIn);
            BukkitObjectInputStream in = new BukkitObjectInputStream(gzIn);
            PlayerDeathData data = (PlayerDeathData) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
