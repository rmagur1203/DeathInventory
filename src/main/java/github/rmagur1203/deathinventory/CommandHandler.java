package github.rmagur1203.deathinventory;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler implements CommandExecutor {
    private Main main;

    public CommandHandler(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("deathinfo")) {
                onInfo(player, args);
            }
            if (command.getName().equalsIgnoreCase("deathrestore")) {
                onRestore(player, args);
            }
            return true;
        }
        return false;
    }

    public void onInfo(Player sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (args.length < 1) {
            sender.sendMessage("UUID is empty.");
            return;
        }
        String uuid = args[0];
        if (uuid.isBlank()) {
            sender.sendMessage("UUID is empty.");
            return;
        }

        PlayerDeathData data = PlayerDeathData.loadData(uuid);
        if (data == null) {
            sender.sendMessage("No data for UUID.");
            return;
        }

        sender.sendMessage("[INFO] location: " + data.deathLocation.toString());
        sender.sendMessage("[INFO] dropped experience: " + data.droppedExp);
        sender.openInventory(data.toInventory());
    }

    public void onRestore(Player sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (args.length < 1) {
            sender.sendMessage("UUID is empty.");
            return;
        }

        String uuid = args[0];
        if (uuid.isBlank()) {
            sender.sendMessage("UUID is empty.");
            return;
        }

        PlayerDeathData data = PlayerDeathData.loadData(uuid);
        if (data == null) {
            sender.sendMessage("No data for UUID.");
            return;
        }

        String playerName = data.playerName;
        Player player = main.getServer().getPlayer(playerName);
        if (player == null) {
            sender.sendMessage("Missing user for saved data. Is the user offline?");
            return;
        }

        for (ItemStack itemStack : data.drops) {
            player.getInventory().addItem(itemStack);
        }
        player.giveExp(data.droppedExp);
        sender.sendMessage("Recovered dropped items and experience points to " + playerName + ".");
        player.sendMessage("Recovered dropped items and experience points of uuid " + uuid + ".");
    }
}
